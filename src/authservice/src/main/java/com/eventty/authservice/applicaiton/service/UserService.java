package com.eventty.authservice.applicaiton.service;

import com.eventty.authservice.api.ApiClient;
import com.eventty.authservice.api.dto.UserCreateRequestDTO;
import com.eventty.authservice.common.response.ErrorResponseDTO;
import com.eventty.authservice.common.response.ResponseDTO;
import com.eventty.authservice.api.exception.ApiException;
import com.eventty.authservice.presentation.dto.FullUserCreateRequestDTO;
import com.eventty.authservice.domain.entity.AuthUserEntity;
import com.eventty.authservice.domain.exception.DuplicateEmailException;
import com.eventty.authservice.domain.repository.AuthUserRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final AuthUserRepository userRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ApiClient apiClient;

    private EntityManager em;

    /*
     대부분의 경우 BCryptPasswordEncoder는 빈의 초기화가 무거운 작업일 수 있고 항상 필요하지 않기 때문에,
     필요한 시점에 생성되도록 @Lazy 사용
     */
    @Autowired
    public UserService(AuthUserRepository userRepository,
                       @Lazy BCryptPasswordEncoder bCryptPasswordEncoder,
                       @Lazy ApiClient apiClient,
                       EntityManager em) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.apiClient = apiClient;
        this.em = em;
    }

    @Transactional
    public void createUser(FullUserCreateRequestDTO fullUserCreateRequestDTO) {
        // 이메일 중복 검사
        String email = fullUserCreateRequestDTO.getEmail();
        isEmailDuplicate(email);

        AuthUserEntity newUser = fullUserCreateRequestDTO.toAuthUserEntity(bCryptPasswordEncoder);

        // EntityManager를 사용하여 데이터베이스에 저장
        em.persist(newUser);

        // 저장된 엔티티의 ID 가져오기
        Long authId = newUser.getId();

        // API 요청 로직
        UserCreateRequestDTO userCreateRequestDTO = fullUserCreateRequestDTO.toUserCreateRequestDTO(authId);
        ResponseEntity<ResponseDTO> response =  apiClient.createUserApi(userCreateRequestDTO);

        System.out.println(response);

        // 보상 트랜 잭션 + 예외 던지기
        // 보상 트랜잭션의 경우 예외가 발생하면 @Transactional에 의해서 수행되며
        // 예외는 기존의 ErrorResponseDTO를 그대로 건네주면서 전역적으로 처리

    }

    public void isEmailDuplicate(String email) {
        Optional<AuthUserEntity> existingUser = userRepository.findByEmail(email);

        if (existingUser.isPresent()) {
            throw DuplicateEmailException.EXCEPTION;
        }
    }
}
