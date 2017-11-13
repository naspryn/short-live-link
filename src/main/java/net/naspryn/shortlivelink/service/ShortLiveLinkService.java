package net.naspryn.shortlivelink.service;

import net.naspryn.shortlivelink.api.ShortLiveLink;
import net.naspryn.shortlivelink.common.TokenGenerator;
import net.naspryn.shortlivelink.domain.TokenLinkPair;
import net.naspryn.shortlivelink.exceptions.LinkNotFoundException;
import net.naspryn.shortlivelink.repositories.TokenLinkPairRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ShortLiveLinkService implements ShortLiveLink {

    private TokenGenerator tokenGenerator;
    private TokenLinkPairRepository repository;
    private ConfigurationService configurationService;

    @Autowired
    public ShortLiveLinkService(
            TokenGenerator tokenGenerator,
            TokenLinkPairRepository repository,
            ConfigurationService configurationService
    ) {
        this.tokenGenerator = tokenGenerator;
        this.repository = repository;
        this.configurationService = configurationService;
    }

    @Override
    public String generateToken(String link) {
        String token = tokenGenerator.generateToken();
        repository.saveWithTTL(token, link, configurationService.getDefaultTTL());
        return token;
    }

    @Override
    public String getUrlFromToken(String token) {
        Optional<TokenLinkPair> tokenLinkPair = repository.getByToken(token);
        return tokenLinkPair.orElseThrow(LinkNotFoundException::new).getLink();
    }
}
