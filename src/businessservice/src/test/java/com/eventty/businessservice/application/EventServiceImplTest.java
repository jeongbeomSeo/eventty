package com.eventty.businessservice.application;

import com.eventty.businessservice.application.dto.request.EventCreateRequestDTO;
import com.eventty.businessservice.application.dto.request.EventFullCreateRequestDTO;
import com.eventty.businessservice.application.dto.request.EventFullUpdateRequestDTO;
import com.eventty.businessservice.application.dto.response.EventFindByIdWithDetailResponseDTO;
import com.eventty.businessservice.application.dto.response.EventFindAllResponseDTO;
import com.eventty.businessservice.application.serviceImpl.EventServiceImpl;
import com.eventty.businessservice.domain.entity.EventDetailEntity;
import com.eventty.businessservice.domain.entity.EventEntity;
import com.eventty.businessservice.domain.repository.EventDetailRepository;
import com.eventty.businessservice.domain.repository.EventRepository;
import com.eventty.businessservice.domain.EventWithDetailDTO;
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
    private EventDetailRepository eventDetailRepository;

    @InjectMocks
    private EventServiceImpl eventService;

    @Test
    @DisplayName("존재하는 이벤트 조회 테스트")
    public void findEventByIdTest_ExistingEvent() {
        // Given
        Long eventId = 1L;
        EventWithDetailDTO mockEvent = createEventWithDetailDAO(eventId);
        when(eventRepository.selectEventWithDetailById(eventId)).thenReturn(mockEvent);

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
        when(eventRepository.deleteEvent(eventId)).thenReturn(eventId);
        when(eventDetailRepository.deleteEventDetail(eventId)).thenReturn(eventId);

        // When
        eventService.deleteEvent(eventId);

        // Then
        verify(eventRepository, times(1)).deleteEvent(eventId);
        verify(eventDetailRepository, times(1)).deleteEventDetail(eventId);
    }

    @Test
    @DisplayName("이벤트 수정 테스트")
    public void testUpdateEvent() {
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

    private static EventWithDetailDTO createEventWithDetailDAO(Long id){
        return EventWithDetailDTO.builder()
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

}