package com.eventty.businessservice.presentation;

import com.eventty.businessservice.application.dto.request.EventFullCreateRequestDTO;
import com.eventty.businessservice.application.dto.request.EventFullUpdateRequestDTO;
import com.eventty.businessservice.common.Enum.SuccessCode;
import com.eventty.businessservice.common.response.SuccessResponseDTO;
import com.eventty.businessservice.application.dto.response.EventFindByIdWithDetailResponseDTO;
import com.eventty.businessservice.application.dto.response.EventFindAllResponseDTO;
import com.eventty.businessservice.application.service.EventService;
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
    public ResponseEntity<SuccessResponseDTO<EventFindByIdWithDetailResponseDTO>> findEventById(@PathVariable @Min(1) Long eventId){
        // 행사 기본 정보 + 상세 정보 + 티켓
        EventFindByIdWithDetailResponseDTO data = eventService.findEventById(eventId);

        SuccessCode code = GET_EVENT_INFO_SUCCESS;
        return ResponseEntity
                .status(code.getStatus())
                .body(SuccessResponseDTO.of(data, code));
    }

    /**
     * 행사 전체 조회
     *
     */
    @GetMapping( "")
    @Operation(summary = "행사 전체 조회")
    public ResponseEntity<SuccessResponseDTO<List<EventFindAllResponseDTO>>> findAllEvents() {
        // 행사 기본 정보 (Event) 만 열거
        List<EventFindAllResponseDTO> eventList = eventService.findAllEvents();

        SuccessCode code = GET_EVENT_INFO_SUCCESS;
        return ResponseEntity
                .status(code.getStatus())
                .body(SuccessResponseDTO.of(eventList, code));
    }

    /**
     * 행사 주회
     *
     */
    @PostMapping("")
    @Operation(summary = "행사 주최")
    public ResponseEntity<SuccessResponseDTO<?>> postEvent(@RequestBody EventFullCreateRequestDTO eventFullCreateRequestDTO){

        Long newEventId = eventService.createEvent(eventFullCreateRequestDTO);

        SuccessCode code = CREATE_EVENT_SUCCESS;
        return ResponseEntity
                .status(code.getStatus())
                .body(SuccessResponseDTO.of(newEventId, code));
    }

    /**
     * 주최한 행사 수정
     *
     */
    @PutMapping(value = "/{eventId}")
    @Operation(summary = "행사 수정")
    public ResponseEntity<SuccessResponseDTO<Long>> postEvent(
            @PathVariable Long eventId,
            @RequestBody EventFullUpdateRequestDTO eventFullUpdateRequestDTO){

        Long updatedEventId = eventService.updateEvent(eventId, eventFullUpdateRequestDTO);

        SuccessCode code = UPDATE_EVENT_SUCCESS;
        return ResponseEntity
                .status(code.getStatus())
                .body(SuccessResponseDTO.of(updatedEventId, code));
    }


    /**
     * 주최한 행사 삭제
     *
     */
    @DeleteMapping("/{eventId}")
    @Operation(summary = "행사 삭제")
    public ResponseEntity<SuccessResponseDTO<?>> deleteEvent(@PathVariable @Min(1) Long eventId){

        Long deleteEventId = eventService.deleteEvent(eventId);

        SuccessCode code = DELETE_EVENT_SUCCESS;
        return ResponseEntity
                .status(code.getStatus())
                .body(SuccessResponseDTO.of(deleteEventId, code));
    }


    /**
     * 행사 카테고리별 조회
     *
     */

}
