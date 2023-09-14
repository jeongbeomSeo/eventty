package com.eventty.authservice.applicaiton.service.subservices;

import com.eventty.authservice.domain.exception.AccessDeletedUserException;
import com.eventty.authservice.domain.exception.UserNotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

import com.eventty.authservice.domain.Enum.UserRole;
import com.eventty.authservice.domain.entity.AuthorityEntity;
import com.eventty.authservice.domain.entity.AuthUserEntity;
import com.eventty.authservice.domain.exception.DuplicateEmailException;
import com.eventty.authservice.domain.repository.AuthUserRepository;


@Service
public class UserDetailServiceImpl implements UserDetailService {

    private final AuthUserRepository userRepository;

    private EntityManager em;

    @Autowired
    public UserDetailServiceImpl(AuthUserRepository userRepository,
                                 EntityManager em) {
        this.userRepository = userRepository;
        this.em = em;
    }
    @Override
    public Long delete(AuthUserEntity authUserEntity) {
        authUserEntity.setDelete(true);
        authUserEntity.setDeleteDate(LocalDateTime.now());
        userRepository.save(authUserEntity);

        return authUserEntity.getId();
    }

    public AuthUserEntity findAuthUser(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException(email));
    }

    @Override
    public AuthUserEntity findAuthUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
    }

    @Transactional
    public Long create(AuthUserEntity authUserEntity, UserRole userRole) {
        // 이메일 중복 검사
        validateEmail(authUserEntity.getEmail());

        // EntityManager를 사용하여 데이터베이스에 저장 => id 저장
        em.persist(authUserEntity);

        // 권한 저장하기
        AuthorityEntity newAuthority = AuthorityEntity.builder()
                .name(userRole.getRole())
                .authUserEntity(authUserEntity)
                .build();

        em.persist(newAuthority);

        return authUserEntity.getId();
    }

    @Override
    public void validationUser(AuthUserEntity authUserEntity) {
        if (authUserEntity.isDelete())
            throw new AccessDeletedUserException(authUserEntity);
    }

    public void validateEmail(String email) {
        Optional<AuthUserEntity> existingUser = userRepository.findByEmail(email);

        if (existingUser.isPresent()) {
            throw new DuplicateEmailException(email);
        }
    }
}

