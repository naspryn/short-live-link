package net.naspryn.shortlivelink;

import net.naspryn.shortlivelink.api.ShortLiveLink;
import net.naspryn.shortlivelink.exceptions.LinkNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static java.util.concurrent.TimeUnit.SECONDS;
import static net.naspryn.shortlivelink.util.TestUtil.GOOGLE_COM;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = {"service.default.ttl=3", "service.token.length=20"})
public class ShortLiveLinkApplicationTests {

	@Autowired
	private ShortLiveLink service;

	@Test
	public void contextLoads() {
	}

	@Test
	public void tokenShouldExpiresAfterTTL () throws InterruptedException {
		String token = service.generateToken(GOOGLE_COM);
		assertThat(token).as("Generated token should be valid").matches("[a-z0-9]{20}");
		String url = service.getUrlFromToken(token);
		assertThat(url).as("Received url should be as expected").isEqualTo(GOOGLE_COM);

		SECONDS.sleep(4); // Wait for TTL expires
		assertThatThrownBy(() -> service.getUrlFromToken(token)).isInstanceOf(LinkNotFoundException.class);
	}

}
