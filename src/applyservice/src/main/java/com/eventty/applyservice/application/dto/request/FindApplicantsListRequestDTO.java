package com.eventty.applyservice.application.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter @ToString @Builder
@AllArgsConstructor
public class FindApplicantsListRequestDTO {
    private Long eventId;
    private Long state;
    private Long applyId;
    private Long priceMin;
    private Long priceMax;
    private String name;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime dateMin;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime dateMax;
    private String phone;
    private Long order;

    public FindApplicantsListRequestDTO(){
        eventId = 1L;
        state = 0L;
        applyId = 2L;
        priceMin = 100L;
        priceMax = 50000000L;
        name = "하이";
        dateMin = LocalDateTime.of(2023, 1, 5, 00, 01, 01);
        dateMax = LocalDateTime.of(2023, 12, 31, 00, 00, 01);
        phone = "010-7895-7895";
        order = 0L;
    }
}
