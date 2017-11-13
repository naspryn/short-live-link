package net.naspryn.shortlivelink.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ConfigurationService {

    @Value("${token.length:12}")
    private Integer tokenLength;

    @Value("${default.ttl:60}")
    private Long defaultTTL;

    public Integer getTokenLength() {
        return tokenLength;
    }

    public void setTokenLength(Integer tokenLength) {
        this.tokenLength = tokenLength;
    }

    public Long getDefaultTTL() {
        return defaultTTL;
    }

    public void setDefaultTTL(Long defaultTTL) {
        this.defaultTTL = defaultTTL;
    }
}
