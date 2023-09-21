package com.eventty.businessservice.event.presentation;

import com.eventty.businessservice.event.application.dto.request.EventCreateRequestDTO;
import com.eventty.businessservice.event.application.dto.request.EventUpdateRequestDTO;
import com.eventty.businessservice.event.application.dto.request.TicketUpdateRequestDTO;
import com.eventty.businessservice.event.application.dto.response.EventFullFindAllResponseDTO;
import com.eventty.businessservice.event.application.dto.response.EventFullFindByIdResponseDTO;
import com.eventty.businessservice.event.application.dto.response.FindEventInfoResponseDTO;
import com.eventty.businessservice.event.application.service.Facade.EventService;
import com.eventty.businessservice.event.domain.Enum.Category;
import com.eventty.businessservice.event.domain.Enum.ErrorCode;
import com.eventty.businessservice.event.domain.Enum.UserRole;
import com.eventty.businessservice.event.domain.annotation.ApiErrorCode;
import com.eventty.businessservice.event.domain.annotation.ApiSuccessData;
import com.eventty.businessservice.event.domain.annotation.Permission;
import com.eventty.businessservice.event.infrastructure.contextholder.UserContextHolder;
import com.eventty.businessservice.event.presentation.response.SuccessResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.eventty.businessservice.event.domain.Enum.SuccessCode.*;

@RestController
@Validated
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Event", description = "Event API")
public class EventController {

    private final EventService eventService;

    @GetMapping( "/events/{eventId}")
    @Operation(summary = "(ALL) 특정 이벤트의 상세 정보를 가져옵니다.")
    @ApiSuccessData(EventFullFindByIdResponseDTO.class)
    @ApiErrorCode(ErrorCode.EVENT_NOT_FOUND)
    @Permission(Roles = {UserRole.USER, UserRole.HOST})
    public ResponseEntity<SuccessResponseDTO<EventFullFindByIdResponseDTO>> findEventById(
            @PathVariable @Min(1) Long eventId
    ){
        EventFullFindByIdResponseDTO event = eventService.findEventById(eventId);

        return ResponseEntity
                .status(GET_EVENT_INFO_SUCCESS.getStatus())
                .body(SuccessResponseDTO.of(event));
    }


    @GetMapping( "/events")
    @Operation(summary = "(ALL) 전체 이벤트 리스트를 가져옵니다.")
    @ApiSuccessData(value = EventFullFindAllResponseDTO.class, array = true)
    @ApiErrorCode(ErrorCode.EVENT_NOT_FOUND)
    @Permission(Roles = {UserRole.USER, UserRole.HOST})
    public ResponseEntity<SuccessResponseDTO<List<EventFullFindAllResponseDTO>>> findAllEvents()
    {

        List<EventFullFindAllResponseDTO> events = eventService.findAllEvents();

        return ResponseEntity
                .status(GET_EVENT_INFO_SUCCESS.getStatus())
                .body(SuccessResponseDTO.of(events));
    }

    @GetMapping( "/events/category/{category}")
    @Operation(summary = "(ALL) 이벤트를 카테고리별로 조회합니다.")
    @ApiSuccessData(value = EventFullFindAllResponseDTO.class, array = true)
    @ApiErrorCode(ErrorCode.CATEGORY_NOT_FOUND)
    @Permission(Roles = {UserRole.USER, UserRole.HOST})
    public ResponseEntity<SuccessResponseDTO<List<EventFullFindAllResponseDTO>>> findEventsByCategory(
            @PathVariable Category category
    ) {
        List<EventFullFindAllResponseDTO> events = eventService.findEventsByCategory(category);

        return ResponseEntity
                .status(GET_EVENT_INFO_SUCCESS.getStatus())
                .body(SuccessResponseDTO.of(events));
    }

    @GetMapping( "/events/search")
    @Operation(summary = "(ALL) 이벤트를 키워드로 검색하여, 최신순으로 가져옵니다.")
    @ApiSuccessData(value = EventFullFindAllResponseDTO.class, array = true)
    @ApiErrorCode(ErrorCode.EVENT_NOT_FOUND)
    @Permission(Roles = {UserRole.USER, UserRole.HOST})
    public ResponseEntity<SuccessResponseDTO<List<EventFullFindAllResponseDTO>>> findEventsBySearch(
            @RequestParam String keyword
    ) {
        List<EventFullFindAllResponseDTO> events = eventService.findEventsBySearch(keyword);

        return ResponseEntity
            .status(GET_EVENT_INFO_SUCCESS.getStatus())
            .body(SuccessResponseDTO.of(events));
    }

    @GetMapping( "/events/host/{hostId}")
    @Operation(summary = "(ALL) 특정 호스트가 등록한 이벤트 리스트를 가져옵니다.")
    @ApiSuccessData(value = EventFullFindAllResponseDTO.class, array = true)
    @ApiErrorCode(ErrorCode.EVENT_NOT_FOUND)
    @Permission(Roles = {UserRole.USER, UserRole.HOST})
    public ResponseEntity<SuccessResponseDTO<List<EventFullFindAllResponseDTO>>> findEventsByHostId() {

        Long hostId = UserContextHolder.getContext().userIdTypeLong();

        List<EventFullFindAllResponseDTO> events = eventService.findEventsByHostId(hostId);

        return ResponseEntity
            .status(GET_EVENT_INFO_SUCCESS.getStatus())
            .body(SuccessResponseDTO.of(events));
    }

    @PostMapping("/events")
    @Operation(summary = "(HOST) 이벤트의 정보를 등록하여, 새로운 이벤트를 생성합니다.")
    @ApiSuccessData()
    @Permission(Roles = {UserRole.HOST})
    public ResponseEntity<Void> createNewEvent(
            @RequestPart(value = "eventInfo") EventCreateRequestDTO eventCreateRequestDTO,
            @RequestPart(value = "image", required = false) MultipartFile image
    ){
        // [수정 필요]
        // 추후 userId 는 Token 정보에서 가져오도록 변경
        Long userId = UserContextHolder.getContext().userIdTypeLong();

        Long newEventId = eventService.createEvent(userId, eventCreateRequestDTO, image);
        log.info("[createNewEvent] newEventId : {}", newEventId);

        return ResponseEntity
            .status(CREATE_EVENT_SUCCESS.getStatus())
            .body(null);
            //.body(SuccessResponseDTO.of(newEventId));
    }

    @PatchMapping(value = "/events/{eventId}")
    @Operation(summary = "(HOST) 호스트가 본인이 주최한 이벤트의 정보를 수정합니다. - 제목, 내용, 카테고리 변경 가능")
    @ApiSuccessData()
    @Permission(Roles = {UserRole.HOST})
    public ResponseEntity<SuccessResponseDTO<Long>> postEvent(
            @PathVariable @Min(1) Long eventId,
            @RequestBody @Valid EventUpdateRequestDTO eventUpdateRequestDTO
    ){
        // [수정 필요]
        // HOST 본인이 주최한 이벤트만 수정 가능하도록 변경

        Long updatedEventId = eventService.updateEvent(eventId, eventUpdateRequestDTO);
        log.info("[postEvent] updatedEventId : {}", updatedEventId);

        return ResponseEntity
            .status(UPDATE_EVENT_SUCCESS.getStatus())
            .body(SuccessResponseDTO.of(updatedEventId));
            //.body(null);
    }

    @DeleteMapping("/events/{eventId}")
    @Operation(summary = "(HOST) 호스트가 본인이 주최한 이벤트를 삭제합니다.")
    @ApiSuccessData()
    @Permission(Roles = {UserRole.HOST})
    public ResponseEntity<SuccessResponseDTO<?>> deleteEvent(
            @PathVariable @Min(1) Long eventId
    ){
        // [수정 필요]
        // HOST 본인이 주최한 이벤트만 삭제 가능하도록 변경

        Long deleteEventId = eventService.deleteEvent(eventId);
        log.info("[deleteEvent] deleteEventId : {}", deleteEventId);

        return ResponseEntity
            .status(DELETE_EVENT_SUCCESS.getStatus())
            .body(SuccessResponseDTO.of(deleteEventId));
            //.body(null);
    }

    /*
    티켓 관련 API
     */

    @PatchMapping(value = "/events/ticket/{ticketId}")
    @Operation(summary = "(HOST) 호스트가 본인이 주최한 이벤트의 티켓 정보를 수정합니다. - 티켓 내용, 가격, 카테고리 수정 가능")
    @ApiSuccessData()
    @Permission(Roles = {UserRole.HOST})
    public ResponseEntity<SuccessResponseDTO<Long>> updateTicket(
            @PathVariable Long ticketId,
            @RequestBody @Valid TicketUpdateRequestDTO ticketUpdateRequestDTO
    ){
        // [수정 필요]
        // HOST 본인이 주최한 이벤트의 티켓만 수정 가능하도록 변경

        Long updatedTicketId = eventService.updateTicket(ticketId, ticketUpdateRequestDTO);
        log.info("[updateTicket] updatedTicketId : {}", updatedTicketId);

        return ResponseEntity
            .status(UPDATE_TICKET_SUCCESS.getStatus())
            .body(SuccessResponseDTO.of(updatedTicketId));
            //.body(null);
    }

    @DeleteMapping("/events/ticket/{ticketId}")
    @Operation(summary = "(HOST) 호스트가 본인이 주최한 이벤트의 티켓을 삭제합니다.")
    @ApiSuccessData()
    @Permission(Roles = {UserRole.HOST})
    public ResponseEntity<SuccessResponseDTO<?>> deleteTicket(
            @PathVariable @Min(1) Long ticketId
    ){
        // [수정 필요]
        // HOST 본인이 주최한 이벤트의 티켓만 삭제 가능하도록 변경

        Long deleteTicketId = eventService.deleteTicket(ticketId);
        log.info("[deleteTicket] deleteTicketId : {}", deleteTicketId);

        return ResponseEntity
            .status(DELETE_TICKET_SUCCESS.getStatus())
            .body(SuccessResponseDTO.of(deleteTicketId));
            //.body(null);
    }


    /*
    API 요청
     */
    @GetMapping("/api/events")
    @Operation(summary = "Apply 서버로부터의 요청 처리")
    @ApiSuccessData()
    @Permission(Roles = {/*UserRole.HOST,*/ UserRole.USER})
    public ResponseEntity<SuccessResponseDTO<List<FindEventInfoResponseDTO>>> findEventInfoApi(
            @RequestParam List<String> eventId,
            @RequestParam List<String> ticketId
    ) {

        List<FindEventInfoResponseDTO> result = null; // eventService.findEventInfoApi(); // 네이밍이 좀 별로인데, 이게 정확히 어디에 쓰이는지 몰라서 이름을 재대로 못 붙이겠다


        return ResponseEntity
                .status(FIND_EVENT_INFO_API.getStatus())
                .body(SuccessResponseDTO.of(result));
    }

}
