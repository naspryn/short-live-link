package net.naspryn.shortlivelink.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ConfigurationService {

    @Value("${token.length:8}")
    private Integer tokenLenght;

    @Value("${default.ttl:60}")
    private Long defaultTTL;

    public Integer getTokenLenght() {
        return tokenLenght;
    }

    public void setTokenLenght(Integer tokenLenght) {
        this.tokenLenght = tokenLenght;
    }

    public Long getDefaultTTL() {
        return defaultTTL;
    }

    public void setDefaultTTL(Long defaultTTL) {
        this.defaultTTL = defaultTTL;
    }
}
