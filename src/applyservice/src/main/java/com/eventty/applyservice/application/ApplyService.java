package com.eventty.applyservice.application;

import com.eventty.applyservice.application.dto.request.CreateApplyRequestDTO;
import com.eventty.applyservice.domain.ApplyEntity;

public interface ApplyService {

    public Long createApply(Long userId, CreateApplyRequestDTO applyEventRequestDTO);

    public Long cancelApply(Long applyId);
}
