package com.eventty.userservice.application.dto.request;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Setter
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserImageUpdateRequestDTO {
    private String imageId;
    private MultipartFile image;
    private String isUpdate;
}
