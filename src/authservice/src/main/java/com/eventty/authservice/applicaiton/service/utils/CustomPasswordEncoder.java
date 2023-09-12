package com.eventty.authservice.applicaiton.service.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CustomPasswordEncoder {

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    CustomPasswordEncoder(@Lazy BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public String encode(String password) {
        return bCryptPasswordEncoder.encode(password);
    }

    public boolean match(String rawPassword, String encodedPassword) { return bCryptPasswordEncoder.matches(rawPassword, encodedPassword); }
}
