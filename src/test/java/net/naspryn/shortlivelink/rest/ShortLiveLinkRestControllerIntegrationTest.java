package net.naspryn.shortlivelink.rest;

import net.naspryn.shortlivelink.common.LinkValidator;
import net.naspryn.shortlivelink.common.TokenGenerator;
import net.naspryn.shortlivelink.domain.TokenLinkPair;
import net.naspryn.shortlivelink.repositories.TokenLinkPairRepository;
import net.naspryn.shortlivelink.service.ConfigurationService;
import net.naspryn.shortlivelink.service.ShortLiveLinkService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.UnsupportedEncodingException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

/**
 * This class tests integration of services with mocked repository
 */
@RunWith(SpringRunner.class)
@WebMvcTest(ShortLiveLinkRestController.class)
public class ShortLiveLinkRestControllerIntegrationTest {

    //TODO: write more tests

    private static final String TOKEN = "asdf78dfs62s";
    private static final String GOOGLE_COM = "https://google.com";
    private static final String GENERATE_TOKEN_URL = "/generateToken?url=";
    private static final String GET_LINK_BY_TOKEN_URL = "/token/";

    @Autowired
    private MockMvc mockMvc;

    @SpyBean private LinkValidator linkValidator;
    @SpyBean private ShortLiveLinkService service;
    @SpyBean private TokenGenerator tokenGenerator;
    @SpyBean private ConfigurationService configurationService;

    @MockBean
    private TokenLinkPairRepository repository;

    @Test
    public void generateToken() throws Exception {
        when(repository.saveWithTTL(any(TokenLinkPair.class), anyLong()))
                .thenReturn(new TokenLinkPair(TOKEN, GOOGLE_COM));

        mockMvc.perform(get(GENERATE_TOKEN_URL + GOOGLE_COM))
                .andExpect(this::statusOk)
                .andExpect(r -> receivedTokenEqualTo(r, TOKEN));
    }

    @Test
    public void redirectToLinkByToken() throws Exception {
        when(repository.getByToken(anyString()))
                .thenReturn(Optional.ofNullable(new TokenLinkPair(TOKEN, GOOGLE_COM)));

        RequestBuilder requestBuilder = get(GET_LINK_BY_TOKEN_URL + TOKEN);
        mockMvc.perform(requestBuilder)
                .andExpect(this::status302)
                .andExpect(r -> redirectedUrlEqualTo(r, GOOGLE_COM));
    }

    private static MockHttpServletRequestBuilder get(String urlTemplate) {
        return MockMvcRequestBuilders.get(
                urlTemplate);
    }

    private void statusOk(MvcResult result) {
        assertThat(result.getResponse().getStatus()).as("Status should be 200 OK").isEqualTo(200);
    }

    private void status302(MvcResult result) {
        assertThat(result.getResponse().getStatus()).as("Status should be 302").isEqualTo(302);
    }

    private void redirectedUrlEqualTo(MvcResult result, String expectedUrl) {
        assertThat(result.getResponse().getRedirectedUrl())
                .as("Redirected URL should be same as expected URL")
                .isEqualTo(expectedUrl);
    }

    private void receivedTokenEqualTo(MvcResult result, String expectedToken) throws UnsupportedEncodingException {
        assertThat(result.getResponse().getContentAsString())
                .as("Received token should be same as expected token")
                .isEqualTo(expectedToken);
    }
}