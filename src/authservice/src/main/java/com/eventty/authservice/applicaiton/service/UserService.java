package com.eventty.authservice.applicaiton.service;

import com.eventty.authservice.api.dto.UserCreateRequestDTO;
import com.eventty.authservice.presentation.dto.FullUserCreateRequestDTO;
import com.eventty.authservice.domain.entity.AuthUserEntity;
import com.eventty.authservice.domain.exception.DuplicateEmailException;
import com.eventty.authservice.domain.repository.AuthUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Optional;

@Service
public class UserService {

    private final AuthUserRepository userRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    /*
     대부분의 경우 BCryptPasswordEncoder는 빈의 초기화가 무거운 작업일 수 있고 항상 필요하지 않기 때문에,
     필요한 시점에 생성되도록 @Lazy 사용
     */
    @Autowired
    public UserService(AuthUserRepository userRepository,
                       @Lazy BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }


    public void isEmailDuplicate(String email) {
        Optional<AuthUserEntity> existingUser = userRepository.findByEmail(email);

        if (existingUser.isPresent()) {
            throw new DuplicateEmailException();
        }
    }

    public void createUser(FullUserCreateRequestDTO fullUserCreateRequestDTO) {
        // 이메일 중복 검사
        String email = fullUserCreateRequestDTO.getEmail();
        isEmailDuplicate(email);

        AuthUserEntity newUser = fullUserCreateRequestDTO.toAuthUserEntity(bCryptPasswordEncoder);
        userRepository.save(newUser);

        // API 요청 로직 + compensating Transaction

        UserCreateRequestDTO userCreateRequestDTO = fullUserCreateRequestDTO.toUserCreateRequestDTO();


    }
}
