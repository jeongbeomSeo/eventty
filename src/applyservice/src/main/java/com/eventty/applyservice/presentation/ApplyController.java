package com.eventty.applyservice.presentation;

import com.eventty.applyservice.application.ApplyService;
import com.eventty.applyservice.application.dto.request.CreateApplyRequestDTO;
import com.eventty.applyservice.domain.annotation.ApiSuccessData;
import com.eventty.applyservice.domain.annotation.Permission;
import com.eventty.applyservice.domain.code.UserRole;
import com.eventty.applyservice.presentation.dto.SuccessResponseDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Apply", description = "Apply Server - About Applies")
@RequiredArgsConstructor
public class ApplyController {

    private final ApplyService applyService;

    /**
     * 행사 신청
     * @param createApplyRequestDTO
     * @return ResponseEntity<SuccessResponseDTO>
     */
    @PostMapping("/applies")
    @ApiSuccessData(stateCode = "201")
    @Permission(Roles = {UserRole.USER})
    public ResponseEntity<SuccessResponseDTO> applyEvent(@RequestBody @Valid CreateApplyRequestDTO createApplyRequestDTO){

        Long userId = getUserIdBySecurityContextHolder();

        applyService.createApply(userId, createApplyRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    private Long getUserIdBySecurityContextHolder(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return Long.parseLong(authentication.getPrincipal().toString());
    }
}
