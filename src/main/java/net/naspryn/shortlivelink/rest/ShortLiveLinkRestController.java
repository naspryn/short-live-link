package net.naspryn.shortlivelink.rest;

import net.naspryn.shortlivelink.api.ShortLiveLink;
import net.naspryn.shortlivelink.common.LinkValidator;
import net.naspryn.shortlivelink.service.ShortLiveLinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@RestController
public class ShortLiveLinkRestController {

    private ShortLiveLink linkService;
    private LinkValidator linkValidator;

    @Autowired
    public ShortLiveLinkRestController(LinkValidator linkValidator, ShortLiveLinkService linkService) {
        this.linkService = linkService;
        this.linkValidator = linkValidator;
    }

    @GetMapping("/generateToken")
    public String generateToken(HttpServletRequest request) {
        String link = request.getQueryString().replaceFirst("url=", "");
        linkValidator.validate(link);
        String token = linkService.generateToken(link);
        return "Token is <a href=\"http://localhost:8080/token/" + token + "\">" + token + "</a>";
    }

    @GetMapping("/token/{token}")
    public ModelAndView redirectToLink(@PathVariable String token) {
        return new ModelAndView("redirect:" + linkService.getUrlFromToken(token));
    }
}
