package com.eventty.authservice.presentation;

import com.eventty.authservice.presentation.dto.IsUserDuplicateRequestDTO;
import com.eventty.authservice.applicaiton.service.UserService;
import com.eventty.authservice.common.Enum.SuccessCode;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eventty.authservice.presentation.dto.FullUserCreateRequestDTO;
import com.eventty.authservice.common.response.ResponseDTO;
import com.eventty.authservice.common.response.SuccessResponseDTO;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@Tag(name= "Auth", description = "Auth API")
public class AuthController {

    private final UserService userService;


    /**
     * 회원가임
     */
    @PostMapping("")
    public ResponseEntity<ResponseDTO> createUser(@Valid @RequestBody FullUserCreateRequestDTO userCreateRequestDTO) {

        userService.createUser(userCreateRequestDTO);

        SuccessCode code = SuccessCode.USER_CREATED;
        return ResponseEntity
                .status(code.getStatus())
                .body(SuccessResponseDTO.of(null, code));
    }

    /**
     * 이메일 검증
     */
    @PostMapping("/email")
    public ResponseEntity<SuccessResponseDTO> isDuplicateEmail(@Valid @RequestBody IsUserDuplicateRequestDTO isUserDuplicateRequestDTO) {

        userService.isEmailDuplicate(isUserDuplicateRequestDTO.getEmail());

        SuccessCode code = SuccessCode.EMAIL_IS_VALID;
        return ResponseEntity
                .status(code.getStatus())
                .body(SuccessResponseDTO.of(true, code));
    }

}
