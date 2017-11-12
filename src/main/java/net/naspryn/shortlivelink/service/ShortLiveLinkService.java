package net.naspryn.shortlivelink.service;

import net.naspryn.shortlivelink.api.ShortLiveLink;
import net.naspryn.shortlivelink.common.TokenGenerator;
import net.naspryn.shortlivelink.domain.TokenLinkPair;
import net.naspryn.shortlivelink.exceptions.LinkNotFoundException;
import net.naspryn.shortlivelink.repositories.TokenLinkPairRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ShortLiveLinkService implements ShortLiveLink {

    private TokenGenerator tokenGenerator;
    private TokenLinkPairRepository repository;

    @Value("${default.ttl}")
    private Long defaultTTL;

    @Autowired
    public ShortLiveLinkService(TokenGenerator tokenGenerator, TokenLinkPairRepository repository) {
        this.tokenGenerator = tokenGenerator;
        this.repository = repository;
    }

    public String generateToken(String url) {
        String token = tokenGenerator.generateToken();
        repository.saveWithTTL(token, url, defaultTTL);
        return token;
    }

    @Override
    public String getUrlFromToken(String token) {
        TokenLinkPair tokenLinkPair = repository.getByToken(token);
        if (tokenLinkPair == null || tokenLinkPair.getLink() == null) {
            throw new LinkNotFoundException();
        }
        return tokenLinkPair.getLink();
    }

    public Long getDefaultTTL() {
        return defaultTTL;
    }
}
