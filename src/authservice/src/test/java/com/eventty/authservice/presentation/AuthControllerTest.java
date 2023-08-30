package com.eventty.authservice.presentation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.eventty.authservice.presentation.dto.FullUserCreateRequestDTO;
import com.eventty.authservice.applicaiton.service.UserService;
import com.eventty.authservice.common.Enum.ErrorCode;
import com.eventty.authservice.common.Enum.SuccessCode;
import com.eventty.authservice.infrastructure.config.BasicSecurityConfig;
import com.eventty.authservice.domain.exception.DuplicateEmailException;

import java.time.LocalDate;

import static org.springframework.security.test.
        web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;

/* 오류 정리
1. h2.H2ConsoleProperties' available Error : BasicSecurityConfig에서 H2Console 설정 부분 주석 처리
2. doThrow(DuplicateEmailException.class) 에서 에러 발생: 이와 같은 형태로 작성하는 것이 아닌 현재 커스텀 마이징 예외의 경우 Enum ErrorCode객체의 경우 생성자가 필요로 함.
=>  doThrow(new DuplicateEmailException())
3. csrf()를 사용하려면 의존성 주입 + imoprt static 작업 필요
 */

@ImportAutoConfiguration(BasicSecurityConfig.class)
@WebMvcTest(AuthController.class)
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    @DisplayName("[POST] 회원 가입 테스트")
    public void createUserTest() throws Exception {
        // given
        Long id = 1L;
        String email = createEmail(id);
        FullUserCreateRequestDTO fullUserCreateRequestDTO = createFullUserCreateRequestDTO(email);

        // When & Then
        mockMvc.perform(post("/api/auth")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(fullUserCreateRequestDTO))
                        .with(csrf()))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.code").value("0"))
                .andExpect(jsonPath("$.message").value(SuccessCode.USER_CREATED.getMessage()))
                .andExpect(jsonPath("$.data").doesNotExist());

    }


    @Test
    @DisplayName("[POST][ERRPR] 이메일 검증 테스트")
    public void isEmailDuplicateTest() throws Exception {
        // given
        Long id = 1L;
        String email = createEmail(id);

        // Arrange: UserService의 isEmailDuplicate 메서드가 DuplicateEmailException을 던질 것을 모킹
        doThrow(DuplicateEmailException.EXCEPTION).when(userService).isEmailDuplicate(email);

        // When & Then
        mockMvc.perform(post("/api/auth/email")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"" + email + "\"}")
                        .with(csrf()))
                .andExpect(status().isConflict()) // 실패 시 BadRequest 상태 코드 예상
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success").value(false)) // 실패 시 success 값이 false로 예상
                .andExpect(jsonPath("$.code").value(ErrorCode.DUPLICATE_EMAIL.getCode())) // 실패 시 code 값이 DUPLICATE_EMAIL로 예상
                .andExpect(jsonPath("$.message").value(ErrorCode.DUPLICATE_EMAIL.getMessage()));
        // Verify that the isEmailDuplicate method was called with the provided email
        verify(userService, times(1)).isEmailDuplicate(email);
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
                .image("image url 0")
                .phone("000-0000-0000")
                .build();
    }
}
