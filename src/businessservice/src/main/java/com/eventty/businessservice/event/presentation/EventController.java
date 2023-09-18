package com.eventty.businessservice.event.presentation;

import com.eventty.businessservice.event.application.dto.request.EventCreateRequestDTO;
import com.eventty.businessservice.event.application.dto.request.EventUpdateRequestDTO;
import com.eventty.businessservice.event.application.dto.request.TicketUpdateRequestDTO;
import com.eventty.businessservice.event.application.dto.response.EventBasicFindAllResponseDTO;
import com.eventty.businessservice.event.application.dto.response.EventWithTicketsFindByIdResponseDTO;
import com.eventty.businessservice.event.application.service.EventService;
import com.eventty.businessservice.event.domain.Enum.Category;
import com.eventty.businessservice.event.domain.Enum.ErrorCode;
import com.eventty.businessservice.event.domain.annotation.ApiErrorCode;
import com.eventty.businessservice.event.domain.annotation.ApiSuccessData;
import com.eventty.businessservice.event.presentation.response.SuccessResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.eventty.businessservice.event.domain.Enum.SuccessCode.*;

@RestController
@Validated
@RequiredArgsConstructor
@Tag(name = "Event", description = "Event API")
public class EventController {
    private final EventService eventService;

    @GetMapping( "/events/{eventId}")
    @Operation(summary = "(ALL) 특정 이벤트의 상세 정보를 가져옵니다.")
    @ApiSuccessData(EventWithTicketsFindByIdResponseDTO.class)
    @ApiErrorCode(ErrorCode.EVENT_NOT_FOUND)
    public ResponseEntity<SuccessResponseDTO<EventWithTicketsFindByIdResponseDTO>> findEventById(
            @PathVariable @Min(1) Long eventId
    ){
        // 행사 기본 정보 + 상세 정보 + 티켓
        EventWithTicketsFindByIdResponseDTO data = eventService.findEventById(eventId);

        return ResponseEntity
                .status(GET_EVENT_INFO_SUCCESS.getStatus())
                .body(SuccessResponseDTO.of(data));
    }

    @GetMapping( "/events")
    @Operation(summary = "(ALL) 전체 이벤트 리스트를 가져옵니다.")
    @ApiSuccessData(value = EventBasicFindAllResponseDTO.class, array = true)
    @ApiErrorCode(ErrorCode.EVENT_NOT_FOUND)
    public ResponseEntity<SuccessResponseDTO<List<EventBasicFindAllResponseDTO>>> findAllEvents()
    {
        // 행사 기본 정보 (Event) 만 열거
        List<EventBasicFindAllResponseDTO> eventList = eventService.findAllEvents();

        return ResponseEntity
                .status(GET_EVENT_INFO_SUCCESS.getStatus())
                .body(SuccessResponseDTO.of(eventList));
    }

    @PostMapping("/events")
    @Operation(summary = "(HOST) 이벤트의 정보를 등록하여, 새로운 이벤트를 생성합니다.")
    @ApiSuccessData()
    public ResponseEntity<Void> postEvent(
            @RequestBody @Valid EventCreateRequestDTO eventCreateRequestDTO
    ){

        Long newEventId = eventService.createEvent(eventCreateRequestDTO);

        return ResponseEntity
                .status(CREATE_EVENT_SUCCESS.getStatus())
                .body(null);
                //.body(SuccessResponseDTO.of(newEventId));
    }

    @PutMapping(value = "/events/ticket/{ticketId}")
    @Operation(summary = "(HOST) 티켓의 정보를 수정합니다.")
    @ApiSuccessData()
    public ResponseEntity<SuccessResponseDTO<Long>> updateTicket(
            @PathVariable Long ticketId,
            @RequestBody @Valid TicketUpdateRequestDTO ticketUpdateRequestDTO
    ){
        Long updatedTicketId = eventService.updateTicket(ticketId, ticketUpdateRequestDTO);

        return ResponseEntity
                .status(UPDATE_TICKET_SUCCESS.getStatus())
                //.body(null);
                .body(SuccessResponseDTO.of(updatedTicketId));
    }

    @DeleteMapping("/events/ticket/{ticketId}")
    @Operation(summary = "(HOST) 티켓을 삭제합니다.")
    @ApiSuccessData()
    public ResponseEntity<SuccessResponseDTO<?>> deleteTicket(
            @PathVariable @Min(1) Long ticketId
    ){

        Long deleteTicketId = eventService.deleteTicket(ticketId);

        return ResponseEntity
                .status(DELETE_TICKET_SUCCESS.getStatus())
                .body(null);
                //.body(SuccessResponseDTO.of(deleteTicketId));
    }

    @GetMapping( "/events/host/{hostId}")
    @Operation(summary = "(HOST) 특정 호스트가 등록한 이벤트 리스트를 가져옵니다.")
    @ApiSuccessData(value = EventBasicFindAllResponseDTO.class, array = true)
    @ApiErrorCode(ErrorCode.EVENT_NOT_FOUND)
    public ResponseEntity<SuccessResponseDTO<List<EventBasicFindAllResponseDTO>>> findEventsByHostId(
            @PathVariable @Min(1) Long hostId
    ) {
        List<EventBasicFindAllResponseDTO> events = eventService.findEventsByHostId(hostId);

        return ResponseEntity
                .status(GET_EVENT_INFO_SUCCESS.getStatus())
                .body(SuccessResponseDTO.of(events));
    }

    @PutMapping(value = "/events/{eventId}")
    @Operation(summary = "(HOST) 이벤트의 정보를 수정합니다.")
    @ApiSuccessData()
    public ResponseEntity<SuccessResponseDTO<Long>> postEvent(
            @PathVariable @Min(1) Long eventId,
            @RequestBody @Valid EventUpdateRequestDTO eventUpdateRequestDTO
    ){
        Long updatedEventId = eventService.updateEvent(eventId, eventUpdateRequestDTO);

        return ResponseEntity
                .status(UPDATE_EVENT_SUCCESS.getStatus())
                .body(null);
                //.body(SuccessResponseDTO.of(updatedEventId));
    }

    @DeleteMapping("/events/{eventId}")
    @Operation(summary = "(HOST) 이벤트를 삭제합니다.")
    @ApiSuccessData()
    public ResponseEntity<SuccessResponseDTO<?>> deleteEvent(
            @PathVariable @Min(1) Long eventId
    ){

        Long deleteEventId = eventService.deleteEvent(eventId);

        return ResponseEntity
                .status(DELETE_EVENT_SUCCESS.getStatus())
                .body(null);
                //.body(SuccessResponseDTO.of(deleteEventId));
    }

    @GetMapping( "/events/category/{category}")
    @Operation(summary = "(ALL) 이벤트를 카테고리별로 조회합니다.")
    @ApiSuccessData(value = EventBasicFindAllResponseDTO.class, array = true)
    @ApiErrorCode(ErrorCode.CATEGORY_NOT_FOUND)
    public ResponseEntity<SuccessResponseDTO<List<EventBasicFindAllResponseDTO>>> findEventsByCategory(
            @PathVariable Category category
    ) {
        List<EventBasicFindAllResponseDTO> events = eventService.findEventsByCategory(category);

        return ResponseEntity
                .status(GET_EVENT_INFO_SUCCESS.getStatus())
                .body(SuccessResponseDTO.of(events));
    }

    @GetMapping( "/events/search")
    @Operation(summary = "(ALL) 이벤트를 키워드로 검색하여, 최신순으로 가져옵니다.")
    @ApiSuccessData(value = EventBasicFindAllResponseDTO.class, array = true)
    @ApiErrorCode(ErrorCode.EVENT_NOT_FOUND)
    public ResponseEntity<SuccessResponseDTO<List<EventBasicFindAllResponseDTO>>> findEventsBySearch(
            @RequestParam String keyword
    ) {
        List<EventBasicFindAllResponseDTO> events = eventService.findEventsBySearch(keyword);

        return ResponseEntity
                .status(GET_EVENT_INFO_SUCCESS.getStatus())
                .body(SuccessResponseDTO.of(events));
    }

}
