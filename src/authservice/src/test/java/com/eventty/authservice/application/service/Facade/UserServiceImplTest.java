/*
package com.eventty.authservice.application.service.Facade;

import com.eventty.authservice.api.ApiClient;
import com.eventty.authservice.api.exception.ApiException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.eventty.authservice.api.dto.UserCreateRequestDTO;
import com.eventty.authservice.applicaiton.service.Facade.UserServiceImpl;
import com.eventty.authservice.applicaiton.service.subservices.UserDetailServiceImpl;
import com.eventty.authservice.common.response.ResponseDTO;
import com.eventty.authservice.common.response.SuccessResponseDTO;
import com.eventty.authservice.domain.Enum.UserRole;
import com.eventty.authservice.domain.entity.AuthUserEntity;
import com.eventty.authservice.domain.entity.AuthorityEntity;
import com.eventty.authservice.domain.exception.DuplicateEmailException;
import com.eventty.authservice.presentation.dto.FullUserCreateRequestDTO;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl authService;

    @Mock
    private UserDetailServiceImpl userService;

    @Mock
    private ApiClient apiClient;

    @Test
    @DisplayName("[POST] 회원가입 성공")
    public void createUser_SUCESS_USER() {

        // Given

        // Local
        Long id = 1L;
        String email = createEmail(id);
        FullUserCreateRequestDTO fullUserCreateRequestDTO = createFullUserCreateRequestDTO(email);
        UserRole userRole = UserRole.USER;
        AuthUserEntity authUserEntity = createAuthUserEntity(id, email, userRole);

        // API
        // API의 예상된 응답값을 설정
        SuccessResponseDTO successResponse = SuccessResponseDTO.of(null);
        ResponseDTO mockedResponse = ResponseDTO.of(successResponse);
        ResponseEntity<ResponseDTO> responseEntity = new ResponseEntity<>(mockedResponse, HttpStatus.CREATED);

        when(userService.create(fullUserCreateRequestDTO, userRole)).thenReturn(authUserEntity);
        doReturn(responseEntity).when(apiClient).createUserApi(any(UserCreateRequestDTO.class));

        // When
        authService.createUser(fullUserCreateRequestDTO, userRole);
        // Then
        verify(userService, times(1)).create(fullUserCreateRequestDTO, userRole);
        verify(apiClient, times(1)).createUserApi(any(UserCreateRequestDTO.class));
    }

    @Test
    @DisplayName("[POST][ERROR] 회원 가입 실패 - 이메일 중복")
    public void createUser_INVALID_INPUT_VALUE() {

        // Given
        Long id = 1L;
        String email = createEmail(id);
        FullUserCreateRequestDTO fullUserCreateRequestDTO = createFullUserCreateRequestDTO(email);
        UserRole userRole = UserRole.USER;

        // when
        doThrow(DuplicateEmailException.class).when(userService).create(fullUserCreateRequestDTO, userRole);

        // Then
        assertThrows(DuplicateEmailException.class, () -> authService.createUser(fullUserCreateRequestDTO, userRole));
        // verify(apiClient, times(0)).createUserApi(any(UserCreateRequestDTO.class));
    }

    @Test
    @DisplayName("[POST][ERROR] 회원 가입 실패 - API 요청 실패")
    public void createUser_API_EXCEPTION() {

        // Given
        Long id = 1L;
        String email = createEmail(id);
        FullUserCreateRequestDTO fullUserCreateRequestDTO = createFullUserCreateRequestDTO(email);
        UserRole role = UserRole.USER;
        AuthUserEntity authUserEntity = createAuthUserEntity(id, email, role);

        // when
        when(userService.create(fullUserCreateRequestDTO, role)).thenReturn(authUserEntity);
        doThrow(ApiException.class).when(apiClient).createUserApi(any(UserCreateRequestDTO.class));

        // then
        assertThrows(ApiException.class, () -> authService.createUser(fullUserCreateRequestDTO, role));
    }

    private static AuthUserEntity createAuthUserEntity(Long id, String email, UserRole role) {
        AuthUserEntity authUserEntity = AuthUserEntity.builder()
                .id(id)
                .email(email)
                .password("123123")
                .build();

        AuthorityEntity authorityEntity = AuthorityEntity.builder()
                .id(id)
                .name(role.getRole())
                .authUserEntity(authUserEntity)
                .build();

        List<AuthorityEntity> Authorities = new ArrayList<>();
        Authorities.add(authorityEntity);

        authUserEntity.setAuthorities(Authorities);

        return authUserEntity;
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
*/
