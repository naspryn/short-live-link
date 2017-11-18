package net.naspryn.shortlivelink.util;

import org.springframework.test.web.servlet.MvcResult;

import java.io.UnsupportedEncodingException;

import static org.assertj.core.api.Assertions.assertThat;

public class TestUtil {

    public static final String VALID_TOKEN = "asdf78dfs62s";
    public static final String INVALID_TOKEN = "Zd78s";
    public static final String GOOGLE_COM = "https://google.com";
    public static final String INVALID_URL = "invalid.com_";
    public static final String GENERATE_TOKEN_URL = "/generateToken?url=";
    public static final String GET_LINK_BY_TOKEN_URL = "/token/";
    public static final String INVALID_URL_ERROR_MESSAGE = "Invalid URL exception";
    public static final String INVALID_TOKEN_ERROR_MESSAGE = "Link for given token not found";


    public static void statusOk(MvcResult result) {
        assertThat(result.getResponse().getStatus()).as("Status should be 200 OK").isEqualTo(200);
    }

    public static void status302(MvcResult result) {
        assertThat(result.getResponse().getStatus()).as("Status should be 302").isEqualTo(302);
    }

    public static void status400(MvcResult result) {
        assertThat(result.getResponse().getStatus()).as("Status should be 400").isEqualTo(400);
    }

    public static void status404(MvcResult result) {
        assertThat(result.getResponse().getStatus()).as("Status should be 404").isEqualTo(404);
    }

    public static void tokenValid(MvcResult result) throws UnsupportedEncodingException {
        assertThat(result.getResponse().getContentAsString())
                .as("Token should be valid")
                .matches("[a-z0-9]{12}");
    }

    public static void invalidUrlErrorMessage(MvcResult result) {
        assertThat(result.getResponse().getErrorMessage())
                .as("Error Message should be as expected")
                .isEqualTo(INVALID_URL_ERROR_MESSAGE);
    }

    public static void invalidTokenErrorMessage(MvcResult result) {
        assertThat(result.getResponse().getErrorMessage())
                .as("Error Message should be as expected")
                .isEqualTo(INVALID_TOKEN_ERROR_MESSAGE);
    }

    public static void redirectedUrlEqualTo(MvcResult result, String expectedUrl) {
        assertThat(result.getResponse().getRedirectedUrl())
                .as("Redirected URL should be as expected URL")
                .isEqualTo(expectedUrl);
    }
}
