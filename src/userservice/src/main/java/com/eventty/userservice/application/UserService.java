package com.eventty.userservice.application;

import com.eventty.userservice.application.dto.request.UserCheckRequestDTO;
import com.eventty.userservice.application.dto.request.UserCreateRequestDTO;
import com.eventty.userservice.application.dto.request.UserImageUpdateRequestDTO;
import com.eventty.userservice.application.dto.request.UserUpdateRequestDTO;
import com.eventty.userservice.application.dto.response.HostFindByIdResposneDTO;
import com.eventty.userservice.application.dto.response.UserFindByIdResponseDTO;
import com.eventty.userservice.application.dto.response.UserImageFindByIdResponseDTO;
import com.eventty.userservice.application.dto.response.UserUpdateImageResponseDTO;
import com.eventty.userservice.domain.UserEntity;

import java.util.List;

public interface UserService {
    /**
     * (API) 회원가입
     * @param userCreateRequestDTO
     * @return
     */
    public UserEntity signUp(UserCreateRequestDTO userCreateRequestDTO);
    /**
     * 내 정보 조회
     * @param id
     * @return
     */
    public UserFindByIdResponseDTO getMyInfo(Long id);

    /**
     * 내 정보 수정
     * @param userId
     * @param userUpdateRequestDTO
     * @param userImageUpdateRequestDTO
     * @return
     */
    public UserUpdateImageResponseDTO updateMyInfo(Long userId, UserUpdateRequestDTO userUpdateRequestDTO, UserImageUpdateRequestDTO userImageUpdateRequestDTO);

    /**
     * (API) 호스트 정보 조회
     * @param hostId
     * @return
     */
    public HostFindByIdResposneDTO apiGetHostInfo(Long hostId);

    /**
     * (API) 유저 이미지 조회
     * @param userId
     * @return
     */
    public UserImageFindByIdResponseDTO apiGetUserImage(Long userId);

    /**
     * (API) 유저 정보 확인 (비밀번호 찾기)
     */
    public List<Long> apiCheckUserInfo(UserCheckRequestDTO userCheckRequestDTO);
}
