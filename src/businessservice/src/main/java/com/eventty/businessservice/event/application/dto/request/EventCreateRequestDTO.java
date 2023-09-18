package com.eventty.businessservice.event.application.dto.request;

import com.eventty.businessservice.event.domain.entity.EventDetailEntity;
import com.eventty.businessservice.event.domain.entity.EventBasicEntity;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
public class EventCreateRequestDTO {
    @NotNull
    private Long userId;
    @NotBlank
    private String title;
    private String image;
    @FutureOrPresent
    private LocalDateTime eventStartAt;
    @FutureOrPresent
    private LocalDateTime eventEndAt;
    private Long participateNum;
    @NotBlank
    private String location;
    @Min(1)
    private Long category;
    @NotBlank
    private String content;
    @FutureOrPresent
    private LocalDateTime applyStartAt;
    @FutureOrPresent
    private LocalDateTime applyEndAt;

    private List<TicketCreateRequestDTO> tickets;

    public EventBasicEntity toEventEntity(){
        return EventBasicEntity.builder()
                .userId(userId)
                .title(title)
                .image(image)
                .eventStartAt(eventStartAt)
                .eventEndAt(eventEndAt)
                .participateNum(0L)
                .location(location)
                .category(category)
                .isActive(true)
                .isDeleted(false)
                .build();
    }

    public EventDetailEntity toEventDetailEntity(Long id) {
        return EventDetailEntity.builder()
                .id(id)
                .content(content)
                .applyStartAt(applyStartAt)
                .applyEndAt(applyEndAt)
                .createDate(LocalDateTime.now())
                .updateDate(null)
                .deleteDate(null)
                .build();
    }

}
