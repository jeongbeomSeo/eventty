package com.eventty.userservice.presentation;

import com.eventty.userservice.application.dto.request.UserCreateRequestDTO;
import com.eventty.userservice.application.UserService;
import com.eventty.userservice.domain.code.ErrorCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@MockBean(JpaMetamodelMappingContext.class)
public class UserEntityControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    UserService userService;

    @BeforeEach
    public void setMockMvc() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }


    @Test
    @DisplayName("[API][POST] 회원가입 성공")
    public void postMeSuccessTest() throws Exception {
        // Given
        Long userId = 1L;
        String name = "길동";
        String address = "서울특별시 도봉구 도봉동 1";
        LocalDate birth = LocalDate.of(1998, 06, 23);
        String phone = "01012345678";
        String image = "/url/url/url.jpeg";
        String url = "/api/users/me";

        UserCreateRequestDTO userCreateRequestDTO = UserCreateRequestDTO
                .builder()
                .userId(userId)
                .name(name)
                .address(address)
                .birth(birth)
                .phone(phone)
                .image(image)
                .build();

        final String requestBody =objectMapper.writeValueAsString(userCreateRequestDTO);

        // When
        final ResultActions response = mockMvc.perform(post(url).contentType(MediaType.APPLICATION_JSON).content(requestBody));

        // Then
        response
                .andExpect(status().isCreated())
                .andExpect(jsonPath("success").value(true))
                .andExpect(jsonPath("SuccessResponseDTO").doesNotHaveJsonPath())
                .andExpect(jsonPath("ErrorResponseDTO").doesNotHaveJsonPath());
    }

    @Test
    @DisplayName("[API][POST] 회원가입 실패 - 필수값 부재")
    public void postMeNullParameterTest() throws Exception {
        // Given -- 전역변수로
        Long userId = 1L;
        String address = "서울특별시 도봉구 도봉동 1";
        LocalDate birth = LocalDate.of(1998, 06, 23);
        String phone = "01012345678";
        String image = "/url/url/url.jpeg";
        String url = "/api/users/me";
        ErrorCode errorCode = ErrorCode.INVALID_INPUT_VALUE;

        UserCreateRequestDTO userCreateRequestDTO = UserCreateRequestDTO
                .builder()
                .userId(userId)
                .address(address)
                .birth(birth)
                .phone(phone)
                .image(image)
                .build();

        final String requestBody =objectMapper.writeValueAsString(userCreateRequestDTO);

        // When
        final ResultActions response = mockMvc.perform(post(url).contentType(MediaType.APPLICATION_JSON).content(requestBody));

        // Then
        response
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("success").value(false))
                .andExpect(jsonPath("errorResponseDTO").hasJsonPath())
                .andExpect(jsonPath("successResponseDTO").doesNotHaveJsonPath());
    }

    @Test
    @DisplayName("[API][GET] 내 정보 조회")
    public void myInfoTest() throws Exception {
        // Given
        Long userId = 1L;
        String url = "/api/users/me/" + userId;

        // When
        final ResultActions response = mockMvc.perform(get(url).contentType(MediaType.APPLICATION_JSON));

        // Then
        response
                .andExpect(status().isOk())
                .andExpect(jsonPath("success").value(true))
                .andExpect(jsonPath("errorResponseDTO").doesNotHaveJsonPath())
                .andExpect(jsonPath("successResponseDTO").hasJsonPath());
    }

    @Test
    @DisplayName("[API][PATCH] 내 정보 수정")
    public void patchMyInfoTest() throws Exception {
        // Given -- 전역변수로
        Long userId = 1L;
        String name = "아항";
        String address = "인천 남동구 장아산로 64 1, 2층";
        LocalDate birth = LocalDate.of(2000, 6, 8);

        String url = "/api/users/me/" + userId;

        UserCreateRequestDTO userCreateRequestDTO = UserCreateRequestDTO
                .builder()
                .name(name)
                .address(address)
                .birth(birth)
                .build();

        final String requestBody =objectMapper.writeValueAsString(userCreateRequestDTO);

        // When
        final ResultActions response = mockMvc.perform(patch(url).contentType(MediaType.APPLICATION_JSON).content(requestBody));

        // Then
        response.andExpect(status().isOk())
                .andExpect(jsonPath("success").value(true))
                .andExpect(jsonPath("SuccessResponseDTO").doesNotHaveJsonPath())
                .andExpect(jsonPath("ErrorResponseDTO").doesNotHaveJsonPath());
    }
}