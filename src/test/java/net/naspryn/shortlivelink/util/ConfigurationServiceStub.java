package net.naspryn.shortlivelink.util;

import net.naspryn.shortlivelink.service.ConfigurationService;

public class ConfigurationServiceStub {

    public static ConfigurationService getInstance() {
        ConfigurationService configurationService = new ConfigurationService();
        configurationService.setTokenLength(12);
        configurationService.setDefaultTTL(5L);
        return configurationService;
    }
}
