package com.eventty.businessservice.domains.event.domain.repository;

import com.eventty.businessservice.domains.event.domain.entity.TicketEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TicketRepository {
    List<TicketEntity> selectTicketByEventId(Long eventId);
    Long insertTicket(TicketEntity ticket);
    Long deleteTicket(Long eventId);
}