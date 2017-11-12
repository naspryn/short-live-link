package net.naspryn.shortlivelink.service;

import net.naspryn.shortlivelink.api.ShortLiveLink;
import net.naspryn.shortlivelink.common.TokenGenerator;
import net.naspryn.shortlivelink.domain.TokenUrlPair;
import net.naspryn.shortlivelink.repositories.TokenUrlPairRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ShortLiveLinkService implements ShortLiveLink {

    private TokenGenerator tokenGenerator;

    private TokenUrlPairRepository repository;

    @Autowired
    public ShortLiveLinkService(TokenGenerator tokenGenerator, TokenUrlPairRepository repository) {
        this.tokenGenerator = tokenGenerator;
        this.repository = repository;
    }

    public String generateToken(String url) {
        String token = tokenGenerator.generateToken();
        repository.save(new TokenUrlPair(token, url));
        return token;
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
