package com.eventty.businessservice.domain;

import com.eventty.businessservice.domains.event.domain.entity.TicketEntity;
import com.eventty.businessservice.domains.event.domain.repository.TicketRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // 테스트용 인메모리 DB
@MybatisTest
public class TicketRepositoryTest {

    @Autowired
    private TicketRepository ticketRepository;

    private Long eventId = 1L;

    @BeforeEach
    public void setUp(){
        TicketEntity ticketEntity = createTicketEntity();
        Long ticketId = ticketRepository.insertTicket(ticketEntity);
    }

    @Test
    @DisplayName("특정 이벤트에 할당된 티켓 조회 테스트")
    public void selectTicketByEventIdTest() {
        // given & when
        List<TicketEntity> tickets = ticketRepository.selectTicketByEventId(eventId);
        // then
        assertNotNull(tickets);
        assertEquals(tickets.size(), 1);
    }

    @Test
    @DisplayName("티켓 삭제 테스트")
    public void deleteTicketTest() {
        // when
        ticketRepository.deleteTicketsByEventId(eventId);
        // then
        assertEquals(ticketRepository.selectTicketByEventId(eventId).size(), 0);
    }

    public TicketEntity createTicketEntity(){
        return TicketEntity.builder()
                .eventId(1L)
                .price(1000L)
                .name("일반")
                .quantity(100L)
                .is_deleted(false)
                .build();
    }

}