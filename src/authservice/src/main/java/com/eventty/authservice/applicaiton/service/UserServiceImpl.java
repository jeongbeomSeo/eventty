package com.eventty.authservice.applicaiton.service;

import com.eventty.authservice.api.ApiClient;
import com.eventty.authservice.api.dto.UserCreateRequestDTO;
import com.eventty.authservice.domain.Enum.Roles;
import com.eventty.authservice.domain.entity.AuthorityEntity;
import com.eventty.authservice.presentation.dto.FullUserCreateRequestDTO;
import com.eventty.authservice.domain.entity.AuthUserEntity;
import com.eventty.authservice.domain.exception.DuplicateEmailException;
import com.eventty.authservice.domain.repository.AuthUserRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    private final AuthUserRepository userRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private EntityManager em;

    /*
     대부분의 경우 BCryptPasswordEncoder는 빈의 초기화가 무거운 작업일 수 있고 항상 필요하지 않기 때문에,
     필요한 시점에 생성되도록 @Lazy 사용
     */
    @Autowired
    public UserServiceImpl(AuthUserRepository userRepository,
                           @Lazy BCryptPasswordEncoder bCryptPasswordEncoder,
                           EntityManager em) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.em = em;
    }

    @Transactional
    public AuthUserEntity create(FullUserCreateRequestDTO fullUserCreateRequestDTO, Roles role) {
        // 이메일 중복 검사
        String email = fullUserCreateRequestDTO.getEmail();
        emailValidationCheck(email);

        AuthUserEntity newUser = fullUserCreateRequestDTO.toAuthUserEntity(bCryptPasswordEncoder);

        // EntityManager를 사용하여 데이터베이스에 저장
        em.persist(newUser);

        // 권한 저장하기
        AuthorityEntity newAuthority = AuthorityEntity.builder()
                .name(role.getRole())
                .authUserEntity(newUser)
                .build();

        em.persist(newAuthority);

        return newUser;
    }

    public void emailValidationCheck(String email) {
        Optional<AuthUserEntity> existingUser = userRepository.findByEmail(email);

        if (existingUser.isPresent()) {
            throw DuplicateEmailException.EXCEPTION;
        }
    }
}
