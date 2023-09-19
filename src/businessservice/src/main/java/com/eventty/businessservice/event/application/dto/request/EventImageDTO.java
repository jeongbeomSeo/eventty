package com.eventty.businessservice.event.application.dto.request;

import com.eventty.businessservice.event.domain.entity.EventImageEntity;
import lombok.*;

@Getter @Setter @ToString @Builder
@AllArgsConstructor @NoArgsConstructor
public class EventImageDTO {
    private Long id;
    private Long eventId;
    private String originalFileName;
    private String storedFilePath;
    private Long fileSize;

    public EventImageEntity toEntity(){
        return EventImageEntity
                .builder()
                .id(this.id)
                .eventId(this.eventId)
                .originalFileName(this.originalFileName)
                .storedFilePath(this.storedFilePath)
                .fileSize(this.fileSize)
                .build();
    }
}