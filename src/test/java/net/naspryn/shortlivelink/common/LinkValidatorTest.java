package net.naspryn.shortlivelink.common;

import net.naspryn.shortlivelink.exceptions.InvalidUrlException;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThatCode;

public class LinkValidatorTest {

    private static final String HTTP_URL = "http://www.google.com";
    private static final String HTTPS_URL = "https://amazon.com";
    private static final String COMPLEX_URL = "https://www.amazon.com/b/ref=unrec_bubbler_3?_encoding=UTF8&node=2102313011&ref=unrec_bubbler_3&pf_rd_m=ATVPDKIKX0DER&pf_rd_s=&pf_rd_r=1DZBHAMM606GGKXQQVR3&pf_rd_t=36701&pf_rd_p=acf4333c-0fca-49cd-a09a-3e392b7d86c9&pf_rd_i=desktop";
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
    public void validateShouldPassForComplexUrl() throws Exception {
        assertThatCode(() -> new LinkValidator().validate(COMPLEX_URL))
                .doesNotThrowAnyException();
    }

    @Test
    public void validateShouldThrowInvalidUrlException() {
        assertThatCode(() -> new LinkValidator().validate(INVALID_URL))
                .isInstanceOf(InvalidUrlException.class);
    }

}