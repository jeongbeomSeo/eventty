package com.eventty.authservice.infrastructure;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 *  사용여부 확인 필요
 *
 *  호스트 여부를 Y, N으로 저장해주는 컨버터
 *  만약 적용하지 않을 경우 0과 1로 저장
 */
@Converter
public class BooleanToYNConverter implements AttributeConverter<Boolean, String> {
    @Override
    public String convertToDatabaseColumn(Boolean attribute) {
        return (attribute != null && attribute ? "Y" : "N");
    }

    @Override
    public Boolean convertToEntityAttribute(String yn) {
        return "Y".equalsIgnoreCase(yn);
    }
}
