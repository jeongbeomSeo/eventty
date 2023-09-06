package com.eventty.businessservice.domain;

import com.eventty.businessservice.domains.event.domain.entity.EventDetailEntity;
import com.eventty.businessservice.domains.event.domain.repository.EventDetailRepository;
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

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@AutoConfigureTestDatabase(replace = Replace.NONE) // 테스트용 인메모리 DB
@MybatisTest
public class EventDetailRepositoryTest {

    @Autowired
    private EventDetailRepository eventDetailRepository;

    private Long eventId = 1L;

    @BeforeEach
    public void setUp(){
        EventDetailEntity mockEventDetail = createEventDetailEntity(eventId);
        eventDetailRepository.insertEventDetail(mockEventDetail);
    }

    @Test
    @DisplayName("특정 이벤트 상세 정보 조회 테스트")
    public void selectEventDetailByIdTest() {
        // given
//        Long eventId = 1L;
//        EventDetailEntity mockEventDetail = createEventDetailEntity(eventId);
//        eventDetailRepository.insertEventDetail(mockEventDetail);
        // when
        EventDetailEntity eventDetail = eventDetailRepository.selectEventDetailById(eventId);
        // then
        assertEquals(eventDetail.getId(), eventId);
        assertEquals(eventDetail.getContent(), "Sample content");
    }

    @Test
    @DisplayName("이벤트 상세 정보 삭제 테스트")
    public void deleteEventDetailTest(){
        // given & when
        Long deletedEventId = eventDetailRepository.deleteEventDetail(eventId);

        // then
        assertEquals(deletedEventId, eventId);
        assertNull(eventDetailRepository.selectEventDetailById(eventId));
    }

    @Test
    @DisplayName("이벤트 조회수 증가 테스트")
    public void updateViewTest(){
        // given & when
        eventDetailRepository.updateView(eventId);

        // then
        assertEquals( 1, eventDetailRepository.selectEventDetailById(eventId).getViews());
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
