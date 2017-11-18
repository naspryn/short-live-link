package net.naspryn.shortlivelink;

import net.naspryn.shortlivelink.api.ShortLiveLink;
import net.naspryn.shortlivelink.exceptions.LinkNotFoundException;
import org.cassandraunit.spring.CassandraUnitDependencyInjectionTestExecutionListener;
import org.cassandraunit.spring.EmbeddedCassandra;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import static java.util.concurrent.TimeUnit.SECONDS;
import static net.naspryn.shortlivelink.util.TestUtil.GOOGLE_COM;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@RunWith(SpringRunner.class)
@SpringBootTest()
@Configuration
@TestPropertySource("classpath:application-test.properties")
@TestExecutionListeners({
        CassandraUnitDependencyInjectionTestExecutionListener.class,
		DependencyInjectionTestExecutionListener.class })
@EmbeddedCassandra(timeout = 30_000L)
public class ShortLiveLinkApplicationTests {

    private static final String VALID_TOKEN_REGEX = "[a-z0-9]{20}";
    private static final String INVALID_TOKEN = "4gd4";

    @Autowired
	private ShortLiveLink service;

	@Test
    public void generateTokenShouldReturnValidToken() {
        assertValidToken(generateSomeToken());
    }

    @Test
    public void getLinkShouldThrowLinkNotFoundExceptionWhenInvalidToken() {
	    assertThatThrownBy(() -> service.getUrlFromToken(INVALID_TOKEN)).isInstanceOf(LinkNotFoundException.class);
    }

    @Test
    public void getLinkForGeneratedTokenShouldReturnExpectedUrl() {
        String token = generateSomeToken();
        String urlFromToken = service.getUrlFromToken(token);
        assertExpectedUrl(urlFromToken);
    }

    @Test
	public void tokenShouldExpiresAfterTTL() throws InterruptedException {
		String token = generateSomeToken();
        assertValidToken(token);
		String url = service.getUrlFromToken(token);
		assertExpectedUrl(url);

		SECONDS.sleep(4); // Wait for TTL expires
		assertThatThrownBy(() -> service.getUrlFromToken(token)).isInstanceOf(LinkNotFoundException.class);
	}

    private String generateSomeToken() {
        return service.generateToken(GOOGLE_COM);
    }

    private void assertExpectedUrl(String url) {
        assertThat(url).as("Received url should be as expected").isEqualTo(GOOGLE_COM);
    }

    private void assertValidToken(String token) {
        assertThat(token).as("Generated token should be valid").matches(VALID_TOKEN_REGEX);
    }
}
