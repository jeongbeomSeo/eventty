package com.eventty.applyservice.application;

import com.eventty.applyservice.application.dto.CheckAlreadyApplyUserDTO;
import com.eventty.applyservice.application.dto.CreateApplyDTO;
import com.eventty.applyservice.application.dto.request.CreateApplyRequestDTO;
import com.eventty.applyservice.domain.ApplyReposiroty;
import com.eventty.applyservice.domain.exception.AlreadyApplyUserException;
import com.eventty.applyservice.domain.exception.AlreadyCancelApplyException;
import com.eventty.applyservice.domain.exception.ExceedApplicantsException;
import com.eventty.applyservice.domain.exception.NonExistentIdException;
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

        validateBeforeApply(userId, eventId, createApplyRequestDTO);

        return applyReposiroty.insertApply(CreateApplyDTO
                .builder()
                .userId(userId)
                .eventId(eventId)
                .ticketId(ticketId)
                .build());
    }

    @Override
    public Long cancelApply(Long applyId) {

        // 신청 취소전 유효성 검증
        validateBeforeCancel(applyId);

        return applyReposiroty.deleteApply(applyId);
    }

    //------------------------------------------ validation -----------------------------------------------//

    private void validateBeforeApply(Long userId, Long eventId, CreateApplyRequestDTO createApplyRequestDTO){
        // 이미 신청한 유저인지 확인
        CheckAlreadyApplyUserDTO checkAlreadyApplyUserDTO = new CheckAlreadyApplyUserDTO(userId, eventId);
        if(applyReposiroty.checkAlreadyApplyUser(checkAlreadyApplyUserDTO) >= MAX_APPLY_NUM)
            throw new AlreadyApplyUserException(checkAlreadyApplyUserDTO);
        
        // 신청 인원수 확인
        Long count = applyReposiroty.checkApplyNum(createApplyRequestDTO.getEventId());
        if(createApplyRequestDTO.getParticipateNum() <= count)
            throw new ExceedApplicantsException(count);
    }

    private void validateBeforeCancel(Long applyId){
        Boolean result = applyReposiroty.deleteCheck(applyId);
        // 유효하지 않는 appyId
        if(result == null)  throw new NonExistentIdException();
        // 이미 신청 취소한 applyId
        else if(!result)    throw new AlreadyCancelApplyException();
    }
}
