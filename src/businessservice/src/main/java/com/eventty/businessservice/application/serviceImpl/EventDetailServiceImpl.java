package com.eventty.businessservice.application.serviceImpl;

import com.eventty.businessservice.application.dto.response.EventDetailResponseDTO;
import com.eventty.businessservice.application.service.EventDetailService;
import com.eventty.businessservice.domain.EventDetailRepository;
import com.eventty.businessservice.domain.exception.EventDetailNotFoundException;
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
