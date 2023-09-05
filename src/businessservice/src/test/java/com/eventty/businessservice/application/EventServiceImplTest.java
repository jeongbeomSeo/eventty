package com.eventty.businessservice.application;

import com.eventty.businessservice.application.dto.request.EventFullUpdateRequestDTO;
import com.eventty.businessservice.application.dto.response.EventFindByIdWithDetailResponseDTO;
import com.eventty.businessservice.application.dto.response.EventFindAllResponseDTO;
import com.eventty.businessservice.application.serviceImpl.EventServiceImpl;
import com.eventty.businessservice.domain.entity.EventDetailEntity;
import com.eventty.businessservice.domain.entity.EventEntity;
import com.eventty.businessservice.domain.entity.TicketEntity;
import com.eventty.businessservice.domain.exception.CategoryNotFoundException;
import com.eventty.businessservice.domain.repository.EventDetailRepository;
import com.eventty.businessservice.domain.repository.EventRepository;
import com.eventty.businessservice.application.dto.response.EventWithDetailResponseDTO;
import com.eventty.businessservice.domain.exception.EventNotFoundException;
import com.eventty.businessservice.domain.repository.TicketRepository;
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
public class EventServiceImplTest {

    @Mock
    private EventRepository eventRepository;
    @Mock
    private EventDetailRepository eventDetailRepository;

    @Mock
    private TicketRepository ticketRepository;

    @InjectMocks
    private EventServiceImpl eventService;

    @Test
    @DisplayName("존재하는 이벤트 조회 테스트")
    public void findEventByIdTest_ExistingEvent() {
        // Given
        Long eventId = 1L;
        EventWithDetailResponseDTO mockEvent = createEventWithDetailDAO(eventId);
        List<TicketEntity> mockTickets = createTickets(); // 필요에 따라 만들어야 합니다.

        when(eventRepository.selectEventWithDetailById(eventId)).thenReturn(mockEvent);
        when(ticketRepository.selectTicketByEventId(eventId)).thenReturn(mockTickets);

        // When
        EventFindByIdWithDetailResponseDTO responseDTO = eventService.findEventById(eventId);

        // Then
        assertEquals(mockEvent.getId(), responseDTO.getId());
        assertEquals(mockEvent.getTitle(), responseDTO.getTitle());
        assertEquals(mockEvent.getContent(), responseDTO.getContent());

        verify(eventRepository, times(1)).selectEventWithDetailById(eventId);
    }

    @Test
    @DisplayName("존재하지 않는 이벤트 조회 시 예외 테스트")
    public void findEventByIdTest_NonExistingEvent() {
        // Given
        Long eventId = 1L;
        when(eventRepository.selectEventWithDetailById(eventId)).thenReturn(null);

        // When & Then
        assertThrows(EventNotFoundException.class, () -> eventService.findEventById(eventId));
        verify(eventRepository, times(1)).selectEventWithDetailById(eventId);
    }

    @Test
    @DisplayName("전체 이벤트 조회 테스트")
    public void findAllEventsTest() {
        // Given
        List<EventEntity> mockEventEntities = createEventEntityList(3L);
        when(eventRepository.selectAllEvents()).thenReturn(mockEventEntities);

        // When
        List<EventFindAllResponseDTO> responseDTOs = eventService.findAllEvents();

        // Then
        assertEquals(mockEventEntities.size(), responseDTOs.size());
        verify(eventRepository, times(1)).selectAllEvents();
    }

    @Test
    @DisplayName("이벤트 삭제 테스트")
    public void deleteEventTest(){
        // Given
        Long eventId = 1L;
        when(eventRepository.deleteEvent(eventId)).thenReturn(1L);
        when(eventDetailRepository.deleteEventDetail(eventId)).thenReturn(1L);
        when(ticketRepository.deleteTicket(eventId)).thenReturn(1L);

        // When
        eventService.deleteEvent(eventId);

        // Then
        assertNull(eventRepository.selectEventById(eventId));
        verify(eventRepository, times(1)).deleteEvent(eventId);
        verify(eventDetailRepository, times(1)).deleteEventDetail(eventId);
    }

    @Test
    @DisplayName("이벤트 수정 테스트")
    public void updateEventTest() {
        Long eventId = 1L;
        EventEntity existingEvent = createEventEntity(eventId);

        EventFullUpdateRequestDTO updateRequestDTO = EventFullUpdateRequestDTO.builder()
                .title("Updated Title")
                .image("updated_image.jpg")
                .content("Updated Content")
                .build();

        EventDetailEntity existingEventDetail = EventDetailEntity.builder()
                .id(eventId)
                .content("Old Content")
                .build();

        when(eventRepository.selectEventById(eventId)).thenReturn(existingEvent);
        when(eventDetailRepository.selectEventDetailById(eventId)).thenReturn(existingEventDetail);

        eventService.updateEvent(eventId, updateRequestDTO);

        verify(eventRepository, times(1)).updateEvent(any(EventEntity.class));
        verify(eventDetailRepository, times(1)).updateEventDetail(any(EventDetailEntity.class));
    }

    @Test
    public void findEventsByCategoryTest() {
        // given
        Long categoryId = 5L;
        List<EventEntity> mockEvents = createEventEntityList(3L); // 필요에 따라 만들어야 합니다.
        when(eventRepository.selectEventsByCategory(categoryId)).thenReturn(mockEvents);

        // when
        List<EventFindAllResponseDTO> result = eventService.findEventsByCategory(categoryId);

        // then
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(mockEvents.size(), result.size());
    }

    @Test
    public void findEventsByCategoryTest_InvalidCategoryId() {
        // given
        Long invalidCategoryId = 15L;

        // when & then
        assertThrows(CategoryNotFoundException.class, () -> {
            eventService.findEventsByCategory(invalidCategoryId);
        });
    }

    @Test
    public void findEventsByCategoryTest_NoEventsFound() {
        // given
        Long categoryId = 7L;
        when(eventRepository.selectEventsByCategory(categoryId)).thenReturn(new ArrayList<>());

        // when & then
        assertThrows(EventNotFoundException.class, () -> {
            eventService.findEventsByCategory(categoryId);
        });
    }

    private static EventEntity createEventEntity(Long i){
        return EventEntity.builder()
            .id(i)
            .hostId(i)
            .title("Sample Event")
            .image("sample.jpg")
            .eventStartAt(Timestamp.valueOf("2023-08-21 10:00:00"))
            .eventEndAt(Timestamp.valueOf("2023-08-21 15:00:00"))
            .participateNum(100L)
            .location("Sample Location")
            .category(1L)
            .isActive(true)
            .isDeleted(false)
            .build();
    }

    private static EventWithDetailResponseDTO createEventWithDetailDAO(Long id){
        return EventWithDetailResponseDTO.builder()
                .id(id)
                .hostId(1L)
                .title("Sample Event")
                .image("sample.jpg")
                .eventStartAt(Timestamp.valueOf("2023-08-21 10:00:00"))
                .eventEndAt(Timestamp.valueOf("2023-08-21 15:00:00"))
                .participateNum(100L)
                .location("Sample Location")
                .category(1L)
                .isActive(true)
                .isDeleted(false)
                .content("Sample content")
                .applyStartAt(Timestamp.valueOf("2023-08-21 10:00:00"))
                .applyEndAt(Timestamp.valueOf("2023-08-21 15:00:00"))
                .views(100L)
                .build();
    }

    private static List<EventEntity> createEventEntityList(Long count) {
        List<EventEntity> eventEntityList = new ArrayList<>();

        for (Long i = 0L; i < count; i++) {
            EventEntity eventEntity = createEventEntity(i);
            eventEntityList.add(eventEntity);
        }

        return eventEntityList;
    }

    private List<TicketEntity> createTickets() {
        List<TicketEntity> tickets = new ArrayList<>();

        // 티켓 1
        TicketEntity ticket1 = TicketEntity.builder()
                .id(1L)
                .name("Ticket 1")
                .price(20.0)
                .quantity(50)
                .eventId(1L)
                .is_deleted(false)
                .build();

        // 티켓 2
        TicketEntity ticket2 = TicketEntity.builder()
                .id(2L)
                .name("Ticket 2")
                .price(30.0)
                .quantity(30)
                .eventId(1L)
                .is_deleted(false)
                .build();

        tickets.add(ticket1);
        tickets.add(ticket2);

        return tickets;
    }


}
