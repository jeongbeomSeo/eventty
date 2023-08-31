package com.eventty.businessservice.domain;

import com.eventty.businessservice.domain.entity.EventEntity;
import com.eventty.businessservice.domain.entity.TicketEntity;
import com.eventty.businessservice.domain.repository.EventRepository;
import com.eventty.businessservice.domain.repository.TicketRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // 테스트용 인메모리 DB
@MybatisTest
public class TicketRepositoryTest {

    @Autowired
    private TicketRepository ticketRepository;

    @Test
    @DisplayName("특정 이벤트에 할당된 티켓 조회 테스트")
    public void selectTicketByEventIdTest() {
        // given
        Long eventId = 1L;
        // when
        List<TicketEntity> tickets = ticketRepository.selectTicketByEventId(eventId);
        // then
        assertNotNull(tickets);
        assertEquals(tickets.get(0).getEventId(), eventId);
    }

    @Test
    @DisplayName("티켓 생성 테스트")
    public void insertTicketTest() {
        // given
        Long eventId = 1L;
        TicketEntity ticketEntity = createTicketEntity();
        // when
        Long ticketId = ticketRepository.insertTicket(ticketEntity);
        // then
        assertNotNull(ticketId);
    }

    public TicketEntity createTicketEntity(){
        return TicketEntity.builder()
                .eventId(1L)
                .price(1000)
                .name("일반")
                .quantity(100)
                .is_deleted(false)
                .build();
    }
}