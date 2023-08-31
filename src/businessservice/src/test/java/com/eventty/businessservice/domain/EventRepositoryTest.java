package com.eventty.businessservice.domain;

import com.eventty.businessservice.application.dto.request.EventCreateRequestDTO;
import com.eventty.businessservice.application.dto.request.EventUpdateRequestDTO;
import com.eventty.businessservice.domain.entity.EventEntity;
import com.eventty.businessservice.domain.repository.EventRepository;
import com.eventty.businessservice.domain.EventWithDetailDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

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
        EventEntity savedEvent = createEventEntity();
        eventRepository.insertEvent(savedEvent);
        // when
        EventEntity event = eventRepository.selectEventById(eventId);
        // then
        assertNotNull(event);
        assertEquals(event.getId(), eventId);
    }

    @Test
    @DisplayName("이벤트 전체 조회 테스트")
    public void selectAllEventsTest() {
        // given & when
        EventEntity savedEvent = createEventEntity();
        eventRepository.insertEvent(savedEvent);
        List<EventEntity> events = eventRepository.selectAllEvents();
        // then
        assertNotNull(events);
        assertEquals(events.size(), 1);
    }

    @Test
    @DisplayName("이벤트 JOIN 문 조회 테스트")
    public void selectEventWithDetailByIdTest(){
        // given
        EventEntity savedEvent = createEventEntity();
        Long eventId = eventRepository.insertEvent(savedEvent);

        // when
        EventWithDetailDTO event = eventRepository.selectEventWithDetailById(eventId);

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
        Long id = eventRepository.insertEvent(savedEvent);

        // then
        assertNotNull(id);
        assertEquals(1, id);
    }

    @Test
    @DisplayName("이벤트 수정 테스트")
    public void updateEventTest(){
        // given
        Long eventId = 1L;
        EventUpdateRequestDTO updatedEvent = createEventUpdateRequestDTO();

        // when
        Long id = eventRepository.updateEvent(updatedEvent.toEntity(eventId));

        // then=
        assertNotNull(id);
    }

    @Test
    @Transactional
    @DisplayName("이벤트 삭제 테스트")
    public void deleteEventTest(){
        // given
        Long eventId = 1L;

        // when
        Long deletedEventId = eventRepository.deleteEvent(eventId);

        // then
        assertEquals(deletedEventId, eventId);
        assertEquals(eventRepository.selectEventById(eventId).getIsDeleted(), true);
    }

    private static EventEntity createEventEntity(){
        return EventEntity.builder()
                //.id(10L)
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

    private static EventUpdateRequestDTO createEventUpdateRequestDTO(){
        return EventUpdateRequestDTO.builder()
                .title("Updated Event")
                .image("Updated.jpg")
                .eventStartAt(Timestamp.valueOf("2023-08-21 10:00:00"))
                .eventEndAt(Timestamp.valueOf("2023-08-21 15:00:00"))
                .participateNum(100L)
                .location("Updated Location")
                .category("Updated Category")
                .build();
    }

}
