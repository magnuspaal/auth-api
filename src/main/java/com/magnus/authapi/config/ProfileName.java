package com.magnus.authapi.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class ProfileName {
    @Autowired
    private Environment environment;

    public boolean isProduction() {
        return getActiveProfileName().equals("production");
    }

    public String getActiveProfileName() {
        String[] activeProfiles = environment.getActiveProfiles();
        if (activeProfiles.length > 0) {
            return activeProfiles[0];
        }
        return "";
    }
}
