package com.eventty.businessservice.domains.event.presentation;

import com.eventty.businessservice.common.SuccessCode;
import com.eventty.businessservice.common.dto.SuccessResponseDTO;
import com.eventty.businessservice.domains.event.application.dto.EventDetailResponseDTO;
import com.eventty.businessservice.domains.event.application.dto.EventFullResponseDTO;
import com.eventty.businessservice.domains.event.application.dto.EventResponseDTO;
import com.eventty.businessservice.domains.event.application.service.EventDetailService;
import com.eventty.businessservice.domains.event.application.service.EventService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping( "/api/events")
@Tag(name = "Event", description = "Event API")
public class EventController {
    private final EventDetailService eventDetailService;
    private final EventService eventService;

    /**
     * 특정 행사 조회
     *
     * @return SuccessResponseDTO<EventFullResponseDTO>
     */
    @GetMapping( "/{eventId}")
    @Operation(summary = "특정 행사 조회")
    public SuccessResponseDTO<EventFullResponseDTO> findEventById(@PathVariable @Valid Long eventId){
        // 행사 기본 정보 (Event)
        EventResponseDTO event = eventService.findEventById(eventId);

        // 행사 상세 정보 (EventDetail)
        EventDetailResponseDTO eventDetail = eventDetailService.findEventDetailById(eventId);

        // 행사 전체 정보 (기본 정보 + 상세 정보)
        EventFullResponseDTO eventFullResponseDTO = new EventFullResponseDTO(event, eventDetail);

        return SuccessResponseDTO.of(eventFullResponseDTO, SuccessCode.GET_EVENT_INFO_SUCCESS);
    }

    /**
     * 행사 전체 조회
     *
     * @return SuccessResponseDTO<List<EventResponseDTO>>
     */
    @GetMapping( "")
    @Operation(summary = "행사 전체 조회")
    public SuccessResponseDTO<List<EventResponseDTO>> findAllEvents() {
        // 행사 기본 정보 (Event) 만 열거
        List<EventResponseDTO> eventList = eventService.findAllEvents();

        return SuccessResponseDTO.of(eventList, SuccessCode.GET_EVENT_INFO_SUCCESS);
    }

}
