package com.eventty.businessservice.domains.event.domain;

import com.eventty.businessservice.domain.EventDetailEntity;
import com.eventty.businessservice.domain.EventDetailRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.test.context.junit.jupiter.SpringExtension;

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

}
