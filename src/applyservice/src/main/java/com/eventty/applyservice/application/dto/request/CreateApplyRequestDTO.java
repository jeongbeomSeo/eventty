package com.eventty.applyservice.application.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter @Setter @Builder @ToString
@AllArgsConstructor @NoArgsConstructor
public class CreateApplyRequestDTO {
    @NotNull
    private Long eventId;
    @NotNull
    private Long ticketId;
    @NotNull
    private Long participateNum;
}
