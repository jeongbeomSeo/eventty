package com.eventty.userservice.presentation;

import com.eventty.userservice.application.UserService;
import com.eventty.userservice.application.dto.request.UserUpdateRequestDTO;
import com.eventty.userservice.domain.code.SuccessCode;
import com.eventty.userservice.application.dto.request.UserCreateRequestDTO;
import com.eventty.userservice.presentation.dto.ResponseDTO;
import com.eventty.userservice.presentation.dto.SuccessResponseDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@Tag(name = "User", description = "User Server - About Users")
@RequiredArgsConstructor
@ResponseStatus(HttpStatus.OK)
public class UserController {

    private final UserService userService;

    /**
     * 회원가입
     *
     * @author khg
     * @param userCreateRequestDTO
     * @return ResponseEntity<SuccessResponseDTO>
     */
    @PostMapping("/me")
    public ResponseDTO postMe(@RequestBody @Valid UserCreateRequestDTO userCreateRequestDTO){
        return SuccessResponseDTO.of(userService.createUser(userCreateRequestDTO), SuccessCode.USER_INFO_INSERT);
    }

    /**
     * ID, PW 제외한 회원 정보 조회
     *
     * @author khg
     * @param userId
     * @return ResponseEntity<SuccessResponseDTO>
     */
    // 우선 Test 할 동안만 파라미터로 받겠습니다!
    // 차후 수정 예정
    @GetMapping("/me/{userId}")
    public ResponseDTO getMe(@PathVariable Long userId){

        // 토큰 내에 있는 정보 UserId Get!
        // Source 추가

        return SuccessResponseDTO.of(userService.findUserById(userId), SuccessCode.USER_INFO_FIND_BY_ID);
    }

    /**
     * 회원정보 수정
     * 
     * @author khg
     * @param userId
     * @param userUpdateRequestDTO
     * @return ResponseEntity<SuccessResponseDTO>
     */
    @PatchMapping("/me/{userId}")
    public ResponseDTO patchMe(@PathVariable Long userId, @RequestBody UserUpdateRequestDTO userUpdateRequestDTO){

        // 토큰 내에 있는 정보 UserId Get!
        // Source 추가

        return SuccessResponseDTO.of(userService.updateUser(userId, userUpdateRequestDTO), SuccessCode.USER_INFO_UPDATE);
    }

}
