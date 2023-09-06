package com.eventty.businessservice.domain;

import com.eventty.businessservice.domains.event.domain.entity.EventEntity;
import com.eventty.businessservice.domains.event.domain.repository.EventRepository;
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
public class EventRepositoryTest {

    @Autowired
    private EventRepository eventRepository;

    private Long eventId = 1L;

    @BeforeEach
    public void setUp(){
        EventEntity savedEvent = createEventEntity();
        eventRepository.insertEvent(savedEvent);
    }

    @Test
    @DisplayName("특정 이벤트 조회 테스트")
    public void selectEventByIdTest() {
        // given & when
        EventEntity event = eventRepository.selectEventById(eventId);
        // then
        assertNotNull(event);
        assertEquals(event.getId(), eventId);
    }

    @Test
    @DisplayName("이벤트 전체 조회 테스트")
    public void selectAllEventsTest() {
        // given & when
        List<EventEntity> events = eventRepository.selectAllEvents();
        // then
        assertNotNull(events);
        assertEquals(events.size(), 1);
    }


    @Test
    @DisplayName("이벤트 삭제 테스트")
    public void deleteEventTest(){
        // given & when
        eventRepository.deleteEvent(eventId);

        // then
        assertNull(eventRepository.selectEventById(eventId));
    }

    @Test
    @DisplayName("이벤트 카테고리 별 조회")
    public void selectEventsByCategoryTest(){
        // given & when
        Long categoryId = 1L;
        List<EventEntity> list = eventRepository.selectEventsByCategory(categoryId);

        // then
        assertNotNull(list);
        assertEquals(list.size(), 1);
    }

    private static EventEntity createEventEntity(){
        return EventEntity.builder()
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

}
