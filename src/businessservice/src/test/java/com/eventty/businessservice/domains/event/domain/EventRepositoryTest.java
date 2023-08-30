package com.eventty.businessservice.domains.event.domain;

import com.eventty.businessservice.application.dto.request.EventCreateRequestDTO;
import com.eventty.businessservice.domain.entity.EventEntity;
import com.eventty.businessservice.domain.repository.EventRepository;
import com.eventty.businessservice.domain.EventWithDetailDAO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.Timestamp;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@AutoConfigureTestDatabase(replace = Replace.NONE) // 테스트용 인메모리 DB
@MybatisTest
public class EventRepositoryTest {

    @Autowired
    private EventRepository eventRepository;

    @Test
    @DisplayName("특정 이벤트 조회 테스트")
    public void selectEventByIdTest() {
        // given
        Long eventId = 1L;
        // when
        EventEntity event = eventRepository.selectEventById(eventId);
        // then
        assertNotNull(event);
        assertEquals(event.getId(), eventId);
        assertEquals(event.getTitle(), "Event 1");
    }

    @Test
    @DisplayName("이벤트 전체 조회 테스트")
    public void selectAllEventsTest() {
        // given & when
        List<EventEntity> events = eventRepository.selectAllEvents();
        // then
        assertNotNull(events);
        assertEquals(events.size(), 3);
    }

    @Test
    @DisplayName("이벤트 JOIN 문 조회 테스트")
    public void selectEventWithDetailByIdTest(){
        // given
        Long eventId = 1L;

        // when
        EventWithDetailDAO event = eventRepository.selectEventWithDetailById(eventId);

        // then
        assertNotNull(event);
        assertEquals(event.getId(), eventId);
        assertEquals(event.getContent(), "Detail for Event 1");
    }

    @Test
    @DisplayName("이벤트 생성 테스트")
    public void createEventTest(){
        // given
        EventEntity savedEvent = createEventEntity();

        // when
        eventRepository.insertEvent(savedEvent);

        // then
        EventEntity retrievedEvent = eventRepository.selectEventById(savedEvent.getId());
        assertEquals(savedEvent.getTitle(), retrievedEvent.getTitle());
    }

    private static EventEntity createEventEntity(){
        return EventEntity.builder()
                .id(10L)
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

}
