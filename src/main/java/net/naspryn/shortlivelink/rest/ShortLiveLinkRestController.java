package net.naspryn.shortlivelink.rest;

import net.naspryn.shortlivelink.common.LinkValidator;
import net.naspryn.shortlivelink.service.ShortLiveLinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class ShortLiveLinkRestController {


    private ShortLiveLinkService linkService;

    private LinkValidator linkValidator;

    @Autowired
    public ShortLiveLinkRestController(LinkValidator linkValidator, ShortLiveLinkService linkService) {
        this.linkService = linkService;
        this.linkValidator = linkValidator;
    }

    @GetMapping("/generateToken")
    public String generateToken(@RequestParam String url) {
        linkValidator.validate(url);
        return "Token for " + url + " is " + linkService.generateToken(url) + " with TTL " + linkService.getDefaultTTL();
    }

    @GetMapping("/token/{token}")
    public ModelAndView redirectToLink(@PathVariable String token) {
        return new ModelAndView("redirect:" + linkService.getUrlFromToken(token));
    }
}
