package com.eventty.businessservice.domains.event.domain;

import com.eventty.businessservice.domain.entity.EventDetailEntity;
import com.eventty.businessservice.domain.entity.EventEntity;
import com.eventty.businessservice.domain.repository.EventDetailRepository;
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

    @Test
    @DisplayName("특정 이벤트 상세 정보 조회 테스")
    public void selectEventDetailByIdTest() {
        // given
        Long eventId = 1L;
        // when
        EventDetailEntity eventDetail = eventDetailRepository.selectEventDetailById(eventId);
        // then
        assertNotNull(eventDetail);
        assertEquals(eventDetail.getId(), eventId);
        assertEquals(eventDetail.getContent(), "Detail for Event 1");
    }

    @Test
    @DisplayName("이벤트 상세 정보 생성 테스트")
    public void createEventDetailTest(){
        // given
        Long eventId = 10L;
        EventDetailEntity eventDetail = createEventDetailEntity(eventId);

        // when
        Long savedEventId = eventDetailRepository.insertEventDetail(eventDetail);

        // then
        assertNotNull(savedEventId);
        EventDetailEntity retrievedEventDetail = eventDetailRepository.selectEventDetailById(eventId);
        assertEquals(eventDetail.getContent(), retrievedEventDetail.getContent());
    }

    private static EventDetailEntity createEventDetailEntity(Long id){
        return EventDetailEntity.builder()
                .id(id)
                .content("Sample content")
                .applyStartAt(Timestamp.valueOf("2023-08-21 10:00:00"))
                .applyEndAt(Timestamp.valueOf("2023-08-21 15:00:00"))
                .views(100L)
                .deleteDate(Timestamp.valueOf("2023-08-21 12:00:00"))
                .updateDate(Timestamp.valueOf("2023-08-21 13:00:00"))
                .createDate(Timestamp.valueOf("2023-08-21 10:30:00"))
                .build();
    }

}
