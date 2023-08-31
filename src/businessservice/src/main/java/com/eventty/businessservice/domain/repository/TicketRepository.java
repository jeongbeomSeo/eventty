package com.eventty.businessservice.domain.repository;

import com.eventty.businessservice.domain.entity.EventEntity;
import com.eventty.businessservice.domain.entity.TicketEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TicketRepository {
    List<TicketEntity> selectTicketByEventId(Long id);
    Long insertTicket(TicketEntity ticket);
}