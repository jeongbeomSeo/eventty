package com.eventty.userservice.application;

import com.eventty.userservice.application.dto.*;
import com.eventty.userservice.application.dto.request.UserCreateRequestDTO;
import com.eventty.userservice.application.dto.request.UserUpdateRequestDTO;
import com.eventty.userservice.application.dto.response.UserCreateAndUpdateResponseDTO;
import com.eventty.userservice.application.dto.response.UserFindByIdResponseDTO;
import com.eventty.userservice.domain.UserEntity;
import com.eventty.userservice.domain.exception.UserInfoNotFoundException;
import com.eventty.userservice.domain.UserJPARepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    @Autowired
    private final EntityManager em;

    private final UserJPARepository userJPARepository;



    @Transactional
    public UserCreateAndUpdateResponseDTO userCreate(UserCreateRequestDTO userCreateRequestDTO){
        return new UserCreateAndUpdateResponseDTO(userJPARepository.save(userCreateRequestDTO.toEntity()));
    }

    @Transactional
    public UserFindByIdResponseDTO userFindById(Long id){
        return new UserFindByIdResponseDTO(UserEntityFindById(id));
    }

    @Transactional
    public UserCreateAndUpdateResponseDTO userUpdate(Long id, UserUpdateRequestDTO userUpdateRequestDTO){
        UserUpdateDTO userUpdateDTO = new UserUpdateDTO(UserEntityFindById(id));
        return new UserCreateAndUpdateResponseDTO(userJPARepository.save(userUpdateDTO.toEntity(userUpdateRequestDTO)));
    }

    private UserEntity UserEntityFindById(Long id){
        return Optional.ofNullable(em.find(UserEntity.class, id)).orElseThrow(() -> UserInfoNotFoundException.EXCEPTION);
    }
}
