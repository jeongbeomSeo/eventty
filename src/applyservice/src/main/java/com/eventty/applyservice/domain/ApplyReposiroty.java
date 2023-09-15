package com.eventty.applyservice.domain;

import com.eventty.applyservice.application.dto.CheckAlreadyApplyUserDTO;
import com.eventty.applyservice.application.dto.CreateApplyDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ApplyReposiroty {

    // 행사 신청 관련
    public Long insertApply(CreateApplyDTO createApplyDTO);
    public Long checkApplyNum(Long eventId);
    public Long checkAlreadyApplyUser(CheckAlreadyApplyUserDTO checkAlreadyApplyUserDTO);

    // 행사 취소 관련
    public Long deleteApply(Long applyId);
    public Boolean deleteCheck(Long applyId);
}
