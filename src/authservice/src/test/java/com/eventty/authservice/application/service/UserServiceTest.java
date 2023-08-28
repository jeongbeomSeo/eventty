package com.eventty.authservice.application.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.eventty.authservice.domain.exception.DuplicateEmailException;
import com.eventty.authservice.applicaiton.dto.FullUserCreateRequestDTO;
import com.eventty.authservice.applicaiton.service.UserService;
import com.eventty.authservice.domain.entity.AuthUserEntity;
import com.eventty.authservice.domain.repository.AuthUserRepository;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

/*
패스워드 검증에는 인코더가 필요한 상황이므로 TestConfig에서 @Bean으로 등록 후 @Mock으로 등록

 */

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private AuthUserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @Test
    @DisplayName("회원가입 성공")
    public void createUser_SUCCESS() {
        // given
        String email = "example1@mm.mm";
        FullUserCreateRequestDTO request = createFullUserCreateRequestDTO(email);

        // When
        userService.createUser(request);

        // Then
       verify(userRepository, times(1)).findByEmail(email);
    }

    @Test
    @DisplayName("회원가입 실패 - Duplicate Email")
    public void createUser_Duplicate_Email() {
        // given: authUserRepository의 findByEmail 메서드를 Stubbing하여 이미 중복된 이메일이 존재하는 것으로 설정
        Long id = 1L;
        FullUserCreateRequestDTO request = createFullUserCreateRequestDTO(createEmail(id));
        AuthUserEntity authUserEntity = request.toAuthUserEntity(bCryptPasswordEncoder);

        // when: authUserRepository의 findByEmail 메서드를 Stubbing하여 이미 중복된 이메일이 존재하는 것으로 설정
        when(userRepository.findByEmail(createEmail(id))).thenReturn(Optional.of(authUserEntity));

        // Then
        assertThrows(DuplicateEmailException.class, () -> userService.createUser(request));
        verify(userRepository, times(0)).save(authUserEntity);
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
        assertThrows(DuplicateEmailException.class, () -> userService.isEmailDuplicate(email));
        verify(userRepository, times(1)).findByEmail(email);
    }


    private static AuthUserEntity createAuthUserEntity(Long id) {
        return AuthUserEntity.builder()
                .id(id)
                .email(createEmail(id))
                .password("123123")
                .isHost(true)
                .build();
    }

    private static String createEmail(Long id) {
        return String.format("Example%d@mm.mm", id);
    }

    private static FullUserCreateRequestDTO createFullUserCreateRequestDTO(String email) {
        return FullUserCreateRequestDTO.builder()
                .email(email)
                .password("123123")
                .nickname("eventty0")
                .name("eventty0")
                .address("서울시 강남")
                .birth(LocalDate.now())
                .isHost(true)
                .image("image url 0")
                .phone("000-0000-0000")
                .build();
    }

}

