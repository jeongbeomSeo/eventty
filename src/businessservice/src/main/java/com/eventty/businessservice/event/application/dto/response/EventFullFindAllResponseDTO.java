package com.eventty.businessservice.event.application.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@ToString
public class EventFullFindAllResponseDTO {
    private Long id;
    private Long userId;
    private String title;
    private LocalDateTime eventStartAt;
    private LocalDateTime eventEndAt;
    private Long participateNum;
    private String location;
    private String categoryName;
    private Boolean isActive;
    private Boolean isDeleted;

    private Long imageId;
    private String image; // 이미지 파일
    private String originFileName; // 원본 파일명

    public static EventFullFindAllResponseDTO from(
            EventBasicResponseDTO eventBasic,
            ImageResponseDTO imageInfo) {
        return EventFullFindAllResponseDTO.builder()
                .id(eventBasic.getId())
                .userId(eventBasic.getUserId())
                .title(eventBasic.getTitle())
                .eventStartAt(eventBasic.getEventStartAt())
                .eventEndAt(eventBasic.getEventEndAt())
                .participateNum(eventBasic.getParticipateNum())
                .location(eventBasic.getLocation())
                .categoryName(eventBasic.getCategoryName())
                .isActive(eventBasic.getIsActive())
                .isDeleted(eventBasic.getIsDeleted())
                .imageId(imageInfo.getImageId())
                .image(imageInfo.getImageResourceFromStorage())
                .originFileName(imageInfo.getImageOriginalFileName())
                .build();
    }

    // Swagger 을 위하여 기본 생성자로 기본값 설정
    public EventFullFindAllResponseDTO() {
        this.id = 1L;
        this.userId = 1L;
        this.title = "String";
        this.eventStartAt = LocalDateTime.now();
        this.eventEndAt = LocalDateTime.now();
        this.participateNum = 1L;
        this.location = "String";
        this.isActive = true;
        this.isDeleted = false;
    }

}
