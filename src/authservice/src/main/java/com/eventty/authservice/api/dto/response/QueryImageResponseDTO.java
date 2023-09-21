package com.eventty.authservice.api.dto.response;

/*
 User Server로 받아올 DTO
 이미지 불러오기
 */

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class QueryImageResponseDTO {
    String imageName;
    String imagePath;
}
