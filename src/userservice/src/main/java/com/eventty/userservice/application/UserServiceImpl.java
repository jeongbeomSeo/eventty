package com.eventty.userservice.application;

import com.eventty.userservice.application.dto.*;
import com.eventty.userservice.application.dto.request.UserCreateRequestDTO;
import com.eventty.userservice.application.dto.request.UserImageUpdateRequestDTO;
import com.eventty.userservice.application.dto.request.UserUpdateRequestDTO;
import com.eventty.userservice.application.dto.response.HostFindByIdResposneDTO;
import com.eventty.userservice.application.dto.response.UserFindByIdResponseDTO;
import com.eventty.userservice.application.dto.response.UserImageFindByIdResponseDTO;
import com.eventty.userservice.domain.UserEntity;
import com.eventty.userservice.domain.UserImageEntity;
import com.eventty.userservice.domain.UserImageJPARepository;
import com.eventty.userservice.domain.exception.DuplicateUserIdException;
import com.eventty.userservice.domain.exception.UserInfoNotFoundException;
import com.eventty.userservice.domain.UserJPARepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserJPARepository userJPARepository;
    private final UserImageJPARepository userImageJPARepository;
    private final FileHandler fileHandler;
    private final EntityManager em;

    @Transactional
    public UserEntity signUp(UserCreateRequestDTO userCreateRequestDTO){
        duplicateUserExceptionCheck(userCreateRequestDTO);
        return userJPARepository.save(userCreateRequestDTO.toEntity());
    }

    @Transactional
    public UserFindByIdResponseDTO getMyInfo(Long id){
        UserEntity user = findUserByEMAndDB(id);

        UserImageEntity userImage = null;
        if(user.getImageId() != null)
            userImage = findUserImageByEMAndDB(user.getImageId());

        return new UserFindByIdResponseDTO(user, userImage);
    }

    @Transactional
    public UserEntity updateMyInfo(Long userId,
                                   UserUpdateRequestDTO userUpdateRequestDTO,
                                   UserImageUpdateRequestDTO userImageUpdateRequestDTO){

        Long imageId = null;
        UserImageDTO userImageDTO = fileUpload(userId, userImageUpdateRequestDTO);
        userImageDTO.setId(userImageDTO == null || userImageDTO.getId() == null ? 0L : userImageDTO.getId());

        // 파일 저장
        if("true".equalsIgnoreCase(userImageUpdateRequestDTO.getIsUpdate()) && userImageDTO.getId() == 0L && userImageDTO.getOriginalFileName() != null){
            imageId = userImageJPARepository.save(userImageDTO.toEntity()).getId();
        }else{
            String id = userImageUpdateRequestDTO.getImageId();
            imageId = ("null".equalsIgnoreCase(id) || "".equals(id) || id == null || userImageDTO.getId() == 0L) ? 0L : Long.parseLong(id);
        }

        // 회원 정보 저장
        return userJPARepository.save(
                new UserUpdateDTO(findUserByEMAndDB(userId))
                        .toEntity(userUpdateRequestDTO, imageId)
        );
    }

    @Override
    public HostFindByIdResposneDTO apiGetHostInfo(Long hostId) {
        UserEntity host = findUserByEMAndDB(hostId);
        return new HostFindByIdResposneDTO(host);
    }

    @Override
    public UserImageFindByIdResponseDTO apiGetUserImage(Long userId) {
        UserEntity user = findUserByEMAndDB(userId);
        UserImageEntity userImage = findUserImageByEMAndDB(user.getImageId());
        return new UserImageFindByIdResponseDTO(userImage);
    }
    // *****************************************************************************************************************
    /**
     * 파일 관련 기능
     */
    private UserImageDTO fileUpload(Long userId, UserImageUpdateRequestDTO userImageUpdateRequestDTO){

        // 파일이 비었을 경우
        MultipartFile multipartFile = userImageUpdateRequestDTO.getImage();
        if (multipartFile == null || multipartFile.isEmpty())
            return UserImageDTO.builder().id(0L).build();

        // 사진이 그대로인 경우
        if("false".equalsIgnoreCase(userImageUpdateRequestDTO.getIsUpdate()))
            return UserImageDTO.builder().id(Long.parseLong(userImageUpdateRequestDTO.getImageId())).build();

        // 파일 정보 parsing
        UserImageDTO userImageDTO = fileHandler.parseFileInfo(userId, userImageUpdateRequestDTO.getImage());

        // 파일 저장
        try{
            fileHandler.saveFileInfo(userImageDTO, userImageUpdateRequestDTO.getImage());
        }catch (IOException e){
            log.error("fileUpload error occured : {}", e.getMessage());
        }

        // 저장
        return userImageDTO;
    }

    // *****************************************************************************************************************
    /**
     * User 관련 기능
     */
    private UserEntity findUserByEMAndDB(Long id){
        return Optional.ofNullable(
                em.find(UserEntity.class, id)
        ).orElseThrow(
                () -> new UserInfoNotFoundException(id)
        );
    }

    private UserImageEntity findUserImageByEMAndDB(Long id){
        return Optional.ofNullable(
                em.find(UserImageEntity.class, id)
        ).orElseThrow(
                () -> new UserInfoNotFoundException(id)
        );
    }

    // userId 중복 검사
    private void duplicateUserExceptionCheck(UserCreateRequestDTO userCreateRequestDTO){
        Optional.ofNullable(
                em.find(UserEntity.class, userCreateRequestDTO.getUserId())
        ).ifPresent(
                entity -> {
                    throw new DuplicateUserIdException(entity);
                }
        );
    }

}
