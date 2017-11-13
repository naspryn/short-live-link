package net.naspryn.shortlivelink.common;

import net.naspryn.shortlivelink.service.ConfigurationService;
import org.apache.commons.text.CharacterPredicates;
import org.apache.commons.text.RandomStringGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TokenGenerator {

    private RandomStringGenerator randomStringGenerator;
    private ConfigurationService configurationService;

    @Autowired
    public TokenGenerator(ConfigurationService configurationService) {
        this.configurationService = configurationService;

        randomStringGenerator =
                new RandomStringGenerator.Builder()
                        .withinRange('0', 'z')
                        .filteredBy(CharacterPredicates.LETTERS, CharacterPredicates.DIGITS)
                        .build();
    }

    public String generateToken() {
        return randomStringGenerator.generate(configurationService.getTokenLength()).toLowerCase();
    }
}

