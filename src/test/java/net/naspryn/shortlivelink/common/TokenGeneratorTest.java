package net.naspryn.shortlivelink.common;

import net.naspryn.shortlivelink.util.ConfigurationServiceStub;
import org.junit.BeforeClass;
import org.junit.Test;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class TokenGeneratorTest {

    private static final int TOKENS_QUANTITY = 1_000_000;
    private static TokenGenerator tokenGenerator;

    @BeforeClass
    public static void prepareTokenGenerator() {
        tokenGenerator = new TokenGenerator(ConfigurationServiceStub.getInstance());
    }

    @Test
    public void generateTokenShouldReturnProperValue() throws Exception {
        String token = tokenGenerator.generateToken();

        assertThat(token).matches("[a-z0-9]{12}");
    }

    @Test
    public void generateTokenShouldReturnDifferentValue() {
        String token1 = tokenGenerator.generateToken();
        String token2 = tokenGenerator.generateToken();

        assertThat(token1).isNotEqualTo(token2);
    }

    @Test
    public void generateTokenShouldReturnUniqueValue() {
        long tokensCount = Stream.iterate(tokenGenerator.generateToken(), t -> tokenGenerator.generateToken())
                .limit(TOKENS_QUANTITY)
                .distinct()
                .count();

        assertThat(tokensCount).as("Unique tokens quantity").isEqualTo(TOKENS_QUANTITY);
    }
}