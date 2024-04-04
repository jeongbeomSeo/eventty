package com.eventty.authservice.presentation;

import com.eventty.authservice.api.ApiClient;
import com.eventty.authservice.api.dto.request.UserCreateApiRequestDTO;
import com.eventty.authservice.global.response.ResponseDTO;
import com.eventty.authservice.presentation.dto.request.FullUserCreateRequestDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class AuthControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    ApiClient apiClient;
    @Autowired
    ObjectMapper objectMapper;

    @Nested
    class Signup {
        // Given
        final String email = "email@mm.mm";
        final String password = "RawPassword";
        final String name = "name";
        final String phone = "000-0000-0000";
        FullUserCreateRequestDTO fullUserCreateRequestDTO = createFullUserCreateRequestDTO(email, password, name, phone);

        @DisplayName("[성공] 회원가입 성공")
        @Test
        void createUser_SUCCESS() throws Exception {
            // Given
            String fullUserCreateRequestDTOJson = objectMapper.writeValueAsString(fullUserCreateRequestDTO);

            ResponseEntity<ResponseDTO<Void>> response = ResponseEntity.ok(ResponseDTO.of(true));
            doReturn(response).when(apiClient).createUserApi(any(UserCreateApiRequestDTO.class));

            mockMvc.perform(post("/me/user")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(fullUserCreateRequestDTOJson))
                    .andExpect(status().isCreated());
        }
        @DisplayName("[성공] 회원가입 순차적 요청")
        @Test
        void createUser_SUCCESS_Sequential() throws Exception {
            // Given
            String fullUserCreateRequestDTOJson = objectMapper.writeValueAsString(fullUserCreateRequestDTO);

            // 5번의 순차적 요청
            // When && Then
            for (int i = 0; i < 5; i++) {
                ResultActions resultActions = mockMvc.perform(post("/me/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(fullUserCreateRequestDTOJson));

                if (i == 0) {
                    resultActions.andExpect(status().isCreated());
                } else {
                    resultActions.andExpect(status().is(409));
                }
            }
        }

        /*
        현재 500 에러가 뜨고, 전혀 예상하지 못한 방향으로 결과가 나왔음.
         */

        @DisplayName("[?] 회원가입 병렬적 요청")
        @Test
        void createUser_Concurrent() throws Exception {
            // Given
            String fullUserCreateRequestDTOJson = objectMapper.writeValueAsString(fullUserCreateRequestDTO);

            int numberOfRequests = 10;
            ExecutorService executorService = Executors.newFixedThreadPool(numberOfRequests);
            List<Callable<Void>> tasks = new ArrayList<>();
            tasks.add(() -> {
                mockMvc.perform(post("/me/user")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(fullUserCreateRequestDTOJson))
                        .andExpect(status().isCreated());
                return null;
            });
            for (int i = 0; i < numberOfRequests - 1; i++) {
                tasks.add(() -> {
                    mockMvc.perform(post("/me/user")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(fullUserCreateRequestDTOJson))
                            .andExpect(status().is(409));
                   return null;
                });
            }

            // When
            List<Future<Void>> futures = executorService.invokeAll(tasks);

            // Then
            for (Future<Void> future : futures) {
                future.get();
            }

            executorService.shutdown();
        }
    }
    private FullUserCreateRequestDTO createFullUserCreateRequestDTO(String email, String password, String name, String phone) {
        return FullUserCreateRequestDTO.builder()
                .email(email)
                .password(password)
                .name(name)
                .phone(phone)
                .build();
    }
}
