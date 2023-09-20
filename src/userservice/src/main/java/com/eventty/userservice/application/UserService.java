package com.eventty.userservice.application;

import com.eventty.userservice.application.dto.request.UserCreateRequestDTO;
import com.eventty.userservice.application.dto.request.UserImageUpdateRequestDTO;
import com.eventty.userservice.application.dto.request.UserUpdateRequestDTO;
import com.eventty.userservice.application.dto.response.HostFindByIdResposneDTO;
import com.eventty.userservice.application.dto.response.UserFindByIdResponseDTO;
import com.eventty.userservice.application.dto.response.UserImageFindByIdResponseDTO;
import com.eventty.userservice.domain.UserEntity;

public interface UserService {
    /**
     * api-회원가입
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
    public UserEntity updateMyInfo(Long userId, UserUpdateRequestDTO userUpdateRequestDTO, UserImageUpdateRequestDTO userImageUpdateRequestDTO);

    public HostFindByIdResposneDTO apiGetHostInfo(Long hostId);

    public UserImageFindByIdResponseDTO apiGetUserImage(Long userId);
}
