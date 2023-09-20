package com.eventty.businessservice.event.application.service.Facade;

import com.eventty.businessservice.event.api.ApiClient;
import com.eventty.businessservice.event.api.dto.response.UserFindByIdResponseDTO;
import com.eventty.businessservice.event.application.dto.request.EventCreateRequestDTO;
import com.eventty.businessservice.event.application.dto.request.EventUpdateRequestDTO;
import com.eventty.businessservice.event.application.dto.request.TicketUpdateRequestDTO;
import com.eventty.businessservice.event.application.dto.response.*;
import com.eventty.businessservice.event.application.service.subservices.EventBasicService;
import com.eventty.businessservice.event.application.service.subservices.EventDetailService;
import com.eventty.businessservice.event.application.service.subservices.ImageService;
import com.eventty.businessservice.event.application.service.subservices.TicketService;
import com.eventty.businessservice.event.domain.Enum.Category;
import com.eventty.businessservice.event.presentation.response.ResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class EventService {
    private final EventBasicService eventBasicService;
    private final EventDetailService eventDetailService;
    private final TicketService ticketService;
    private final ImageService imageService;
    private final ApiClient apiClient;

    @Transactional(readOnly = true)
    public List<EventFullFindAllResponseDTO> findAllEvents() {
        // 이벤트 기본 정보
        List<EventBasicResponseDTO> eventBasicList = eventBasicService.findAllEvents();

        // 이벤트 이미지
        return eventBasicList.stream()
                .map(eventBasic -> {
                    ImageResponseDTO imageInfo = imageService.findImageByEventId(eventBasic.getId());
                    return EventFullFindAllResponseDTO.from(eventBasic, imageInfo);
                })
                .collect(Collectors.toList());
    }

    public EventFullFindByIdResponseDTO findEventById(Long eventId) {
        // 이벤트 기본 정보
        EventBasicResponseDTO eventBasic = eventBasicService.findEventById(eventId);

        // 이벤트 상세 정보
        EventDetailResponseDTO eventDetail = eventDetailService.findEventById(eventId);
        eventDetailService.increaseView(eventId); // 조회수 증가 (비동기)

        // 유저 상세 정보 가져오기 (API 호출) (비동기 함수 아래 배치) ( !!! 호스트 아이디는 requestParam에 담아서 보내야 됩니다.)
        Long hostId = eventBasic.getUserId();
        ResponseEntity<ResponseDTO<UserFindByIdResponseDTO>> response = apiClient.queryUserInfoApi(hostId);

        System.out.println(response);

        // 티켓 정보
        List<TicketResponseDTO> tickets = ticketService.findTicketsByEventId(eventId);

        // 남은 티켓 수 가져오기 (API 호출)

        // 이벤트 이미지
        ImageResponseDTO imageInfo = imageService.findImageByEventId(eventId);

        return EventFullFindByIdResponseDTO.from(eventBasic, eventDetail, tickets, imageInfo);
    }

    @Transactional(readOnly = true)
    public List<EventFullFindAllResponseDTO> findEventsByHostId(Long hostId) {
        // 이벤트 기본 정보
        List<EventBasicResponseDTO> eventBasicList = eventBasicService.findEventsByHostId(hostId);

        // 이벤트 이미지
        return eventBasicList.stream()
                .map(eventBasic -> {
                    ImageResponseDTO imageInfo = imageService.findImageByEventId(eventBasic.getId());
                    return EventFullFindAllResponseDTO.from(eventBasic, imageInfo);
                })
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<EventFullFindAllResponseDTO> findEventsByCategory(Category category) {
        // 이벤트 기본 정보
        List<EventBasicResponseDTO> eventBasicList = eventBasicService.findEventsByCategory(category);

        // 이벤트 이미지
        return eventBasicList.stream()
                .map(eventBasic -> {
                    ImageResponseDTO imageInfo = imageService.findImageByEventId(eventBasic.getId());
                    return EventFullFindAllResponseDTO.from(eventBasic, imageInfo);
                })
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<EventFullFindAllResponseDTO> findEventsBySearch(String keyword) {
        // 이벤트 기본 정보
        List<EventBasicResponseDTO> eventBasicList = eventBasicService.findEventsBySearch(keyword);

        // 이벤트 이미지
        return eventBasicList.stream()
                .map(eventBasic -> {
                    ImageResponseDTO imageInfo = imageService.findImageByEventId(eventBasic.getId());
                    return EventFullFindAllResponseDTO.from(eventBasic, imageInfo);
                })
                .collect(Collectors.toList());
    }

    public Long createEvent(Long userId, EventCreateRequestDTO eventCreateRequestDTO, MultipartFile image) {

        eventCreateRequestDTO.setUserId(userId);

        // 이벤트 기본 정보
        Long eventId = eventBasicService.createEvent(eventCreateRequestDTO);

        // 이벤트 상세 정보
        eventDetailService.createEventDetail(eventId, eventCreateRequestDTO);

        // 티켓 정보
        ticketService.createTickets(eventId, eventCreateRequestDTO);

        // 이벤트 이미지
        if(image != null){
            imageService.createEventImage(eventId, image);
        }

        return eventId;
    }

    public Long updateEvent(Long eventId, EventUpdateRequestDTO eventUpdateRequestDTO){

        // title, content, isActive, category 수정 가능

        // 이벤트 기본 정보 : title, category, isActive
        eventBasicService.updateEvent(eventId, eventUpdateRequestDTO);

        // 이벤트 상세 정보 : content
        eventDetailService.updateEventDetail(eventId, eventUpdateRequestDTO);

        return eventId;
    }

    public Long deleteEvent(Long eventId){
        // 이벤트 기본 정보
        Long deletedEventId = eventBasicService.deleteEvent(eventId);

        // 이벤트 상세 정보
        eventDetailService.deleteEventDetail(eventId);

        // 티켓 정보
        ticketService.deleteTickets(eventId);

        // 이벤트 이미지
        imageService.deleteEventImage(eventId);

        return eventId;
    }

    public Long updateTicket(Long eventId, TicketUpdateRequestDTO ticketUpdateRequestDTO) {
        // 티켓 정보
        return ticketService.updateTicket(eventId, ticketUpdateRequestDTO);
    }

    public Long deleteTicket(Long ticketId) {
        // 티켓 정보
        return ticketService.deleteTicket(ticketId);
    }

}
