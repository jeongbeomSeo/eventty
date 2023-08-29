package com.eventty.userservice.presentation;

import com.eventty.userservice.application.UserService;
import com.eventty.userservice.application.dto.response.UserCreateAndUpdateResponseDTO;
import com.eventty.userservice.application.dto.request.UserUpdateRequestDTO;
import com.eventty.userservice.domain.code.SuccessCode;
import com.eventty.userservice.application.dto.request.UserCreateRequestDTO;
import com.eventty.userservice.application.dto.response.UserFindByIdResponseDTO;
import com.eventty.userservice.presentation.dto.SuccessResponseDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/users")
@Tag(name = "User", description = "User Server - About Users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    public <T> ResponseEntity<SuccessResponseDTO> makeResponse(T response, SuccessCode successCode){
        return ResponseEntity
                .status(successCode.getStatus())
                .body(SuccessResponseDTO.of(response, successCode));
    }

    /**
     * 회원가입
     *
     * @author khg
     * @param userCreateRequestDTO
     * @return void
     */
    @PostMapping("/register")
    public ResponseEntity<SuccessResponseDTO> register(@RequestBody @Valid UserCreateRequestDTO userCreateRequestDTO){
        UserCreateAndUpdateResponseDTO response =  userService.userCreate(userCreateRequestDTO);

        SuccessCode successCode = SuccessCode.USER_INFO_INSERT;
        return makeResponse(response, successCode);
    }

    /**
     * ID, PW 제외한 회원 정보 조회
     *
     * @author khg
     * @param userId
     * @return ResponseEntity<UserFindByIdResponseDTO>
     */
    // 우선 Test 할 동안만 파라미터로 받겠습니다!
    // 차후 수정 예정
    @GetMapping("/myInfo/{userId}")
    public ResponseEntity<SuccessResponseDTO> getMyInfo(@PathVariable Long userId){

        // 토큰 내에 있는 정보 UserId Get!
        // Source 추가

        UserFindByIdResponseDTO response = userService.userFindById(userId);

        SuccessCode successCode = SuccessCode.USER_INFO_FIND_BY_ID;
        return makeResponse(response, successCode);
    }

    /**
     * 회원정보 수정
     * 
     * @author khg
     * @param userId
     * @param userUpdateRequestDTO
     * @return ResponseEntity<UserFindByIdResponseDTO>
     */
    @PatchMapping("/myInfo/{userId}")
    public ResponseEntity<SuccessResponseDTO> patchMyInfo(@PathVariable Long userId, @RequestBody UserUpdateRequestDTO userUpdateRequestDTO){
        // 토큰 내에 있는 정보 UserId Get!
        // Source 추가

        UserCreateAndUpdateResponseDTO response = userService.userUpdate(userId, userUpdateRequestDTO);

        SuccessCode successCode = SuccessCode.USER_INFO_UPDATE;
        return makeResponse(response, successCode);
    }

}
