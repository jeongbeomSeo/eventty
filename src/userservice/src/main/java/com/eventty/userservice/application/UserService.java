package com.eventty.userservice.application;

import com.eventty.userservice.application.dto.*;
import com.eventty.userservice.application.dto.request.UserCreateRequestDTO;
import com.eventty.userservice.application.dto.request.UserUpdateRequestDTO;
import com.eventty.userservice.application.dto.response.UserFindByIdResponseDTO;
import com.eventty.userservice.domain.UserEntity;
import com.eventty.userservice.domain.exception.DuplicateUserIdException;
import com.eventty.userservice.domain.exception.UserInfoNotFoundException;
import com.eventty.userservice.domain.UserJPARepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final EntityManager em;
    private final UserJPARepository userJPARepository;

    @Transactional
    public UserEntity createUser(UserCreateRequestDTO userCreateRequestDTO){
        duplicateExceptionCheck(userCreateRequestDTO);
        return userJPARepository.save(userCreateRequestDTO.toEntity());
    }

    @Transactional
    public UserFindByIdResponseDTO findUserById(Long id){
        return new UserFindByIdResponseDTO(findUserByEMAndDB(id));
    }

    @Transactional
    public UserEntity updateUser(Long id, UserUpdateRequestDTO userUpdateRequestDTO){
        UserUpdateDTO userUpdateDTO = new UserUpdateDTO(findUserByEMAndDB(id));
        return userJPARepository.save(userUpdateDTO.toEntity(userUpdateRequestDTO));
    }

    private void duplicateExceptionCheck(UserCreateRequestDTO userCreateRequestDTO){
        Optional.ofNullable(
                em.find(UserEntity.class, userCreateRequestDTO.getUserId())
        ).ifPresent(
                entity -> {
                    throw new DuplicateUserIdException(entity);
                }
        );
    }

    private UserEntity findUserByEMAndDB(Long id){
        return Optional.ofNullable(
                em.find(UserEntity.class, id)
        ).orElseThrow(
                () -> new UserInfoNotFoundException(id)
        );
    }
}
