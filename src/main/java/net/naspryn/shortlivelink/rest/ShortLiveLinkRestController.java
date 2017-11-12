package net.naspryn.shortlivelink.rest;

import net.naspryn.shortlivelink.common.TokenGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ShortLiveLinkRestController {

    private TokenGenerator tokenGenerator;

    @Autowired
    public ShortLiveLinkRestController(TokenGenerator tokenGenerator) {
        this.tokenGenerator = tokenGenerator;
    }

    @GetMapping("/generateToken")
    public String generateToken(@RequestParam String url) {
        return "Token for " + url + " is " + tokenGenerator.generateToken();
    }
}
