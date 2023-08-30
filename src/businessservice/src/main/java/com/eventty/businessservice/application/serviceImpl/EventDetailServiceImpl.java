package com.eventty.businessservice.application.serviceImpl;

import com.eventty.businessservice.application.dto.request.EventDetailCreateRequestDTO;
import com.eventty.businessservice.application.dto.response.EventDetailFindByIdResponseDTO;
import com.eventty.businessservice.application.service.EventDetailService;
import com.eventty.businessservice.domain.entity.EventDetailEntity;
import com.eventty.businessservice.domain.repository.EventDetailRepository;
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

    // 이벤트 상세 정보만 조회
    @Override
    public EventDetailFindByIdResponseDTO findEventDetailById(Long id){
        return Optional.ofNullable(eventDetailRepository.selectEventDetailById(id))
            .map(EventDetailFindByIdResponseDTO::fromEntity)
            .orElseThrow(()->EventDetailNotFoundException.EXCEPTION);
    }

    // 이벤트 상세 정보만 저장
    @Override
    public void createEventDetail(EventDetailEntity eventDetail) {
        eventDetailRepository.insertEventDetail(eventDetail);
    }

}
