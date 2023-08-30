package com.eventty.businessservice.domains.event.application.serviceImpl;

import com.eventty.businessservice.domains.event.application.dto.EventDetailResponseDTO;
import com.eventty.businessservice.domains.event.application.service.EventDetailService;
import com.eventty.businessservice.domains.event.domain.EventDetailRepository;
import com.eventty.businessservice.domains.event.domain.exception.EventDetailNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class EventDetailServiceImpl implements EventDetailService {

    private final EventDetailRepository eventDetailRepository;

    @Override
    public EventDetailResponseDTO findEventDetailById(Long id){
        return Optional.ofNullable(eventDetailRepository.selectEventDetailById(id))
            .map(EventDetailResponseDTO::fromEntity)
            .orElseThrow(()->EventDetailNotFoundException.EXCEPTION);
    }

}
