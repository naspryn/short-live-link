package net.naspryn.shortlivelink.common;

import net.naspryn.shortlivelink.service.ConfigurationService;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TokenGeneratorTest {

    @Test
    public void generateTokenShouldReturnProperValue() throws Exception {
        //Given
        TokenGenerator tokenGenerator = getTokenGenerator();

        //When
        String token = tokenGenerator.generateToken();

        //Then
        assertThat(token).matches("[a-z0-9]{8}");
    }

    @Test
    public void generateTokenShouldReturnDifferentValue() {
        TokenGenerator tokenGenerator = getTokenGenerator();

        String token1 = tokenGenerator.generateToken();
        String token2 = tokenGenerator.generateToken();

        assertThat(token1).isNotEqualTo(token2);
    }

    private static TokenGenerator getTokenGenerator() {
        ConfigurationService configurationService = new ConfigurationService();
        configurationService.setTokenLenght(8);
        return new TokenGenerator(configurationService);
    }

}