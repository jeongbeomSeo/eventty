package com.eventty.businessservice.application;

import com.eventty.businessservice.domains.event.application.dto.request.EventUpdateRequestDTO;
import com.eventty.businessservice.domains.event.application.dto.response.EventWithTicketsFindByIdResponseDTO;
import com.eventty.businessservice.domains.event.application.dto.response.EventBasicFindAllResponseDTO;
import com.eventty.businessservice.domains.event.application.Facade.EventServiceImpl;
import com.eventty.businessservice.domains.event.domain.entity.EventDetailEntity;
import com.eventty.businessservice.domains.event.domain.entity.EventBasicEntity;
import com.eventty.businessservice.domains.event.domain.entity.TicketEntity;
import com.eventty.businessservice.domains.event.domain.exception.CategoryNotFoundException;
import com.eventty.businessservice.domains.event.domain.repository.EventDetailRepository;
import com.eventty.businessservice.domains.event.domain.repository.EventBasicRepository;
import com.eventty.businessservice.domains.event.application.dto.response.EventFullFindByIdResponseDTO;
import com.eventty.businessservice.domains.event.domain.exception.EventNotFoundException;
import com.eventty.businessservice.domains.event.domain.repository.TicketRepository;
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
public class EventServiceImplImplTest {

    @Mock
    private EventBasicRepository eventBasicRepository;
    @Mock
    private EventDetailRepository eventDetailRepository;

    @Mock
    private TicketRepository ticketRepository;

    @InjectMocks
    private EventServiceImpl eventServiceImpl;

    @Test
    @DisplayName("존재하는 이벤트 조회 테스트")
    public void findEventByIdTest_ExistingEvent() {
        // Given
        Long eventId = 1L;
        EventFullFindByIdResponseDTO mockEvent = createEventWithDetailDAO(eventId);
        List<TicketEntity> mockTickets = createTickets(); // 필요에 따라 만들어야 합니다.

        when(eventBasicRepository.selectEventWithDetailById(eventId)).thenReturn(mockEvent);
        when(ticketRepository.selectTicketByEventId(eventId)).thenReturn(mockTickets);

        // When
        EventWithTicketsFindByIdResponseDTO responseDTO = eventServiceImpl.findEventById(eventId);

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
        assertThrows(EventNotFoundException.class, () -> eventServiceImpl.findEventById(eventId));
        verify(eventBasicRepository, times(1)).selectEventWithDetailById(eventId);
    }

    @Test
    @DisplayName("전체 이벤트 조회 테스트")
    public void findAllEventsTest() {
        // Given
        List<EventBasicEntity> mockEventEntities = createEventEntityList(3L);
        when(eventBasicRepository.selectAllEvents()).thenReturn(mockEventEntities);

        // When
        List<EventBasicFindAllResponseDTO> responseDTOs = eventServiceImpl.findAllEvents();

        // Then
        assertEquals(mockEventEntities.size(), responseDTOs.size());
        verify(eventBasicRepository, times(1)).selectAllEvents();
    }

    @Test
    @DisplayName("이벤트 삭제 테스트")
    public void deleteEventTest(){
        // Given
        Long eventId = 1L;
        when(eventBasicRepository.deleteEvent(eventId)).thenReturn(1L);
        when(eventDetailRepository.deleteEventDetail(eventId)).thenReturn(1L);
        when(ticketRepository.deleteTicket(eventId)).thenReturn(1L);

        // When
        eventServiceImpl.deleteEvent(eventId);

        // Then
        assertNull(eventBasicRepository.selectEventById(eventId));
        verify(eventBasicRepository, times(1)).deleteEvent(eventId);
        verify(eventDetailRepository, times(1)).deleteEventDetail(eventId);
    }

    @Test
    @DisplayName("이벤트 수정 테스트")
    public void updateEventTest() {
        Long eventId = 1L;
        EventBasicEntity existingEvent = createEventEntity(eventId);

        EventUpdateRequestDTO updateRequestDTO = EventUpdateRequestDTO.builder()
                .title("Updated Title")
                .image("updated_image.jpg")
                .content("Updated Content")
                .build();

        EventDetailEntity existingEventDetail = EventDetailEntity.builder()
                .id(eventId)
                .content("Old Content")
                .build();

        when(eventBasicRepository.selectEventById(eventId)).thenReturn(existingEvent);
        when(eventDetailRepository.selectEventDetailById(eventId)).thenReturn(existingEventDetail);

        eventServiceImpl.updateEvent(eventId, updateRequestDTO);

        verify(eventBasicRepository, times(1)).updateEvent(any(EventBasicEntity.class));
        verify(eventDetailRepository, times(1)).updateEventDetail(any(EventDetailEntity.class));
    }

    @Test
    public void findEventsByCategoryTest() {
        // given
        Long categoryId = 5L;
        List<EventBasicEntity> mockEvents = createEventEntityList(3L); // 필요에 따라 만들어야 합니다.
        when(eventBasicRepository.selectEventsByCategory(categoryId)).thenReturn(mockEvents);

        // when
        List<EventBasicFindAllResponseDTO> result = eventServiceImpl.findEventsByCategory(categoryId);

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
            eventServiceImpl.findEventsByCategory(invalidCategoryId);
        });
    }

    @Test
    public void findEventsByCategoryTest_NoEventsFound() {
        // given
        Long categoryId = 7L;
        when(eventBasicRepository.selectEventsByCategory(categoryId)).thenReturn(new ArrayList<>());

        // when & then
        assertThrows(EventNotFoundException.class, () -> {
            eventServiceImpl.findEventsByCategory(categoryId);
        });
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

    private static EventFullFindByIdResponseDTO createEventWithDetailDAO(Long id){
        return EventFullFindByIdResponseDTO.builder()
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

    private static List<EventBasicEntity> createEventEntityList(Long count) {
        List<EventBasicEntity> eventBasicEntityList = new ArrayList<>();

        for (Long i = 0L; i < count; i++) {
            EventBasicEntity eventBasicEntity = createEventEntity(i);
            eventBasicEntityList.add(eventBasicEntity);
        }

        return eventBasicEntityList;
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
