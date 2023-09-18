package com.eventty.userservice.application;

import com.eventty.userservice.application.dto.*;
import com.eventty.userservice.application.dto.request.UserCreateRequestDTO;
import com.eventty.userservice.application.dto.request.UserImageUpdateRequestDTO;
import com.eventty.userservice.application.dto.request.UserUpdateRequestDTO;
import com.eventty.userservice.application.dto.response.UserFindByIdResponseDTO;
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

import java.io.IOException;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserJPARepository userJPARepository;
    private final UserImageJPARepository userImageJPARepository;
    private final FileHandler fileHandler;
    private final EntityManager em;

    @Transactional
    public UserEntity createUser(UserCreateRequestDTO userCreateRequestDTO){
        duplicateUserExceptionCheck(userCreateRequestDTO);
        return userJPARepository.save(userCreateRequestDTO.toEntity());
    }

    @Transactional
    public UserFindByIdResponseDTO findUserById(Long id){
        UserEntity user = findUserByEMAndDB(id);

        String base64EncodedFile = null;
        String originFileName = null;
        if(user.getImageId() != null){
            UserImageEntity userImage = findUserImageByEMAndDB(user.getImageId());
            base64EncodedFile = getFileInfo(userImage.getStoredFilePath());
            originFileName = userImage.getOriginalFileName();
        }

        return new UserFindByIdResponseDTO(user, base64EncodedFile, originFileName);
    }

    @Transactional
    public UserEntity updateUser(Long id,
                           UserUpdateRequestDTO userUpdateRequestDTO,
                           UserImageUpdateRequestDTO userImageUpdateRequestDTO){

        // 파일 저장
        UserImageDTO userImageDTO = fileUpload(id, userImageUpdateRequestDTO);
        Long imageId = userImageDTO.getId();
        if(imageId == null || imageId != 0)
            imageId = userImageJPARepository.save(userImageDTO.toEntity()).getId();

        // 회원 정보 저장
        return userJPARepository.save(
                new UserUpdateDTO(findUserByEMAndDB(id))
                        .toEntity(userUpdateRequestDTO, imageId)
        );
    }
    // *****************************************************************************************************************
    /**
     * 파일 관련 기능
     */
    private UserImageDTO fileUpload(Long id, UserImageUpdateRequestDTO userImageUpdateRequestDTO){

        UserImageDTO userImageDTO = null;
        try{
            userImageDTO = fileHandler.parseFileInfo(id, userImageUpdateRequestDTO.getImage());
        }catch (IOException e){
            log.error("fileUpload Fail" + e.getMessage());
        }

        // iamge가 없을 경우 0리턴)
        if(userImageDTO == null) {
            return  UserImageDTO.builder().id(0L).build();
        }

        // 저장
        userImageDTO.setId(userImageUpdateRequestDTO.getImageId());
        return userImageDTO;
    }

    // NCP에서 파일 내려받기
    private String getFileInfo(String filePath){
        try {
            return fileHandler.getFileInfo(filePath);
        }catch (IOException e){
            log.error("getFile error : " + e.getMessage());
        }
        return null;
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
