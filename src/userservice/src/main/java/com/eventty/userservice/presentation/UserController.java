package com.eventty.userservice.presentation;

import com.eventty.userservice.application.UserService;
import com.eventty.userservice.application.dto.request.UserImageUpdateRequestDTO;
import com.eventty.userservice.application.dto.request.UserUpdateRequestDTO;
import com.eventty.userservice.application.dto.response.UserFindByIdResponseDTO;
import com.eventty.userservice.domain.annotation.ApiErrorCode;
import com.eventty.userservice.domain.annotation.ApiSuccessData;
import com.eventty.userservice.application.dto.request.UserCreateRequestDTO;
import com.eventty.userservice.domain.annotation.Permission;
import com.eventty.userservice.domain.code.UserRole;
import com.eventty.userservice.presentation.dto.SuccessResponseDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.eventty.userservice.domain.code.ErrorCode.*;

@RestController
@Tag(name = "User", description = "User Server - About Users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * 회원가입
     *
     * @author khg
     * @param userCreateRequestDTO
     * @return ResponseEntity<SuccessResponseDTO>
     */
    @PostMapping("/api/users/me")
    @ApiSuccessData(stateCode = "201")
    @ApiErrorCode({INVALID_INPUT_VALUE, INVALID_JSON})
    public ResponseEntity<SuccessResponseDTO> postMe(@RequestBody @Valid UserCreateRequestDTO userCreateRequestDTO){
        userService.createUser(userCreateRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * ID, PW 제외한 회원 정보 조회
     * @return ResponseEntity<SuccessResponseDTO>
     */
    @GetMapping("/users/me")
    @ApiSuccessData(UserFindByIdResponseDTO.class)
    @ApiErrorCode(USER_INFO_NOT_FOUND)
    @Permission(Roles = {UserRole.USER, UserRole.HOST})
    public ResponseEntity<SuccessResponseDTO> getMe(){

        Long userId = getUserIdBySecurityContextHolder();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(SuccessResponseDTO.of(userService.findUserById(userId)));
    }

    /**
     * 회원정보 수정
     * @param userUpdateRequestDTO
     * @return ResponseEntity<SuccessResponseDTO>
     */
    @PatchMapping("/users/me")
    @ApiSuccessData()
    @ApiErrorCode({USER_INFO_NOT_FOUND, INVALID_JSON})
    @Permission(Roles = {UserRole.USER, UserRole.HOST})
    public ResponseEntity<SuccessResponseDTO> patchMe(@ModelAttribute UserUpdateRequestDTO userUpdateRequestDTO,
                                                      @ModelAttribute UserImageUpdateRequestDTO userImageUpdateRequestDTO){

        Long userId = getUserIdBySecurityContextHolder();

        userService.updateUser(userId, userUpdateRequestDTO, userImageUpdateRequestDTO);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    private Long getUserIdBySecurityContextHolder(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return Long.parseLong(authentication.getPrincipal().toString());
    }

}
