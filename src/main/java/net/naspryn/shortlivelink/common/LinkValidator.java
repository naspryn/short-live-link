package net.naspryn.shortlivelink.common;

import net.naspryn.shortlivelink.exceptions.InvalidUrlException;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;



@Component
public class LinkValidator {

    UrlValidator urlValidator;

    @PostConstruct
    private void init() {
    String[] schemes = {"http","https"};
    urlValidator = new UrlValidator(schemes);
    }

    public void validate(String url) {
        if (!urlValidator.isValid(url)) {
            throw new InvalidUrlException();
        }
    }
}
