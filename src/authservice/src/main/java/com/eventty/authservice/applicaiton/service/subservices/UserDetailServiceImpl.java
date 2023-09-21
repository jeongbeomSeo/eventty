package com.eventty.authservice.applicaiton.service.subservices;

import com.eventty.authservice.applicaiton.service.utils.CustomPasswordEncoder;
import com.eventty.authservice.domain.exception.AccessDeletedUserException;
import com.eventty.authservice.domain.exception.UserNotFoundException;
import com.eventty.authservice.presentation.dto.request.PWChangeRequestDTO;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
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

    // Soft Delete
    @Transactional
    @Override
    public Long delete(AuthUserEntity authUserEntity) {
        authUserEntity.setDelete(true);
        authUserEntity.setDeleteDate(LocalDateTime.now());
        userRepository.save(authUserEntity);

        return authUserEntity.getId();
    }

    @Override
    public AuthUserEntity findAuthUser(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException(email, "Email"));
    }

    @Override
    public AuthUserEntity findAuthUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
    }

    @Transactional
    @Override
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

    // 삭제된 유저인지 확인
    @Override
    public void validationUser(AuthUserEntity authUserEntity) {
        if (authUserEntity.isDelete())
            throw new AccessDeletedUserException(authUserEntity);
    }

    @Override
    public void validateEmail(String email) {
        Optional<AuthUserEntity> existingUser = userRepository.findByEmail(email);

        if (existingUser.isPresent()) {
            throw new DuplicateEmailException(email);
        }
    }

    @Transactional
    @Override
    public AuthUserEntity changePwAuthUser(String encryptedPassword, AuthUserEntity authUserEntity) {

        authUserEntity.setPassword(encryptedPassword);

        em.persist(authUserEntity);

        return authUserEntity;
    }

    @Override
    public List<AuthUserEntity> findNotDeletedAuthUserList(List<Long> userIds) {
        return userIds.stream()
                .map(this::findAuthUser)
                .filter(user -> !user.isDelete())
                .toList();
    }
}

