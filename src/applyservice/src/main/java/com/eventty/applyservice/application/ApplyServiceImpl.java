package com.eventty.applyservice.application;

import com.eventty.applyservice.application.dto.CheckAlreadyApplyUserDTO;
import com.eventty.applyservice.application.dto.CreateApplyDTO;
import com.eventty.applyservice.application.dto.request.CreateApplyRequestDTO;
import com.eventty.applyservice.domain.ApplyReposiroty;
import com.eventty.applyservice.domain.exception.AlreadyApplyUserException;
import com.eventty.applyservice.domain.exception.ExceedApplicantsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.eventty.applyservice.domain.code.GlobalConstant.MAX_APPLY_NUM;

@Service
@RequiredArgsConstructor
public class ApplyServiceImpl implements ApplyService{

    private final ApplyReposiroty applyReposiroty;

    @Override
    public Long createApply(Long userId, CreateApplyRequestDTO createApplyRequestDTO) {

        Long eventId = createApplyRequestDTO.getEventId();
        Long ticketId = createApplyRequestDTO.getTicketId();

        // 이미 신청한 고객인지 체크(1인 1매만 가능)
        checkAlreadyApplyUser(userId, eventId);

        // 신청 인원수 체크
        checkParticipateNum(createApplyRequestDTO);

        return applyReposiroty.insertApply(CreateApplyDTO
                .builder()
                .userId(userId)
                .eventId(eventId)
                .ticketId(ticketId)
                .build());
    }

    private void checkAlreadyApplyUser(Long userId, Long eventId){
        CheckAlreadyApplyUserDTO checkAlreadyApplyUserDTO = new CheckAlreadyApplyUserDTO(userId, eventId);

        if(applyReposiroty.checkAlreadyApplyUser(checkAlreadyApplyUserDTO) >= MAX_APPLY_NUM)
            throw new AlreadyApplyUserException(checkAlreadyApplyUserDTO);
    }

    private void checkParticipateNum(CreateApplyRequestDTO createApplyRequestDTO){
        Long count = applyReposiroty.checkApplyNum(createApplyRequestDTO.getEventId());
        if(createApplyRequestDTO.getParticipateNum() <= count)
            throw new ExceedApplicantsException(count);
    }
}
