package com.eventty.userservice.controller;

import com.eventty.userservice.dto.UserCreateRequestDTO;
import com.eventty.userservice.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
public class UserControllerTest {
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
    @DisplayName("[API][POST] 회원가입")
    public void registerTest() throws Exception {
        //given -- 전역변수로
        String name = "길동";
        String address = "서울특별시 도봉구 도봉동 1";
        LocalDate birth = LocalDate.of(1998, 06, 23);
        boolean isHost = true;
        String phone = "01012345678";
        String image = "/url/url/url.jpeg";
        String url = "http://localhost:8000/api/users/register";

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

        // when
        final ResultActions response = mockMvc.perform(post("/api/users/register").contentType(MediaType.APPLICATION_JSON).content(requestBody));

        // then
        response
                .andExpect(status().isCreated())
                .andExpect(jsonPath("message").value("회원가입에 성공했습니다."));
    }
}