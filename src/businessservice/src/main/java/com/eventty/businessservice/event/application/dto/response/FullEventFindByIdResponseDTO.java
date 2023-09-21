package com.eventty.businessservice.event.application.dto.response;

import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@ToString
public class FullEventFindByIdResponseDTO {
    // eventBasic
    private Long id;
    private String hostName; // from User Server
    private String hostPhone; // from User Server
    private String title;
    private LocalDateTime eventStartAt;
    private LocalDateTime eventEndAt;
    private Long participateNum;
    private String location;
    private String categoryName;
    private Boolean isActive;

    // eventDetail
    private String content;
    private LocalDateTime applyStartAt;
    private LocalDateTime applyEndAt;
    private Long views;

    // tickets
    private List<TicketResponseDTO> tickets;

    // imageInfo
    private String image; // 이미지 파일
    private String originFileName; // 원본 파일명


    public static FullEventFindByIdResponseDTO of(
            EventBasicFindByIdResponseDTO eventBasic,
            EventDetailFindByIdResponseDTO eventDetail,
            List<TicketResponseDTO> tickets,
            ImageResponseDTO imageInfo) {
        return FullEventFindByIdResponseDTO.builder()
                .id(eventBasic.getId())
                .hostName(eventBasic.getHostName())
                .hostPhone(eventBasic.getHostPhone())
                .title(eventBasic.getTitle())
                .eventStartAt(eventBasic.getEventStartAt())
                .eventEndAt(eventBasic.getEventEndAt())
                .participateNum(eventBasic.getParticipateNum())
                .location(eventBasic.getLocation())
                .categoryName(eventBasic.getCategoryName())
                .isActive(eventBasic.getIsActive())
                .content(eventDetail.getContent())
                .applyStartAt(eventDetail.getApplyStartAt())
                .applyEndAt(eventDetail.getApplyEndAt())
                .views(eventDetail.getViews())
                .tickets(tickets)
                .image(imageInfo.getImagePathFromStorage())
                .originFileName(imageInfo.getImageOriginalFileName())
            .build();
    }


    // Swagger 을 위하여 기본 생성자로 기본값 설정
    public FullEventFindByIdResponseDTO() {
        this.id = 1L;
        this.title = "String";
        this.image = "String";
        this.eventStartAt = LocalDateTime.now();
        this.eventEndAt = LocalDateTime.now();
        this.participateNum = 1L;
        this.location = "String";
        this.isActive = true;
        this.content = "String";
        this.applyStartAt = LocalDateTime.now();
        this.applyEndAt = LocalDateTime.now();
        this.views = 1L;
        this.tickets = new ArrayList<>();
    }
}
