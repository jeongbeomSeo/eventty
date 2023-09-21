package com.eventty.businessservice.event.application.service.Facade;

import com.eventty.businessservice.event.api.ApiClient;
import com.eventty.businessservice.event.api.dto.response.HostFindByIdResponseDTO;
import com.eventty.businessservice.event.application.dto.request.EventCreateRequestDTO;
import com.eventty.businessservice.event.application.dto.request.EventUpdateRequestDTO;
import com.eventty.businessservice.event.application.dto.request.TicketUpdateRequestDTO;
import com.eventty.businessservice.event.application.dto.response.*;
import com.eventty.businessservice.event.application.service.subservices.EventBasicService;
import com.eventty.businessservice.event.application.service.subservices.EventDetailService;
import com.eventty.businessservice.event.application.service.subservices.ImageService;
import com.eventty.businessservice.event.application.service.subservices.TicketService;
import com.eventty.businessservice.event.domain.Enum.Category;
import com.eventty.businessservice.event.domain.entity.TicketEntity;
import com.eventty.businessservice.event.presentation.response.ResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    public List<FullEventFindAllResponseDTO> findAllEvents() {
        // 이벤트 기본 정보
        List<EventBasicResponseDTO> eventBasicList = eventBasicService.findAllEvents();

        // 이벤트 이미지
        return mapEventBasicListToFullResponseDTO(eventBasicList);
    }

    @Transactional(readOnly = true)
    public Map<String, List<FullEventFindAllResponseDTO>> findTop10Events() {
        Map<String, List<FullEventFindAllResponseDTO>> result = new HashMap<>();

        List<String> criteriaList = Arrays.asList("Views", "CreatedAt", "ApplyEndAt");

        for (String criteria : criteriaList) {
            String key = "Top10" + criteria;
            List<Long> eventIds = getEventIdsByCriteria(criteria);
            List<FullEventFindAllResponseDTO> eventFullList = mapEventIdListToEventFullFindAllResponseDTO(eventIds);
            result.put(key, eventFullList);
        }

        return result;
    }

    public FullEventFindByIdResponseDTO findEventById(Long eventId) {
        // 이벤트 기본 정보
        EventBasicResponseDTO eventBasic = eventBasicService.findEventById(eventId);

        // 이벤트 상세 정보
        EventDetailResponseDTO eventDetail = eventDetailService.findEventById(eventId);
        eventDetailService.increaseView(eventId); // 조회수 증가 (비동기)

        // API 호출은 호출하는 서버에 구현만 되면 바로 사용 가능
        // 유저 상세 정보 가져오기 (API 호출) (비동기 함수 아래 배치) ( !!! 호스트 아이디는 requestParam에 담아서 보내야 됩니다.)
        Long hostId = eventBasic.getUserId();
        ResponseEntity<ResponseDTO<HostFindByIdResponseDTO>> response = apiClient.queryUserInfoApi(hostId);

        // 티켓 정보
        List<TicketResponseDTO> tickets = ticketService.findTicketsByEventId(eventId);

        // 남은 티켓 수 가져오기 (API 호출)

        // 이벤트 이미지
        ImageResponseDTO imageInfo = imageService.findImageByEventId(eventId);

        return FullEventFindByIdResponseDTO.of(eventBasic, eventDetail, tickets, imageInfo);
    }

    @Transactional(readOnly = true)
    public List<FullEventFindAllResponseDTO> findEventsByHostId(Long hostId) {
        // 이벤트 기본 정보
        List<EventBasicResponseDTO> eventBasicList = eventBasicService.findEventsByHostId(hostId);

        // 이벤트 이미지
        return mapEventBasicListToFullResponseDTO(eventBasicList);
    }

    @Transactional(readOnly = true)
    public List<FullEventFindAllResponseDTO> findEventsByCategory(Category category) {
        // 이벤트 기본 정보
        List<EventBasicResponseDTO> eventBasicList = eventBasicService.findEventsByCategory(category);

        // 이벤트 이미지
        return mapEventBasicListToFullResponseDTO(eventBasicList);
    }

    @Transactional(readOnly = true)
    public List<FullEventFindAllResponseDTO> findEventsBySearch(String keyword) {
        // 이벤트 기본 정보
        List<EventBasicResponseDTO> eventBasicList = eventBasicService.findEventsBySearch(keyword);

        // 이벤트 이미지
        return mapEventBasicListToFullResponseDTO(eventBasicList);
    }

    public Long createEvent(Long hostId, EventCreateRequestDTO eventCreateRequestDTO, MultipartFile image) {
        // 이벤트의 호스트 등록
        eventCreateRequestDTO.setUserId(hostId);

        // 이벤트 기본 정보
        Long eventId = eventBasicService.createEvent(eventCreateRequestDTO);

        // 이벤트 상세 정보
        eventDetailService.createEventDetail(eventId, eventCreateRequestDTO);

        // 티켓 정보
        ticketService.createTickets(eventId, eventCreateRequestDTO);

        // 이벤트 이미지
        if(image != null){
            imageService.uploadEventImage(eventId, image);
        }

        return eventId;
    }

    public Long updateEvent(Long hostId, Long eventId, EventUpdateRequestDTO eventUpdateRequestDTO){
        // 호스트가 주최한 이벤트인지 확인
        eventBasicService.checkHostId(hostId, eventId);

        // 이벤트 기본 정보 : title, category, isActive 수정 가능
        eventBasicService.updateEvent(eventId, eventUpdateRequestDTO);

        // 이벤트 상세 정보 : content 수정 가능
        eventDetailService.updateEventDetail(eventId, eventUpdateRequestDTO);

        return eventId;
    }

    public Long deleteEvent(Long hostId, Long eventId){
        // 호스트가 주최한 이벤트인지 확인
        eventBasicService.checkHostId(hostId, eventId);

        // 이벤트 기본 정보
        eventBasicService.deleteEvent(eventId);

        // 이벤트 상세 정보
        eventDetailService.deleteEventDetail(eventId);

        // 티켓 정보
        ticketService.deleteTickets(eventId);

        // 이벤트 이미지
        imageService.deleteEventImage(eventId);

        return eventId;
    }

    public Long updateTicket(Long hostId, Long eventId, TicketUpdateRequestDTO ticketUpdateRequestDTO) {
        // 호스트가 주최한 이벤트인지 확인
        eventBasicService.checkHostId(hostId, eventId);

        // 티켓 정보
        return ticketService.updateTicket(eventId, ticketUpdateRequestDTO);
    }

    public Long deleteTicket(Long hostId, Long ticketId) {
        // 호스트가 주최한 이벤트의 티켓인지 확인
        checkTicketHostId(hostId, ticketId);

        // 티켓 정보
        TicketEntity ticket = ticketService.deleteTicket(ticketId);

        // 티켓 삭제된 만큼 행사 인원 수 감소
        eventBasicService.subtractParticipateNum(ticket.getEventId(), ticket.getQuantity());

        return ticketId;
    }

    public List<EventInfoResponseDTO> findByTicketIds(List<Long> ticketIds) {

        List<TicketResponseDTO> ticketList = ticketService.findTicketsByTicketIdList(ticketIds);

        return ticketList.stream()
                .map(ticket -> {
                    EventBasicResponseDTO eventBasic = eventBasicService.findEventById(ticket.getEventId());
                    ImageResponseDTO imageInfo = imageService.findImageByEventId(ticket.getEventId());
                    return EventInfoResponseDTO.of(imageInfo, eventBasic, ticket);
                })
                .toList();
    }

    private List<Long> getEventIdsByCriteria(String criteria) {
        return switch (criteria) {
            case "Views" -> eventDetailService.findTop10EventsIdByViews();
            case "CreatedAt" -> eventDetailService.findTop10EventsIdByCreatedAt();
            case "ApplyEndAt" -> eventDetailService.findTop10EventsIdByApplyEndAt();
            default -> throw new IllegalArgumentException("Invalid criteria: " + criteria);
        };
    }

    private List<FullEventFindAllResponseDTO> mapEventIdListToEventFullFindAllResponseDTO(List<Long> eventIds) {
        List<EventBasicResponseDTO> eventBasicList = eventBasicService.findEventsByIdList(eventIds);
        return eventBasicList.stream()
                .map(eventBasic -> {
                    ImageResponseDTO imageInfo = imageService.findImageByEventId(eventBasic.getId());
                    return FullEventFindAllResponseDTO.of(eventBasic, imageInfo);
                })
                .toList();
    }

    private List<FullEventFindAllResponseDTO> mapEventBasicListToFullResponseDTO(List<EventBasicResponseDTO> eventBasicList) {
        return eventBasicList.stream()
                .map(eventBasic -> {
                    ImageResponseDTO imageInfo = imageService.findImageByEventId(eventBasic.getId());
                    return FullEventFindAllResponseDTO.of(eventBasic, imageInfo);
                })
                .collect(Collectors.toList());
    }

    private void checkTicketHostId(Long hostId, Long ticketId) {
        Long eventIdOfTicket = ticketService.findEventIdByTicketId(ticketId);
        eventBasicService.checkHostId(hostId, eventIdOfTicket);
    }

}
