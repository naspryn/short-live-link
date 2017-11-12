package net.naspryn.shortlivelink.common;

import org.apache.commons.text.CharacterPredicates;
import org.apache.commons.text.RandomStringGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class TokenGenerator {

    @Value("${token.length}")
    private Integer tokenLenght;

    private RandomStringGenerator randomStringGenerator;

    @PostConstruct
    private void init() {
        randomStringGenerator =
                new RandomStringGenerator.Builder()
                        .withinRange('0', 'z')
                        .filteredBy(CharacterPredicates.LETTERS, CharacterPredicates.DIGITS)
                        .build();
    }

    public String generateToken() {
        System.out.println(tokenLenght);
        return randomStringGenerator.generate(tokenLenght).toLowerCase();
    }
}

