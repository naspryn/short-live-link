package net.naspryn.shortlivelink.common;

import net.naspryn.shortlivelink.service.ConfigurationService;
import org.junit.Test;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class TokenGeneratorTest {

    private static final int TOKENS_QUANTITY = 1_000_000;

    @Test
    public void generateTokenShouldReturnProperValue() throws Exception {
        //Given
        TokenGenerator tokenGenerator = getTokenGenerator();

        //When
        String token = tokenGenerator.generateToken();

        //Then
        assertThat(token).matches("[a-z0-9]{12}");
    }

    @Test
    public void generateTokenShouldReturnDifferentValue() {
        TokenGenerator tokenGenerator = getTokenGenerator();

        String token1 = tokenGenerator.generateToken();
        String token2 = tokenGenerator.generateToken();

        assertThat(token1).isNotEqualTo(token2);
    }

    @Test
    public void generateTokenShouldReturnUniqueValue() {
        TokenGenerator tokenGenerator = getTokenGenerator();

        long tokensCount = Stream.iterate(tokenGenerator.generateToken(), t -> tokenGenerator.generateToken())
                .limit(TOKENS_QUANTITY)
                .distinct()
                .count();

        assertThat(tokensCount).as("Unique tokens quantity").isEqualTo(TOKENS_QUANTITY);
    }

    private static TokenGenerator getTokenGenerator() {
        ConfigurationService configurationService = new ConfigurationService();
        configurationService.setTokenLength(12);
        return new TokenGenerator(configurationService);
    }

}