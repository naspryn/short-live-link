package net.naspryn.shortlivelink.rest;

import net.naspryn.shortlivelink.common.TokenGenerator;
import net.naspryn.shortlivelink.service.ShortLiveLinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ShortLiveLinkRestController {


    private ShortLiveLinkService linkService;

    @Autowired
    public ShortLiveLinkRestController(TokenGenerator tokenGenerator, ShortLiveLinkService linkService) {
        this.linkService = linkService;
    }

    @GetMapping("/generateToken")
    public String generateToken(@RequestParam String url) {
        return "Token for " + url + " is " + linkService.generateToken(url) + " with TTL " + linkService.getDefaultTTL();
    }
}
