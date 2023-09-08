package com.eventty.authservice.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import io.swagger.v3.oas.annotations.tags.Tag;

import com.eventty.authservice.presentation.dto.FullUserCreateRequestDTO;
import com.eventty.authservice.applicaiton.service.Facade.AuthService;
import com.eventty.authservice.domain.Enum.UserRole;
import com.eventty.authservice.presentation.dto.IsUserDuplicateRequestDTO;
import com.eventty.authservice.common.Enum.SuccessCode;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@Tag(name= "Auth", description = "Auth API")
public class AuthController {

    private final AuthService authService;

    /**
     * 회원가입
     */
    @PostMapping("/me/{userRole}")
    public ResponseEntity<Void> createUser(@Valid @RequestBody FullUserCreateRequestDTO userCreateRequestDTO,
                                           @PathVariable("userRole") UserRole userRole) {

        authService.createUser(userCreateRequestDTO, userRole);

        return ResponseEntity
                .status(SuccessCode.USER_CREATED.getStatus())
                .body(null);
    }

    /**
     * 이메일 검증
     */
    @PostMapping("/email")
    public ResponseEntity<Void> isDuplicateEmail(@Valid @RequestBody IsUserDuplicateRequestDTO isUserDuplicateRequestDTO) {

        authService.validateEmailNotDuplicated(isUserDuplicateRequestDTO.getEmail());

        return ResponseEntity
                .status(SuccessCode.IS_OK.getStatus())
                .body(null);
    }


}
