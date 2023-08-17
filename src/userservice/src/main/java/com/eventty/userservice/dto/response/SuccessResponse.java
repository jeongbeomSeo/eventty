package com.eventty.userservice.dto.response;

import lombok.*;
import org.springframework.http.ResponseEntity;

import java.util.Map;

@Setter @Getter @Builder @ToString
@NoArgsConstructor @AllArgsConstructor
public class SuccessResponse {
    private Integer code;
    private String message;
    private Map<String, Object> data;

    public static SuccessResponse of(Integer code, String message, Map<String, Object> data){
        return new SuccessResponse(code, message, data);
    }
}
