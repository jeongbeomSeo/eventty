package com.eventty.businessservice.application;

import com.eventty.businessservice.domains.event.domain.Enum.Category;
import com.eventty.businessservice.domains.event.application.dto.response.EventBasicFindAllResponseDTO;
import com.eventty.businessservice.domains.event.application.service.EventService;
import com.eventty.businessservice.domains.event.domain.entity.EventBasicEntity;
import com.eventty.businessservice.domains.event.domain.exception.CategoryNotFoundException;
import com.eventty.businessservice.domains.event.domain.repository.EventBasicRepository;
import com.eventty.businessservice.domains.event.domain.exception.EventNotFoundException;
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
public class EventServiceTest {

    @Mock
    private EventBasicRepository eventBasicRepository;

    @InjectMocks
    private EventService eventService;

    @Test
    @DisplayName("전체 이벤트 조회 테스트")
    public void findAllEventsTest() {
        // Given
        List<EventBasicEntity> mockEventEntities = createEventEntityList(3L);
        when(eventBasicRepository.selectAllEvents()).thenReturn(mockEventEntities);

        // When
        List<EventBasicFindAllResponseDTO> responseDTOs = eventService.findAllEvents();

        // Then
        assertEquals(mockEventEntities.size(), responseDTOs.size());
        verify(eventBasicRepository, times(1)).selectAllEvents();
    }

    @Test
    @DisplayName("주최자가 등록한 이벤트 조회 테스트")
    public void findEventsByHostIdTest() {
        // given
        Long hostId = 1L;
        List<EventBasicEntity> mockEvents = createEventEntityList(3L);
        when(eventBasicRepository.selectEventsByHostId(hostId)).thenReturn(mockEvents);

        // when
        List<EventBasicFindAllResponseDTO> result = eventService.findEventsByHostId(hostId);

        // then
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(mockEvents.size(), result.size());
    }

    @Test
    @DisplayName("이벤트 카테고리별 조회 테스트")
    public void findEventsByCategoryTest() {
        // given
        Category category = Category.교양;
        List<EventBasicEntity> mockEvents = createEventEntityList(3L);
        when(eventBasicRepository.selectEventsByCategory(category.getId())).thenReturn(mockEvents);

        // when
        List<EventBasicFindAllResponseDTO> result = eventService.findEventsByCategory(category);

        // then
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(mockEvents.size(), result.size());
    }

    @Test
    @DisplayName("이벤트 카테고리별 조회 테스트 - 이벤트 없음")
    public void findEventsByCategoryTest_NoEventsFound() {
        // given
        Category category = Category.교양;
        when(eventBasicRepository.selectEventsByCategory(category.getId())).thenReturn(new ArrayList<>());

        // when & then
        assertThrows(EventNotFoundException.class, () -> {
            eventService.findEventsByCategory(category);
        });
    }

    @Test
    @DisplayName("이벤트 검색 테스트")
    public void findEventsBySearchTest() {
        // given
        String keyword = "Sample";
        List<EventBasicEntity> mockEvents = createEventEntityList(3L);
        when(eventBasicRepository.selectEventsBySearch(keyword)).thenReturn(mockEvents);

        // when
        List<EventBasicFindAllResponseDTO> result = eventService.findEventsBySearch(keyword);

        // then
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(mockEvents.size(), result.size());
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

    private static List<EventBasicEntity> createEventEntityList(Long count) {
        List<EventBasicEntity> eventBasicEntityList = new ArrayList<>();

        for (Long i = 0L; i < count; i++) {
            EventBasicEntity eventBasicEntity = createEventEntity(i);
            eventBasicEntityList.add(eventBasicEntity);
        }

        return eventBasicEntityList;
    }

}
