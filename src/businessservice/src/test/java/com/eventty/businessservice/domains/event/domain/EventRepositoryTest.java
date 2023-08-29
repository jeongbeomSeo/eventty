package com.eventty.businessservice.domains.event.domain;

import com.eventty.businessservice.domain.EventEntity;
import com.eventty.businessservice.domain.EventRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.test.context.junit.jupiter.SpringExtension;

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
}
