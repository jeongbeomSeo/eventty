package com.eventty.businessservice.domains.event.application;

import com.eventty.businessservice.application.dto.response.EventWithDetailDTO;
import com.eventty.businessservice.application.dto.response.EventResponseDTO;
import com.eventty.businessservice.application.service.EventDetailService;
import com.eventty.businessservice.application.serviceImpl.EventServiceImpl;
import com.eventty.businessservice.domain.EventEntity;
import com.eventty.businessservice.domain.EventRepository;
import com.eventty.businessservice.domain.EventWithDetailDAO;
import com.eventty.businessservice.domain.exception.EventNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EventServiceImplTest {

    @Mock
    private EventRepository eventRepository;

    @Mock
    private EventDetailService eventDetailService;

    @InjectMocks
    private EventServiceImpl eventService;

    @Test
    @DisplayName("존재하는 이벤트 조회 테스트")
    public void findEventByIdTest_ExistingEvent() {
        // Given
        Long eventId = 1L;
        EventWithDetailDAO mockEvent = createEventWithDetailDAO(eventId);
        when(eventRepository.selectEventWithDetailById(eventId)).thenReturn(mockEvent);

        // When
        EventWithDetailDTO responseDTO = eventService.findEventById(eventId);

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
        when(eventRepository.selectEventById(eventId)).thenReturn(null);

        // When & Then
        assertThrows(EventNotFoundException.class, () -> eventService.findEventById(eventId));
        verify(eventRepository, times(1)).selectEventById(eventId);
    }

    @Test
    @DisplayName("전체 이벤트 조회 테스트")
    public void findAllEventsTest() {
        // Given
        List<EventEntity> mockEventEntities = createEventEntityList(3L);
        when(eventRepository.selectAllEvents()).thenReturn(mockEventEntities);

        // When
        List<EventResponseDTO> responseDTOs = eventService.findAllEvents();

        // Then
        assertEquals(mockEventEntities.size(), responseDTOs.size());
        verify(eventRepository, times(1)).selectAllEvents();
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
            .category("Sample Category")
            .isActive(true)
            .isDeleted(false)
            .build();
    }

    private static EventWithDetailDAO createEventWithDetailDAO(Long id){
        return EventWithDetailDAO.builder()
                .id(id)
                .hostId(1L)
                .title("Sample Event")
                .image("sample.jpg")
                .eventStartAt(Timestamp.valueOf("2023-08-21 10:00:00"))
                .eventEndAt(Timestamp.valueOf("2023-08-21 15:00:00"))
                .participateNum(100L)
                .location("Sample Location")
                .category("Sample Category")
                .isActive(true)
                .isDeleted(false)
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

}
