package com.eventty.businessservice.event.application.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@ToString
public class EventInfoApiResponseDTO {
    private String image;               // Image(Image Path)
    private String title;               // 제목 - Event_Basic Table
    private String ticketName;          // 티켓 명(VVIP, VIP...) - Tickets Table
    private Long ticketPrice;           // 티켓 가격 - Tickets Table
    private LocalDateTime eventEndAt;   // 행사 종료 일자 - Event_Basic Table
    private Long eventId;               // Event_Basic 고유 ID

    public static EventInfoApiResponseDTO from(
            ImageResponseDTO image, EventBasicWithoutHostInfoResponseDTO eventBasic, TicketResponseDTO ticket
    ) {
        return EventInfoApiResponseDTO.builder()
                .image(image.getImagePathFromStorage())
                .title(eventBasic.getTitle())
                .ticketName(ticket.getName())
                .ticketPrice(ticket.getPrice())
                .eventEndAt(eventBasic.getEventEndAt())
                .eventId(eventBasic.getId())
                .build();
    }
}
