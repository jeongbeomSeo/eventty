package com.eventty.applyservice.presentation;

import com.eventty.applyservice.application.ApplyService;
import com.eventty.applyservice.application.dto.request.CreateApplyRequestDTO;
import com.eventty.applyservice.domain.annotation.ApiErrorCode;
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
import org.springframework.web.bind.annotation.*;

import static com.eventty.applyservice.domain.code.ErrorCode.*;

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
    @ApiErrorCode({ALREADY_APPLY_USER, EXCEED_APPLICANTS})
    @Permission(Roles = {UserRole.USER})
    public ResponseEntity<SuccessResponseDTO> applyEvent(@RequestBody @Valid CreateApplyRequestDTO createApplyRequestDTO){

        Long userId = getUserIdBySecurityContextHolder();

        applyService.createApply(userId, createApplyRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/applies/{applyId}")
    @ApiSuccessData(stateCode = "200")
    @ApiErrorCode({NON_EXISTENT_ID, ALREADY_CANCELED_APPLY})
    @Permission(Roles = {UserRole.USER})
    public ResponseEntity<SuccessResponseDTO> cancelApply(@PathVariable("applyId")Long applyId){
        applyService.cancelApply(applyId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    private Long getUserIdBySecurityContextHolder(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return Long.parseLong(authentication.getPrincipal().toString());
    }
}
