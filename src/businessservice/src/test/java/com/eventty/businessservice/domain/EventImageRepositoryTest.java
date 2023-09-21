package com.eventty.businessservice.domain;


import com.eventty.businessservice.event.domain.entity.EventImageEntity;
import com.eventty.businessservice.event.domain.repository.EventImageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // 테스트용 인메모리 DB
@MybatisTest
public class EventImageRepositoryTest {
    /*
    @Autowired
    private EventImageRepository eventImageRepository;

    private Long eventId = 1L;

    @BeforeEach
    public void setUp(){
        EventImageEntity mockEventImage = createEventImageEntity(eventId);
        eventImageRepository.insertEventImage(mockEventImage);
    }

    @Test
    @DisplayName("이벤트 이미지 조회 테스트")
    public void selectEventImageByEventIdTest() {
        // given & when
        eventImageRepository.selectEventImageByEventId(eventId);

        // then
        assertEquals(eventId, eventImageRepository.selectEventImageByEventId(eventId).getEventId());
        assertEquals(100L, eventImageRepository.selectEventImageByEventId(eventId).getFileSize());
    }

    private EventImageEntity createEventImageEntity(Long eventId) {
        return EventImageEntity.builder()
                .eventId(eventId)
                .originalFileName("originalFileName")
                .storedFilePath("storedFilePath")
                .fileSize(100L)
                .isDeleted(false)
                .build();
    }


     */
}

