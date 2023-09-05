package com.eventty.authservice.application.service;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.eventty.authservice.presentation.dto.FullUserCreateRequestDTO;
import com.eventty.authservice.applicaiton.service.subservices.UserDetailServiceImpl;
import com.eventty.authservice.domain.entity.AuthUserEntity;
import com.eventty.authservice.domain.repository.AuthUserRepository;
import com.eventty.authservice.domain.Enum.UserRole;
import com.eventty.authservice.domain.entity.AuthorityEntity;
import com.eventty.authservice.domain.exception.DuplicateEmailException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/*
패스워드 검증에는 인코더가 필요한 상황이므로 TestConfig에서 @Bean으로 등록 후 @Mock으로 등록

 */

@ExtendWith(MockitoExtension.class)
public class UserDetailServiceImplTest {

    @InjectMocks
    private UserDetailServiceImpl userServiceImpl;

    @Mock
    private AuthUserRepository userRepository;

    @Mock
    private EntityManager em;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @Test
    @DisplayName("[Local] 회원가입 및 권한 정보 저장 성공")
    public void create_SUCCESS() {
        Long id = 1L;
        String email = createEmail(id);
        FullUserCreateRequestDTO fullUserCreateRequestDTO = createFullUserCreateRequestDTO(email);
        UserRole userRole = UserRole.USER;

        // When
        AuthUserEntity newUser = userServiceImpl.create(fullUserCreateRequestDTO, userRole);
`
        /*  오류: 새로운 생성자를 만들면 위에서 실행했을 때 만들어 지는 newAutority 랑은 다른 생성자로 만들어 지는 것임.
        AuthorityEntity newAuthority = AuthorityEntity.builder()
                .name(userRole.getRole())
                .authUserEntity(newUser)
                .build();
                */

        // Then
       verify(em, times(1)).persist(newUser);
       verify(em, times(1)).persist(any(AuthorityEntity.class));
    }

    @Test
    @DisplayName("회원가입 실패 - Duplicate Email")
    public void createUser_Duplicate_Email() {
        // given: authUserRepository의 findByEmail 메서드를 Stubbing하여 이미 중복된 이메일이 존재하는 것으로 설정
        Long id = 1L;
        String email = createEmail(id);
        FullUserCreateRequestDTO fullUserCreateRequestDTO = createFullUserCreateRequestDTO(email);
        UserRole userRole = UserRole.USER;
        AuthUserEntity authUserEntity = fullUserCreateRequestDTO.toAuthUserEntity(bCryptPasswordEncoder);

        // when: authUserRepository의 findByEmail 메서드를 Stubbing하여 이미 중복된 이메일이 존재하는 것으로 설정
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(authUserEntity));

        // Then
        assertThrows(DuplicateEmailException.class, () -> userServiceImpl.create(fullUserCreateRequestDTO, userRole));
        verify(em, times(0)).persist(any(AuthUserEntity.class));
        verify(em, times(0)).persist(any(AuthorityEntity.class));
    }

    @Test
    @DisplayName("이메일 검증 - Duplicate Email")
    public void isEmailDuplicate_Duplicate_Email() {
        // given
        Long id = 1L;
        String email = createEmail(id);
        AuthUserEntity authUserEntity = createAuthUserEntity(id);

        // when
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(authUserEntity));

        // Then
        assertThrows(DuplicateEmailException.class, () -> userServiceImpl.validateEmail(email));
        verify(userRepository, times(1)).findByEmail(email);
    }


    private static AuthUserEntity createAuthUserEntity(Long id) {
        return AuthUserEntity.builder()
                .id(id)
                .email(createEmail(id))
                .password("123123")
                .build();
    }

    private static String createEmail(Long id) {
        return String.format("Example%d@mm.mm", id);
    }

    private static FullUserCreateRequestDTO createFullUserCreateRequestDTO(String email) {
        return FullUserCreateRequestDTO.builder()
                .email(email)
                .password("123123")
                .name("eventty0")
                .address("서울시 강남")
                .birth(LocalDate.now())
                .phone("000-0000-0000")
                .build();
    }

}

