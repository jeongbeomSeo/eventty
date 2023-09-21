package com.eventty.businessservice.application;

import com.eventty.businessservice.event.application.dto.request.EventUpdateRequestDTO;
import com.eventty.businessservice.event.application.dto.response.FullEventFindByIdResponseDTO;
import com.eventty.businessservice.event.application.service.subservices.EventDetailService;
import com.eventty.businessservice.event.domain.entity.EventDetailEntity;
import com.eventty.businessservice.event.domain.entity.EventBasicEntity;
import com.eventty.businessservice.event.domain.entity.TicketEntity;
import com.eventty.businessservice.event.domain.repository.EventDetailRepository;
import com.eventty.businessservice.event.domain.repository.EventBasicRepository;
import com.eventty.businessservice.event.domain.exception.EventNotFoundException;
import com.eventty.businessservice.event.domain.repository.TicketRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EventDetailServiceTest {

    @Mock
    private EventBasicRepository eventBasicRepository;
    @Mock
    private EventDetailRepository eventDetailRepository;

    @Mock
    private TicketRepository ticketRepository;

    @InjectMocks
    private EventDetailService eventDetailService;

    @Test
    @DisplayName("존재하는 이벤트 조회 테스트")
    public void findEventByIdTest_ExistingEvent() {
        // Given
        Long eventId = 1L;
        FullEventFindByIdResponseDTO mockEvent = createEventWithDetailDAO(eventId);
        List<TicketEntity> mockTickets = createTickets();

        when(eventBasicRepository.selectEventWithDetailById(eventId)).thenReturn(mockEvent);
        when(ticketRepository.selectTicketByEventId(eventId)).thenReturn(mockTickets);

        // When
        FullEventFindByIdResponseDTO responseDTO = eventDetailService.findEventById(eventId);

        // Then
        assertEquals(mockEvent.getId(), responseDTO.getId());
        assertEquals(mockEvent.getTitle(), responseDTO.getTitle());
        assertEquals(mockEvent.getContent(), responseDTO.getContent());

        verify(eventBasicRepository, times(1)).selectEventWithDetailById(eventId);
    }

    @Test
    @DisplayName("존재하지 않는 이벤트 조회 시 예외 테스트")
    public void findEventByIdTest_NonExistingEvent() {
        // Given
        Long eventId = 1L;
        when(eventBasicRepository.selectEventWithDetailById(eventId)).thenReturn(null);

        // When & Then
        assertThrows(EventNotFoundException.class, () -> eventDetailService.findEventById(eventId));
        verify(eventBasicRepository, times(1)).selectEventWithDetailById(eventId);
    }

    @Test
    @DisplayName("이벤트 삭제 테스트")
    public void deleteEventTest(){
        // Given
        Long eventId = 1L;
        when(eventBasicRepository.deleteEvent(eventId)).thenReturn(1L);
        when(eventDetailRepository.deleteEventDetail(eventId)).thenReturn(1L);
        when(ticketRepository.deleteTicketsByEventId(eventId)).thenReturn(1L);

        // When
        eventDetailService.deleteEvent(eventId);

        // Then
        assertNull(eventBasicRepository.selectEventById(eventId));
        verify(eventBasicRepository, times(1)).deleteEvent(eventId);
        verify(eventDetailRepository, times(1)).deleteEventDetail(eventId);
    }

    @Test
    @DisplayName("이벤트 수정 테스트")
    public void updateEventTest() {
        // Given
        Long eventId = 1L;
        EventBasicEntity existingEvent = createEventEntity(eventId);

        EventUpdateRequestDTO updateRequestDTO = EventUpdateRequestDTO.builder()
                .title("Updated Title")
                .imageId(1L)
                .content("Updated Content")
                .build();

        EventDetailEntity existingEventDetail = EventDetailEntity.builder()
                .id(eventId)
                .content("Old Content")
                .build();

        when(eventBasicRepository.selectEventById(eventId)).thenReturn(existingEvent);
        when(eventDetailRepository.selectEventDetailById(eventId)).thenReturn(existingEventDetail);

        // When
        eventDetailService.updateEvent(eventId, updateRequestDTO);

        // Then
        verify(eventBasicRepository, times(1)).updateEvent(any(EventBasicEntity.class));
        verify(eventDetailRepository, times(1)).updateEventDetail(any(EventDetailEntity.class));
    }

    private static EventBasicEntity createEventEntity(Long i){
        return EventBasicEntity.builder()
                .id(i)
                .userId(i)
                .title("Sample Event")
                .image("sample.jpg")
                .eventStartAt(Timestamp.valueOf("2023-08-21 10:00:00").toLocalDateTime())
                .eventEndAt(Timestamp.valueOf("2023-08-21 15:00:00").toLocalDateTime())
                .participateNum(100L)
                .location("Sample Location")
                .category(1L)
                .isActive(true)
                .isDeleted(false)
                .build();
    }

    private static FullEventFindByIdResponseDTO createEventWithDetailDAO(Long id){
        return FullEventFindByIdResponseDTO.builder()
                .id(id)
                .userId(1L)
                .title("Sample Event")
                .image("sample.jpg")
                .eventStartAt(Timestamp.valueOf("2023-08-21 10:00:00").toLocalDateTime())
                .eventEndAt(Timestamp.valueOf("2023-08-21 15:00:00").toLocalDateTime())
                .participateNum(100L)
                .location("Sample Location")
                .category(1L)
                .isActive(true)
                .isDeleted(false)
                .content("Sample content")
                .applyStartAt(Timestamp.valueOf("2023-08-21 10:00:00").toLocalDateTime())
                .applyEndAt(Timestamp.valueOf("2023-08-21 15:00:00").toLocalDateTime())
                .views(100L)
                .build();
    }

    private List<TicketEntity> createTickets() {
        List<TicketEntity> tickets = new ArrayList<>();

        // 티켓 1
        TicketEntity ticket1 = TicketEntity.builder()
                .id(1L)
                .name("Ticket 1")
                .price(2000L)
                .quantity(50L)
                .eventId(1L)
                .is_deleted(false)
                .build();

        // 티켓 2
        TicketEntity ticket2 = TicketEntity.builder()
                .id(2L)
                .name("Ticket 2")
                .price(3000L)
                .quantity(30L)
                .eventId(1L)
                .is_deleted(false)
                .build();

        tickets.add(ticket1);
        tickets.add(ticket2);

        return tickets;
    }


}
