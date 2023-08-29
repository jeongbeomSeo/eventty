package com.eventty.userservice.presentation;

import com.eventty.userservice.application.dto.request.UserCreateRequestDTO;
import com.eventty.userservice.domain.code.ErrorCode;
import com.eventty.userservice.domain.code.SuccessCode;
import com.eventty.userservice.application.UserService;
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
    public void registerSuccessTest() throws Exception {
        // Given
        String name = "길동";
        String address = "서울특별시 도봉구 도봉동 1";
        LocalDate birth = LocalDate.of(1998, 06, 23);
        boolean isHost = true;
        String phone = "01012345678";
        String image = "/url/url/url.jpeg";
        String url = "http://localhost:8000/api/users/register";
        SuccessCode successCode = SuccessCode.USER_INFO_INSERT;

        UserCreateRequestDTO userCreateRequestDTO = UserCreateRequestDTO
                .builder()
                .name(name)
                .address(address)
                .birth(birth)
                .isHost(isHost)
                .phone(phone)
                .image(image)
                .build();

        final String requestBody =objectMapper.writeValueAsString(userCreateRequestDTO);

        // When
        final ResultActions response = mockMvc.perform(post("/api/users/register").contentType(MediaType.APPLICATION_JSON).content(requestBody));

        // Then
        response
                .andExpect(status().isOk())
                .andExpect(jsonPath("message").value(successCode.getMessage()));
    }

    @Test
    @DisplayName("[API][POST] 회원가입 실패 - 필수값 부재")
    public void registerNullParameterTest() throws Exception {
        // Given -- 전역변수로
        String address = "서울특별시 도봉구 도봉동 1";
        LocalDate birth = LocalDate.of(1998, 06, 23);
        boolean isHost = true;
        String phone = "01012345678";
        String image = "/url/url/url.jpeg";
        String url = "http://localhost:8000/api/users/register";
        ErrorCode errorCode = ErrorCode.INVALID_INPUT_VALUE;

        UserCreateRequestDTO userCreateRequestDTO = UserCreateRequestDTO
                .builder()
                .address(address)
                .birth(birth)
                .isHost(isHost)
                .phone(phone)
                .image(image)
                .build();

        final String requestBody =objectMapper.writeValueAsString(userCreateRequestDTO);

        // When
        final ResultActions response = mockMvc.perform(post("/api/users/register").contentType(MediaType.APPLICATION_JSON).content(requestBody));

        // Then
        response
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("code").value(errorCode.getCode()))
                .andExpect(jsonPath("message").value(errorCode.getMessage()));
    }

    @Test
    @DisplayName("[API][GET] 내 정보 조회")
    public void myInfoTest() throws Exception {
        // Given
        Long userId = 1L;
        SuccessCode successCode = SuccessCode.USER_INFO_FIND_BY_ID;
        String url = "http://localhost:8000/api/users/myInfo/" + userId;

        // When
        final ResultActions response = mockMvc.perform(get(url).contentType(MediaType.APPLICATION_JSON));

        // Then
        response
                .andExpect(status().isOk())
                .andExpect(jsonPath("message").value(successCode.getMessage()));
    }

    @Test
    @DisplayName("[API][PATCH] 내 정보 수정")
    public void patchMyInfoTest() throws Exception {
        // Given -- 전역변수로
        Long userId = 1L;
        String name = "아항";
        String address = "인천 남동구 장아산로 64 1, 2층";
        LocalDate birth = LocalDate.of(2000, 6, 8);
        String phone = "01012345678";
        String image = "";
        String url = "http://localhost:8000/api/users/myInfo/" + userId;
        SuccessCode successCode = SuccessCode.USER_INFO_UPDATE;

        UserCreateRequestDTO userCreateRequestDTO = UserCreateRequestDTO
                .builder()
                .name(name)
                .address(address)
                .birth(birth)
                .phone(phone)
                .image(image)
                .build();

        final String requestBody =objectMapper.writeValueAsString(userCreateRequestDTO);

        // When
        final ResultActions response = mockMvc.perform(patch(url).contentType(MediaType.APPLICATION_JSON).content(requestBody));

        // Then
        response
                .andExpect(status().isOk())
                .andExpect(jsonPath("message").value(successCode.getMessage()));
    }
}