package net.naspryn.shortlivelink.service;

import net.naspryn.shortlivelink.api.ShortLiveLink;
import net.naspryn.shortlivelink.common.TokenGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ShortLiveLinkService implements ShortLiveLink {

    private TokenGenerator tokenGenerator;

    @Autowired
    public ShortLiveLinkService(TokenGenerator tokenGenerator) {
        this.tokenGenerator = tokenGenerator;
    }

    public String generateToken(String url) {
        return tokenGenerator.generateToken();
    }

    @Override
    public String getUrlFromToken(String token) {
        return "http://google.pl?q=" + token;
    }

    @Value("${default.ttl}")
    private Integer defaultTTL;

    public Integer getDefaultTTL() {
        return defaultTTL;
    }
}
