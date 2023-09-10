package com.eventty.userservice.presentation;

import com.eventty.userservice.application.UserService;
import com.eventty.userservice.application.dto.request.UserUpdateRequestDTO;
import com.eventty.userservice.application.dto.response.UserFindByIdResponseDTO;
import com.eventty.userservice.domain.annotation.ApiErrorCode;
import com.eventty.userservice.domain.annotation.ApiSuccessData;
import com.eventty.userservice.application.dto.request.UserCreateRequestDTO;
import com.eventty.userservice.presentation.dto.SuccessResponseDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.eventty.userservice.domain.code.ErrorCode.*;

@RestController
@RequestMapping("/api/users")
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
    @PostMapping("/me")
    @ApiSuccessData(stateCode = "201")
    @ApiErrorCode({INVALID_INPUT_VALUE, INVALID_JSON})
    public ResponseEntity<SuccessResponseDTO> postMe(@RequestBody @Valid UserCreateRequestDTO userCreateRequestDTO){
        userService.createUser(userCreateRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
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
    @ApiSuccessData(UserFindByIdResponseDTO.class)
    @ApiErrorCode(USER_INFO_NOT_FOUND)
    public ResponseEntity<SuccessResponseDTO> getMe(@PathVariable Long userId){

        // 토큰 내에 있는 정보 UserId Get!
        // Source 추가
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(SuccessResponseDTO.of(userService.findUserById(userId)));
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
    @ApiSuccessData()
    @ApiErrorCode({USER_INFO_NOT_FOUND, INVALID_JSON})
    public ResponseEntity<SuccessResponseDTO> patchMe(@PathVariable Long userId, @RequestBody UserUpdateRequestDTO userUpdateRequestDTO){

        // 토큰 내에 있는 정보 UserId Get!
        // Source 추가

        userService.updateUser(userId, userUpdateRequestDTO);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
