package net.naspryn.shortlivelink.common;

import net.naspryn.shortlivelink.exceptions.InvalidUrlException;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThatCode;

public class LinkValidatorTest {

    private static final String HTTP_URL = "http://www.google.com";
    private static final String HTTPS_URL = "https://amazon.com";
    private static final String INVALID_URL = "34nbsd.c";

    @Test
    public void validateShouldPassForHttp() throws Exception {
        assertThatCode(() -> new LinkValidator().validate(HTTP_URL))
                .doesNotThrowAnyException();
    }

    @Test
    public void validateShouldPassForHttps() throws Exception {
        assertThatCode(() -> new LinkValidator().validate(HTTPS_URL))
                .doesNotThrowAnyException();
    }

    @Test
    public void validateShouldThrowInvalidUrlException() {
        assertThatCode(() -> new LinkValidator().validate(INVALID_URL))
                .isInstanceOf(InvalidUrlException.class);
    }

}