package com.eventty.businessservice.event.api.dto.response;

import lombok.*;

import java.time.LocalDate;

@Getter
public class HostFindByIdResponseDTO {
    private String name;                // 이름
    private String phone;               // 유저 전화번호
}
