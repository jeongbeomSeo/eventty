package com.eventty.businessservice.domains.event.presentation;

import com.eventty.businessservice.domains.event.application.dto.request.EventFullCreateRequestDTO;
import com.eventty.businessservice.domains.event.application.dto.request.EventFullUpdateRequestDTO;
import com.eventty.businessservice.common.Enum.ErrorCode;
import com.eventty.businessservice.common.annotation.ApiErrorCode;
import com.eventty.businessservice.common.annotation.ApiSuccessData;
import com.eventty.businessservice.common.response.SuccessResponseDTO;
import com.eventty.businessservice.domains.event.application.dto.response.EventFindByIdWithDetailResponseDTO;
import com.eventty.businessservice.domains.event.application.dto.response.EventFindAllResponseDTO;
import com.eventty.businessservice.domains.event.application.dto.response.EventWithDetailResponseDTO;
import com.eventty.businessservice.domains.event.application.service.EventService;
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
@RequestMapping( "/api/events")
@Tag(name = "Event", description = "Event API")
public class EventController {
    private final EventService eventService;

    /**
     * 특정 행사 조회
     *
     */
    @GetMapping( "/{eventId}")
    @Operation(summary = "특정 행사 조회")
    @ApiSuccessData(EventFindByIdWithDetailResponseDTO.class)
    @ApiErrorCode(ErrorCode.EVENT_NOT_FOUND)
    public ResponseEntity<SuccessResponseDTO<EventFindByIdWithDetailResponseDTO>> findEventById(@PathVariable @Min(1) Long eventId){
        // 행사 기본 정보 + 상세 정보 + 티켓
        EventFindByIdWithDetailResponseDTO data = eventService.findEventById(eventId);

        return ResponseEntity
                .status(GET_EVENT_INFO_SUCCESS.getStatus())
                .body(SuccessResponseDTO.of(data));
    }

    /**
     * 행사 전체 조회
     *
     */
    @GetMapping( "")
    @Operation(summary = "행사 전체 조회")
    @ApiSuccessData(value = EventFindAllResponseDTO.class, array = true)
    @ApiErrorCode(ErrorCode.EVENT_NOT_FOUND)
    public ResponseEntity<SuccessResponseDTO<List<EventFindAllResponseDTO>>> findAllEvents() {
        // 행사 기본 정보 (Event) 만 열거
        List<EventFindAllResponseDTO> eventList = eventService.findAllEvents();

        return ResponseEntity
                .status(GET_EVENT_INFO_SUCCESS.getStatus())
                .body(SuccessResponseDTO.of(eventList));
    }

    /**
     * 행사 주회
     *
     */
    @PostMapping("")
    @Operation(summary = "행사 주최")
    @ApiSuccessData()
    public ResponseEntity<Void> postEvent(@RequestBody EventFullCreateRequestDTO eventFullCreateRequestDTO){

        Long newEventId = eventService.createEvent(eventFullCreateRequestDTO);

        return ResponseEntity
                .status(CREATE_EVENT_SUCCESS.getStatus())
                .body(null);
                //.body(SuccessResponseDTO.of(newEventId));
    }

    /**
     * 주최한 행사 수정
     *
     */
    @PutMapping(value = "/{eventId}")
    @Operation(summary = "행사 수정")
    @ApiSuccessData()
    public ResponseEntity<SuccessResponseDTO<Long>> postEvent(
            @PathVariable Long eventId,
            @RequestBody EventFullUpdateRequestDTO eventFullUpdateRequestDTO){

        Long updatedEventId = eventService.updateEvent(eventId, eventFullUpdateRequestDTO);

        return ResponseEntity
                .status(UPDATE_EVENT_SUCCESS.getStatus())
                .body(null);
                //.body(SuccessResponseDTO.of(updatedEventId));
    }


    /**
     * 주최한 행사 삭제
     *
     */
    @DeleteMapping("/{eventId}")
    @Operation(summary = "행사 삭제")
    @ApiSuccessData()
    public ResponseEntity<SuccessResponseDTO<?>> deleteEvent(@PathVariable @Min(1) Long eventId){

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
    @GetMapping( "/category/{categoryId}")
    @Operation(summary = "카테고리 별 행사 조회")
    @ApiSuccessData(value = EventFindAllResponseDTO.class, array = true)
    @ApiErrorCode(ErrorCode.CATEGORY_NOT_FOUND)
    public ResponseEntity<SuccessResponseDTO<List<EventFindAllResponseDTO>>> findEventsByCategory(
            @PathVariable Long categoryId
    ) {
        List<EventFindAllResponseDTO> events = eventService.findEventsByCategory(categoryId);

        return ResponseEntity
                .status(GET_EVENT_INFO_SUCCESS.getStatus())
                .body(SuccessResponseDTO.of(events));
    }

}
