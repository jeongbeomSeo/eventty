package com.eventty.applyservice.domain;

import com.eventty.applyservice.application.dto.CheckAlreadyApplyUserDTO;
import com.eventty.applyservice.application.dto.CreateApplyDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ApplyReposiroty {
    public Long insertApply(CreateApplyDTO createApplyDTO);
    public Long checkApplyNum(Long eventId);
    public Long checkAlreadyApplyUser(CheckAlreadyApplyUserDTO checkAlreadyApplyUserDTO);
}
