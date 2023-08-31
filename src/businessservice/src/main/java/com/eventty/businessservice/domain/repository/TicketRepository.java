package com.eventty.businessservice.domain.repository;

import com.eventty.businessservice.domain.entity.EventEntity;
import com.eventty.businessservice.domain.entity.TicketEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TicketRepository {
    TicketEntity selectTicketByEventId(Long id);
    Long insertTicket(TicketEntity ticket);
}
