package net.naspryn.shortlivelink.common;

import net.naspryn.shortlivelink.exceptions.InvalidUrlException;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.stereotype.Component;

@Component
public class LinkValidator {

    UrlValidator urlValidator;

    public LinkValidator() {
        String[] schemes = {"http","https"};
        this.urlValidator =  new UrlValidator(schemes);
    }

    public void validate(String url) {
        if (!urlValidator.isValid(url)) {
            throw new InvalidUrlException();
        }
    }
}
