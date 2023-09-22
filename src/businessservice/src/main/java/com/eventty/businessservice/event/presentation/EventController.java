package com.eventty.businessservice.event.presentation;

import com.eventty.businessservice.event.application.dto.request.EventCreateRequestDTO;
import com.eventty.businessservice.event.application.dto.request.EventUpdateRequestDTO;
import com.eventty.businessservice.event.application.dto.request.TicketUpdateRequestDTO;
import com.eventty.businessservice.event.application.dto.response.FullEventFindAllResponseDTO;
import com.eventty.businessservice.event.application.dto.response.FullEventFindByIdResponseDTO;
import com.eventty.businessservice.event.application.dto.response.EventInfoApiResponseDTO;
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
import java.util.Map;

import static com.eventty.businessservice.event.domain.Enum.SuccessCode.*;

@RestController
@Validated
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Event", description = "Event API")
public class EventController {

    private final EventService eventService;

    /*
    USER 권한 API
     */

    @GetMapping( "/events")
    @Operation(summary = "(ALL) 전체 이벤트 리스트를 가져옵니다.")
    @ApiSuccessData(value = FullEventFindAllResponseDTO.class, array = true)
    @ApiErrorCode(ErrorCode.EVENT_NOT_FOUND)
    public ResponseEntity<SuccessResponseDTO<List<FullEventFindAllResponseDTO>>> findAllEvents()
    {
        List<FullEventFindAllResponseDTO> events = eventService.findAllEvents();

        return ResponseEntity
                .status(GET_EVENT_INFO_SUCCESS.getStatus())
                .body(SuccessResponseDTO.of(events));
    }

    @GetMapping("/events/top10")
    @Operation(summary = "(ALL) 이벤트를 조회수, 최신순, 이벤트 마감일 가까운 순으로 각 10개 씩 가져옵니다.")
    @ApiSuccessData(value = FullEventFindAllResponseDTO.class, array = true)
    @ApiErrorCode(ErrorCode.EVENT_NOT_FOUND)
    public ResponseEntity<SuccessResponseDTO<Map<String, List<FullEventFindAllResponseDTO>>>> findEventsBySpecial() {

        Map<String, List<FullEventFindAllResponseDTO>> events = eventService.findTop10Events();

        return ResponseEntity
                .status(GET_EVENT_INFO_SUCCESS.getStatus())
                .body(SuccessResponseDTO.of(events));
    }

    @GetMapping( "/events/category/{category}")
    @Operation(summary = "(ALL) 이벤트를 카테고리별로 조회합니다.")
    @ApiSuccessData(value = FullEventFindAllResponseDTO.class, array = true)
    @ApiErrorCode(ErrorCode.EVENT_NOT_FOUND)
    public ResponseEntity<SuccessResponseDTO<List<FullEventFindAllResponseDTO>>> findEventsByCategory(
            @PathVariable Category category
    ) {
        List<FullEventFindAllResponseDTO> events = eventService.findEventsByCategory(category);

        return ResponseEntity
                .status(GET_EVENT_INFO_SUCCESS.getStatus())
                .body(SuccessResponseDTO.of(events));
    }

    @GetMapping( "/events/search")
    @Operation(summary = "(ALL) 이벤트를 키워드로 검색하여, 최신순으로 가져옵니다.")
    @ApiSuccessData(value = FullEventFindAllResponseDTO.class, array = true)
    @ApiErrorCode(ErrorCode.EVENT_NOT_FOUND)
    public ResponseEntity<SuccessResponseDTO<List<FullEventFindAllResponseDTO>>> findEventsBySearch(
            @RequestParam String keyword
    ) {
        List<FullEventFindAllResponseDTO> events = eventService.findEventsBySearch(keyword);

        return ResponseEntity
            .status(GET_EVENT_INFO_SUCCESS.getStatus())
            .body(SuccessResponseDTO.of(events));
    }

    @GetMapping( "/events/host/{hostId}")
    @Operation(summary = "(ALL) 특정 호스트가 등록한 이벤트 리스트를 가져옵니다.")
    @ApiSuccessData(value = FullEventFindAllResponseDTO.class, array = true)
    @ApiErrorCode(ErrorCode.EVENT_NOT_FOUND)
    public ResponseEntity<SuccessResponseDTO<List<FullEventFindAllResponseDTO>>> findEventsByHostId(
            @PathVariable @Min(1) Long hostId
    ) {

        List<FullEventFindAllResponseDTO> events = eventService.findEventsByHostId(hostId);

        return ResponseEntity
            .status(GET_EVENT_INFO_SUCCESS.getStatus())
            .body(SuccessResponseDTO.of(events));
    }

    @GetMapping( "/events/{eventId}")
    @Operation(summary = "(ALL) 특정 이벤트의 상세 정보를 가져옵니다.")
    @ApiSuccessData(FullEventFindByIdResponseDTO.class)
    @ApiErrorCode(ErrorCode.EVENT_NOT_FOUND)
    public ResponseEntity<SuccessResponseDTO<FullEventFindByIdResponseDTO>> findEventById(
            @PathVariable @Min(1) Long eventId
    ){
        // 상세 페이지에서 Host 정보를 위해 User Server API 호출
        FullEventFindByIdResponseDTO event = eventService.findEventById(eventId);

        return ResponseEntity
                .status(GET_EVENT_INFO_SUCCESS.getStatus())
                .body(SuccessResponseDTO.of(event));
    }


    /*
    HOST 권한 API
     */

    @GetMapping( "/events/registered")
    @Operation(summary = "(HOST) 호스트 본인이 주최한 이벤트 리스트를 조회합니다.")
    @ApiSuccessData(value = FullEventFindAllResponseDTO.class, array = true)
    @ApiErrorCode(ErrorCode.EVENT_NOT_FOUND)
    @Permission(Roles = {UserRole.HOST})
    public ResponseEntity<SuccessResponseDTO<List<FullEventFindAllResponseDTO>>> findMyEvents() {

        Long hostId = getUserIdBySecurityContextHolder();
        List<FullEventFindAllResponseDTO> events = eventService.findEventsByHostId(hostId);

        return ResponseEntity
                .status(GET_EVENT_INFO_SUCCESS.getStatus())
                .body(SuccessResponseDTO.of(events));
    }


    @PostMapping("/events")
    @Operation(summary = "(HOST) 이벤트의 정보를 등록하여, 새로운 이벤트를 생성합니다.")
    @ApiSuccessData()
    @Permission(Roles = {UserRole.HOST})
    public ResponseEntity<SuccessResponseDTO<Long>> createEvent(
            @RequestPart(value = "eventInfo") EventCreateRequestDTO eventCreateRequestDTO,
            @RequestPart(value = "image", required = false) MultipartFile image
    ){
        Long hostId = getUserIdBySecurityContextHolder();
        Long newEventId = eventService.createEvent(hostId, eventCreateRequestDTO, image);

        return ResponseEntity
            .status(CREATE_EVENT_SUCCESS.getStatus())
            .body(SuccessResponseDTO.of(newEventId));
    }

    @PatchMapping(value = "/events/{eventId}")
    @Operation(summary = "(HOST) 호스트가 본인이 주최한 이벤트의 정보를 수정합니다. - 제목, 내용, 카테고리, 활성화 여부 변경 가능")
    @ApiSuccessData()
    @Permission(Roles = {UserRole.HOST})
    public ResponseEntity<SuccessResponseDTO<Long>> updateEvent(
            @PathVariable @Min(1) Long eventId,
            @RequestBody @Valid EventUpdateRequestDTO eventUpdateRequestDTO
    ){
        Long hostId = getUserIdBySecurityContextHolder();
        Long updatedEventId = eventService.updateEvent(hostId, eventId, eventUpdateRequestDTO);

        return ResponseEntity
            .status(UPDATE_EVENT_SUCCESS.getStatus())
            .body(SuccessResponseDTO.of(updatedEventId));
    }

    @DeleteMapping("/events/{eventId}")
    @Operation(summary = "(HOST) 호스트가 본인이 주최한 이벤트를 삭제합니다.")
    @ApiSuccessData()
    @Permission(Roles = {UserRole.HOST})
    public ResponseEntity<SuccessResponseDTO<?>> deleteEvent(
            @PathVariable @Min(1) Long eventId
    ){
        Long hostId = getUserIdBySecurityContextHolder();
        Long deleteEventId = eventService.deleteEvent(hostId, eventId);

        return ResponseEntity
            .status(DELETE_EVENT_SUCCESS.getStatus())
            .body(SuccessResponseDTO.of(deleteEventId));
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
        Long hostId = getUserIdBySecurityContextHolder();
        Long updatedTicketId = eventService.updateTicket(hostId, ticketId, ticketUpdateRequestDTO);

        return ResponseEntity
            .status(UPDATE_TICKET_SUCCESS.getStatus())
            .body(SuccessResponseDTO.of(updatedTicketId));
    }

    @DeleteMapping("/events/ticket/{ticketId}")
    @Operation(summary = "(HOST) 호스트가 본인이 주최한 이벤트의 티켓을 삭제합니다.")
    @ApiSuccessData()
    @Permission(Roles = {UserRole.HOST})
    public ResponseEntity<SuccessResponseDTO<?>> deleteTicket(
            @PathVariable @Min(1) Long ticketId
    ){
        Long hostId = getUserIdBySecurityContextHolder();
        Long deleteTicketId = eventService.deleteTicket(hostId, ticketId);

        return ResponseEntity
            .status(DELETE_TICKET_SUCCESS.getStatus())
            .body(SuccessResponseDTO.of(deleteTicketId));
    }

    /*
    API 요청
     */
    @GetMapping("/api/events")
    @Operation(summary = "Apply 서버로부터의 요청 처리 - 신청한 티켓 ID 리스트를 파라미터로 보내면, 해당 티켓과 이벤트의 정보를 조회하기 위한 용도.")
    @ApiSuccessData()
    @Permission(Roles = {UserRole.USER})
    public ResponseEntity<SuccessResponseDTO<List<EventInfoApiResponseDTO>>> findEventInfoApi(
            @RequestParam List<Long> ticketIds
    ) {
        List<EventInfoApiResponseDTO> result = eventService.findByTicketIds(ticketIds);

        return ResponseEntity
                .status(FIND_EVENT_INFO_API.getStatus())
                .body(SuccessResponseDTO.of(result));
    }

    private Long getUserIdBySecurityContextHolder(){
        return Long.parseLong(UserContextHolder.getContext().getUserId());
    }

}
