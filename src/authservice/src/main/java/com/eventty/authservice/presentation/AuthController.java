package com.eventty.authservice.presentation;

import com.eventty.authservice.applicaiton.service.Facade.AuthService;
import com.eventty.authservice.domain.Enum.Roles;
import com.eventty.authservice.presentation.dto.IsUserDuplicateRequestDTO;
import com.eventty.authservice.applicaiton.service.UserServiceImpl;
import com.eventty.authservice.common.Enum.SuccessCode;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.eventty.authservice.presentation.dto.FullUserCreateRequestDTO;
import com.eventty.authservice.common.response.SuccessResponseDTO;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@Tag(name= "Auth", description = "Auth API")
public class AuthController {

    private final AuthService authService;


    /**
     * 회원가입
     */
    @PostMapping("/me/{role}")
    public ResponseEntity<Void> createUser(@Valid @RequestBody FullUserCreateRequestDTO userCreateRequestDTO,
                                           @PathVariable("role") Roles role) {

        authService.createUser(userCreateRequestDTO, role);

        SuccessCode code = SuccessCode.USER_CREATED;
        return ResponseEntity
                .status(code.getStatus())
                .body(null);

        /* 출력 형태

         */
    }

    /**
     * 이메일 검증
     */
    @PostMapping("/email")
    public ResponseEntity<SuccessResponseDTO> isDuplicateEmail(@Valid @RequestBody IsUserDuplicateRequestDTO isUserDuplicateRequestDTO) {

        authService.isEmailDuplicate(isUserDuplicateRequestDTO.getEmail());

        return ResponseEntity
                .status(SuccessCode.IS_OK.getStatus())
                .body(SuccessResponseDTO.of(null));
        /* 출력 형태
            {
            "successResponseDTO": {
                "data": null
            },
            "success": true
        }
         */
    }


}
