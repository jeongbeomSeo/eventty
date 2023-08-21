package com.eventty.businessservice.domains.event.application.serviceImpl;

import com.eventty.businessservice.domains.event.application.dto.EventDetailResponseDTO;
import com.eventty.businessservice.domains.event.application.service.EventDetailService;
import com.eventty.businessservice.domains.event.domain.EventDetailEntity;
import com.eventty.businessservice.domains.event.domain.EventDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class EventDetailServiceImpl implements EventDetailService {

    private final EventDetailRepository eventDetailRepository;

    @Override
    public EventDetailResponseDTO findEventDetailById(Long id){
        EventDetailEntity detail = eventDetailRepository.selectEventDetailById(id);
        return EventDetailResponseDTO.fromEntity(detail);
    }

}
