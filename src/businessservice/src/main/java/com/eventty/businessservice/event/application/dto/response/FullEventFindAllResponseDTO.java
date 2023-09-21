package com.eventty.businessservice.event.application.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@ToString
public class FullEventFindAllResponseDTO {
    private Long id;
    private Long hostId;
    private String title;
    private LocalDateTime eventStartAt;
    private LocalDateTime eventEndAt;
    private Long participateNum;
    private String location;
    private String categoryName;
    private Boolean isActive;

    private String image; // 이미지 파일
    private String originFileName; // 원본 파일명

    public static FullEventFindAllResponseDTO of(
            EventBasicWithoutHostInfoResponseDTO eventBasic,
            ImageResponseDTO imageInfo) {
        return FullEventFindAllResponseDTO.builder()
                .id(eventBasic.getId())
                .hostId(eventBasic.getHostId())
                .title(eventBasic.getTitle())
                .eventStartAt(eventBasic.getEventStartAt())
                .eventEndAt(eventBasic.getEventEndAt())
                .participateNum(eventBasic.getParticipateNum())
                .location(eventBasic.getLocation())
                .categoryName(eventBasic.getCategoryName())
                .isActive(eventBasic.getIsActive())
                .image(imageInfo.getImagePathFromStorage())
                .originFileName(imageInfo.getImageOriginalFileName())
                .build();
    }

    // Swagger 을 위하여 기본 생성자로 기본값 설정
    public FullEventFindAllResponseDTO() {
        this.id = 1L;
        this.hostId = 1L;
        this.title = "String";
        this.eventStartAt = LocalDateTime.now();
        this.eventEndAt = LocalDateTime.now();
        this.participateNum = 1L;
        this.location = "String";
        this.isActive = true;
    }

}
