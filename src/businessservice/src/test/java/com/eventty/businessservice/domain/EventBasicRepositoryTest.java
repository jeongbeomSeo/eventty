package com.eventty.businessservice.domain;

import com.eventty.businessservice.domains.event.application.dto.response.EventFullFindByIdResponseDTO;
import com.eventty.businessservice.domains.event.domain.entity.EventDetailEntity;
import com.eventty.businessservice.domains.event.domain.entity.EventBasicEntity;
import com.eventty.businessservice.domains.event.domain.repository.EventDetailRepository;
import com.eventty.businessservice.domains.event.domain.repository.EventBasicRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@AutoConfigureTestDatabase(replace = Replace.NONE) // 테스트용 인메모리 DB
@MybatisTest
public class EventBasicRepositoryTest {

    @Autowired
    private EventBasicRepository eventBasicRepository;

    @Autowired
    private EventDetailRepository eventDetailRepository;

    private Long eventId = 1L;

    @BeforeEach
    public void setUp(){
        EventBasicEntity mockEvent = createEventEntity();
        eventBasicRepository.insertEvent(mockEvent);
        EventDetailEntity mockDetailEvent = createEventDetailEntity(eventId);
        eventDetailRepository.insertEventDetail(mockDetailEvent);
    }

    @Test
    @DisplayName("특정 이벤트 조회 테스트 - 일반 정보와 상세 정보 JOIN")
    public void selectEventWithDetailByIdTest() {
        // given & when
        EventFullFindByIdResponseDTO event = eventBasicRepository.selectEventWithDetailById(eventId);
        // then
        assertNotNull(event);
        assertEquals(eventId, event.getId());
        assertEquals("Sample content", event.getContent());
    }

    @Test
    @DisplayName("이벤트 전체 조회 테스트")
    public void selectAllEventsTest() {
        // given & when
        List<EventBasicEntity> events = eventBasicRepository.selectAllEvents();
        // then
        assertNotNull(events);
        assertEquals(events.size(), 1);
    }


    @Test
    @DisplayName("이벤트 삭제 테스트")
    public void deleteEventTest(){
        // given & when
        eventBasicRepository.deleteEvent(eventId);

        // then
        assertNull(eventBasicRepository.selectEventWithDetailById(eventId));
    }

    @Test
    @DisplayName("이벤트 카테고리 별 조회")
    public void selectEventsByCategoryTest(){
        // given & when
        Long categoryId = 1L;
        List<EventBasicEntity> list = eventBasicRepository.selectEventsByCategory(categoryId);

        // then
        assertNotNull(list);
        assertEquals(list.size(), 1);
    }

    @Test
    @DisplayName("이벤트 검색")
    public void selectEventsBySearchTest(){
        // given & when
        String keyword = "Sample";
        List<EventBasicEntity> list = eventBasicRepository.selectEventsBySearch(keyword);

        // then
        assertNotNull(list);
        assertEquals(list.size(), 1);
    }

    private static EventBasicEntity createEventEntity(){
        return EventBasicEntity.builder()
                //.id(10L)
                .userId(1L)
                .title("Sample Event")
                .image("sample.jpg")
                .eventStartAt(LocalDateTime.now())
                .eventEndAt(LocalDateTime.now())
                .participateNum(100L)
                .location("Sample Location")
                .category(1L)
                .isActive(true)
                .isDeleted(false)
                .build();
    }

    private static EventDetailEntity createEventDetailEntity(Long id){
        return EventDetailEntity.builder()
                .id(id)
                .content("Sample content")
                .applyStartAt(Timestamp.valueOf("2023-08-21 10:00:00").toLocalDateTime())
                .applyEndAt(Timestamp.valueOf("2023-08-21 15:00:00").toLocalDateTime())
                .views(100L)
                .deleteDate(Timestamp.valueOf("2023-08-21 12:00:00").toLocalDateTime())
                .updateDate(Timestamp.valueOf("2023-08-21 13:00:00").toLocalDateTime())
                .createDate(Timestamp.valueOf("2023-08-21 10:30:00").toLocalDateTime())
                .build();
    }


}
