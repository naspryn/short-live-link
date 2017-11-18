package net.naspryn.shortlivelink.rest;

import net.naspryn.shortlivelink.common.LinkValidator;
import net.naspryn.shortlivelink.common.TokenGenerator;
import net.naspryn.shortlivelink.domain.TokenLinkPair;
import net.naspryn.shortlivelink.repositories.TokenLinkPairRepository;
import net.naspryn.shortlivelink.service.ConfigurationService;
import net.naspryn.shortlivelink.service.ShortLiveLinkService;
import net.naspryn.shortlivelink.util.TestUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;

import static net.naspryn.shortlivelink.util.TestUtil.*;
import static org.mockito.Mockito.when;

/**
 * This class tests integration of services with mocked repository
 */
@RunWith(SpringRunner.class)
@TestPropertySource("classpath:application-test.properties")
@WebMvcTest(ShortLiveLinkRestController.class)
public class ShortLiveLinkRestControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @SpyBean private LinkValidator linkValidator;
    @SpyBean private ShortLiveLinkService service;
    @SpyBean private TokenGenerator tokenGenerator;
    @SpyBean private ConfigurationService configurationService;

    @MockBean
    private TokenLinkPairRepository repository;

    @Test
    public void generateTokenShouldReturnValidToken() throws Exception {
        mockMvc.perform(get(GENERATE_TOKEN_URL + GOOGLE_COM))
                .andExpect(TestUtil::statusOk)
                .andExpect(TestUtil::tokenValid);
    }

    @Test
    public void redirectToLinkByTokenShouldHave302Response() throws Exception {
        when(repository.getByToken(VALID_TOKEN))
                .thenReturn(Optional.ofNullable(new TokenLinkPair(VALID_TOKEN, GOOGLE_COM)));

        mockMvc.perform(get(GET_LINK_BY_TOKEN_URL + VALID_TOKEN))
                .andExpect(TestUtil::status302)
                .andExpect(r -> redirectedUrlEqualTo(r, GOOGLE_COM));
    }

    @Test
    public void generateTokenForInvalidUrlShouldHaveError400Response() throws Exception {
        mockMvc.perform(get(GENERATE_TOKEN_URL + INVALID_URL))
                .andExpect(TestUtil::status400)
                .andExpect(TestUtil::invalidUrlErrorMessage);
    }

    @Test
    public void getLinkForInvalidTokenShouldHaveError404Response() throws Exception {
        when(repository.getByToken(INVALID_TOKEN))
                .thenReturn(Optional.empty());

        mockMvc.perform(get(GET_LINK_BY_TOKEN_URL + INVALID_TOKEN))
                .andExpect(TestUtil::status404)
                .andExpect(TestUtil::invalidTokenErrorMessage);
    }

    private static MockHttpServletRequestBuilder get(String urlTemplate) {
        return MockMvcRequestBuilders.get(
                urlTemplate);
    }
}
