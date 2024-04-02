package com.eventty.authservice.application.service.subservices;

import com.eventty.authservice.applicaiton.service.subservices.UserDetailServiceImpl;
import com.eventty.authservice.domain.Enum.UserRole;
import com.eventty.authservice.domain.entity.AuthUserEntity;
import com.eventty.authservice.domain.exception.DuplicateEmailException;
import com.eventty.authservice.domain.repository.AuthUserRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class UserDetailServiceImplTest {

    @InjectMocks
    UserDetailServiceImpl userDetailService;

    @Mock
    AuthUserRepository userRepository;

    @Mock
    EntityManager em;

    @DisplayName("[성공] 유저 삭제")
    @Test
    void delete() {
        // Given
        AuthUserEntity authUserEntity = AuthUserEntity.builder().id(1L).email("email").isDelete(false).build();

        // When
        Long id = userDetailService.delete(authUserEntity);

        // Then
        assertTrue(authUserEntity.isDelete());
        assertEquals(1L, id);
    }

    @DisplayName("[성공] 유저 생성")
    @Test
    void create_SUCCESS() {
        // Given
        String email = "email";
        Long userId = 1L;
        UserRole userRole = UserRole.USER;

        AuthUserEntity newAuthUserEntity = AuthUserEntity.builder().id(userId).email(email).isDelete(false).build();
        Optional<AuthUserEntity> optAuthEntity = Optional.empty();
        doReturn(optAuthEntity).when(userRepository).findByEmail(email);

        // When
        AuthUserEntity authUserEntity = userDetailService.create(newAuthUserEntity, userRole);

        // Then
        assertEquals(UserRole.USER.getRole(), authUserEntity.getAuthorities().get(0).getName());
        assertEquals(email, authUserEntity.getEmail());
        verify(em, times(1)).persist(authUserEntity);
        verify(em, times(1)).persist(authUserEntity.getAuthorities().get(0));
    }

    @DisplayName("[실패] 중복된 유저 발견")
    @Test
    void create_FAIL_DUPLICATE_EMAIL() {
        // Given
        String email = "email";
        Long userId = 1L;
        UserRole userRole = UserRole.USER;

        AuthUserEntity authUserEntity = AuthUserEntity.builder().id(userId).email(email).isDelete(false).build();
        Optional<AuthUserEntity> optAuthEntity = Optional.of(authUserEntity);
        doReturn(optAuthEntity).when(userRepository).findByEmail(email);

        // When && Then
        assertThrows(DuplicateEmailException.class, () -> userDetailService.create(authUserEntity, userRole));
        verify(em, times(0)).persist(authUserEntity);
    }
}
