package com.eventty.userservice.presentation;

import com.eventty.userservice.application.UserServiceImpl;
import com.eventty.userservice.application.dto.request.UserImageUpdateRequestDTO;
import com.eventty.userservice.application.dto.request.UserUpdateRequestDTO;
import com.eventty.userservice.application.dto.response.HostFindByIdResposneDTO;
import com.eventty.userservice.application.dto.response.UserFindByIdResponseDTO;
import com.eventty.userservice.application.dto.response.UserImageFindByIdResponseDTO;
import com.eventty.userservice.domain.annotation.ApiErrorCode;
import com.eventty.userservice.domain.annotation.ApiSuccessData;
import com.eventty.userservice.application.dto.request.UserCreateRequestDTO;
import com.eventty.userservice.domain.annotation.Permission;
import com.eventty.userservice.domain.code.UserRole;
import com.eventty.userservice.infrastructure.context.UserContextHolder;
import com.eventty.userservice.presentation.dto.SuccessResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.eventty.userservice.domain.code.ErrorCode.*;

@RestController
@Tag(name = "User", description = "User Server - About Users")
@RequiredArgsConstructor
public class UserController {

    private final UserServiceImpl userServiceImpl;

    /**
     * (API)ID, PW 제외한 내 정보 등록 (회원가입)
     *
     * @author khg
     * @param userCreateRequestDTO
     * @return ResponseEntity<SuccessResponseDTO>
     */
    @PostMapping("/api/users/me")
    @Operation(summary = "(API)ID, PW 제외한 내 정보 등록 (회원가입)")
    @ApiSuccessData(stateCode = "201")
    @ApiErrorCode({INVALID_INPUT_VALUE, INVALID_JSON})
    public ResponseEntity<SuccessResponseDTO> signUp(@RequestBody @Valid UserCreateRequestDTO userCreateRequestDTO){
        userServiceImpl.signUp(userCreateRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 내 정보 조회
     * @return ResponseEntity<SuccessResponseDTO>
     */
    @GetMapping("/users/me")
    @Operation(summary = "내 정보 조회")
    @ApiSuccessData(UserFindByIdResponseDTO.class)
    @ApiErrorCode(USER_INFO_NOT_FOUND)
    @Permission(Roles = {UserRole.USER, UserRole.HOST})
    public ResponseEntity<SuccessResponseDTO> getMyInfo(){

        Long userId = getUserIdByUserContextHolder();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(SuccessResponseDTO.of(userServiceImpl.getMyInfo(userId)));
    }

    /**
     * 내 정보 수정
     * @param userUpdateRequestDTO
     * @return ResponseEntity<SuccessResponseDTO>
     */
    @PostMapping("/users/me")
    @Operation(summary = "내 정보 수정")
    @ApiSuccessData()
    @ApiErrorCode({USER_INFO_NOT_FOUND, INVALID_JSON})
    @Permission(Roles = {UserRole.USER, UserRole.HOST})
    public ResponseEntity<SuccessResponseDTO> updateMyInfo(@ModelAttribute UserUpdateRequestDTO userUpdateRequestDTO,
                                                           @ModelAttribute UserImageUpdateRequestDTO userImageUpdateRequestDTO){

        Long userId = getUserIdByUserContextHolder();

        userServiceImpl.updateMyInfo(userId, userUpdateRequestDTO, userImageUpdateRequestDTO);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * (API) 호스트 정보 반환
     * @param hostId
     * @return ResponseEntity<SuccessResponseDTO>
     */
    @GetMapping("/api/host")
    @Operation(summary = "(API) 호스트 정보 반환")
    @ApiSuccessData(HostFindByIdResposneDTO.class)
    @Permission(Roles = {UserRole.USER, UserRole.HOST})
    public ResponseEntity<SuccessResponseDTO> apiGetHostInfo(@RequestParam Long hostId){

        HostFindByIdResposneDTO response =  userServiceImpl.apiGetHostInfo(hostId);
        return ResponseEntity.ok(SuccessResponseDTO.of(response));
    }

    /**
     * (API) User Image 반환
     * @return
     */
    @GetMapping("api/image")
    @Operation(summary = "(API) User Image 반환")
    @ApiSuccessData(UserImageFindByIdResponseDTO.class)
    @Permission(Roles = {UserRole.USER, UserRole.HOST})
    public ResponseEntity<SuccessResponseDTO> apiGetUserImage(){

        Long userId = getUserIdByUserContextHolder();

        UserImageFindByIdResponseDTO response = userServiceImpl.apiGetUserImage(userId);
        return ResponseEntity.ok(SuccessResponseDTO.of(response));
    }

    private Long getUserIdByUserContextHolder(){
        return Long.parseLong(UserContextHolder.getContext().getUserId());
    }

}
