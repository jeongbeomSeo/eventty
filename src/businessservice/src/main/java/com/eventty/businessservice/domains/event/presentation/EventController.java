package com.eventty.businessservice.domains.event.presentation;

import com.eventty.businessservice.domains.event.application.dto.request.EventCreateRequestDTO;
import com.eventty.businessservice.domains.event.application.dto.request.EventUpdateRequestDTO;
import com.eventty.businessservice.common.Enum.ErrorCode;
import com.eventty.businessservice.common.annotation.ApiErrorCode;
import com.eventty.businessservice.common.annotation.ApiSuccessData;
import com.eventty.businessservice.common.response.SuccessResponseDTO;
import com.eventty.businessservice.domains.event.application.dto.response.EventWithTicketsFindByIdResponseDTO;
import com.eventty.businessservice.domains.event.application.dto.response.EventBasicFindAllResponseDTO;
import com.eventty.businessservice.domains.event.application.Facade.EventService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.eventty.businessservice.common.Enum.SuccessCode.*;

@RestController
@Validated
@RequiredArgsConstructor
@Tag(name = "Event", description = "Event API")
public class EventController {
    private final EventService eventService;

    /**
     * 특정 행사 조회
     *
     */
    @GetMapping( "/events/{eventId}")
    @Operation(summary = "특정 행사 조회")
    @ApiSuccessData(EventWithTicketsFindByIdResponseDTO.class)
    @ApiErrorCode(ErrorCode.EVENT_NOT_FOUND)
    public ResponseEntity<SuccessResponseDTO<EventWithTicketsFindByIdResponseDTO>> findEventById(@PathVariable @Min(1) Long eventId
    ){
        // 행사 기본 정보 + 상세 정보 + 티켓
        EventWithTicketsFindByIdResponseDTO data = eventService.findEventById(eventId);

        return ResponseEntity
                .status(GET_EVENT_INFO_SUCCESS.getStatus())
                .body(SuccessResponseDTO.of(data));
    }

    /**
     * 행사 전체 조회
     *
     */
    @GetMapping( "/events")
    @Operation(summary = "행사 전체 조회")
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

    /**
     * 행사 주회
     *
     */
    @PostMapping("/events")
    @Operation(summary = "행사 주최")
    @ApiSuccessData()
    public ResponseEntity<Void> postEvent(@RequestBody EventCreateRequestDTO eventCreateRequestDTO
    ){

        Long newEventId = eventService.createEvent(eventCreateRequestDTO);

        return ResponseEntity
                .status(CREATE_EVENT_SUCCESS.getStatus())
                .body(null);
                //.body(SuccessResponseDTO.of(newEventId));
    }

    /**
     * 주최한 행사 수정
     *
     */
    @PutMapping(value = "/events/{eventId}")
    @Operation(summary = "행사 수정")
    @ApiSuccessData()
    public ResponseEntity<SuccessResponseDTO<Long>> postEvent(
            @PathVariable Long eventId,
            @RequestBody EventUpdateRequestDTO eventUpdateRequestDTO
    ){

        Long updatedEventId = eventService.updateEvent(eventId, eventUpdateRequestDTO);

        return ResponseEntity
                .status(UPDATE_EVENT_SUCCESS.getStatus())
                .body(null);
                //.body(SuccessResponseDTO.of(updatedEventId));
    }


    /**
     * 주최한 행사 삭제
     *
     */
    @DeleteMapping("/events/{eventId}")
    @Operation(summary = "행사 삭제")
    @ApiSuccessData()
    public ResponseEntity<SuccessResponseDTO<?>> deleteEvent(@PathVariable @Min(1) Long eventId
    ){

        Long deleteEventId = eventService.deleteEvent(eventId);

        return ResponseEntity
                .status(DELETE_EVENT_SUCCESS.getStatus())
                .body(null);
                //.body(SuccessResponseDTO.of(deleteEventId));
    }


    /**
     * 행사 카테고리별 조회
     *
     */
    @GetMapping( "/events/category/{categoryId}")
    @Operation(summary = "카테고리 별 행사 조회")
    @ApiSuccessData(value = EventBasicFindAllResponseDTO.class, array = true)
    @ApiErrorCode(ErrorCode.CATEGORY_NOT_FOUND)
    public ResponseEntity<SuccessResponseDTO<List<EventBasicFindAllResponseDTO>>> findEventsByCategory(
            @PathVariable Long categoryId
    ) {
        List<EventBasicFindAllResponseDTO> events = eventService.findEventsByCategory(categoryId);

        return ResponseEntity
                .status(GET_EVENT_INFO_SUCCESS.getStatus())
                .body(SuccessResponseDTO.of(events));
    }

    /**
     * 행사 검색
     *
     */
    @GetMapping( "/events/search")
    @Operation(summary = "행사 검색")
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
