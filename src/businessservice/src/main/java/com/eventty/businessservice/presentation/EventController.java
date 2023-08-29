package com.eventty.businessservice.presentation;

import com.eventty.businessservice.common.Enum.SuccessCode;
import com.eventty.businessservice.common.response.SuccessResponseDTO;
import com.eventty.businessservice.application.dto.response.EventFullResponseDTO;
import com.eventty.businessservice.application.dto.response.EventResponseDTO;
import com.eventty.businessservice.application.service.EventDetailService;
import com.eventty.businessservice.application.service.EventService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.eventty.businessservice.common.Enum.SuccessCode.*;

@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping( "/api/events")
@Tag(name = "Event", description = "Event API")
public class EventController {
    private final EventDetailService eventDetailService;
    private final EventService eventService;

    /**
     * 특정 행사 조회
     *
     */
    @GetMapping( "/{eventId}")
    @Operation(summary = "특정 행사 조회")
    public ResponseEntity<SuccessResponseDTO<EventFullResponseDTO>> findEventById(@PathVariable @Min(1) Long eventId){
        // 행사 기본 정보 + 상세 정보
        EventFullResponseDTO event = eventService.findEventById(eventId);

        SuccessCode code = GET_EVENT_INFO_SUCCESS;
        return ResponseEntity
                .status(code.getStatus())
                .body(SuccessResponseDTO.of(event, code));
    }

    /**
     * 행사 전체 조회
     *
     */
    @GetMapping( "")
    @Operation(summary = "행사 전체 조회")
    public ResponseEntity<SuccessResponseDTO<List<EventResponseDTO>>> findAllEvents() {
        // 행사 기본 정보 (Event) 만 열거
        List<EventResponseDTO> eventList = eventService.findAllEvents();

        SuccessCode code = GET_EVENT_INFO_SUCCESS;
        return ResponseEntity
                .status(code.getStatus())
                .body(SuccessResponseDTO.of(eventList, code));
    }

}
