package com.eventty.authservice.infrastructure.config.utils;

import com.eventty.authservice.domain.Enum.Roles;
import org.springframework.core.convert.converter.Converter;

public class StringToEnumConverter implements Converter<String, Roles> {
    @Override
    public Roles convert(String source) {
        return Roles.valueOf(source.toUpperCase());
    }
}
