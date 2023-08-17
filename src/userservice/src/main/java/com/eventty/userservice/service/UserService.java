package com.eventty.userservice.service;

import com.eventty.userservice.dto.UserCreateRequestDTO;
import com.eventty.userservice.entity.User;
import com.eventty.userservice.repository.UserJPARepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    UserJPARepository userJPARepository;

    @Autowired
    public UserService(UserJPARepository userJPARepository){
        this.userJPARepository = userJPARepository;
    }

    /**
     * 회원가입
     *
     * @author khg
     * @param userCreateRequestDTO
     * @return void
     */
    public User save(UserCreateRequestDTO userCreateRequestDTO){
        return userJPARepository.save(userCreateRequestDTO.toEntity());
    }
}
