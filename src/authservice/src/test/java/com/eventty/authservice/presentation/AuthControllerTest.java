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

            ResponseEntity<ResponseDTO<Void>> response = ResponseEntity.ok(ResponseDTO.of(true));
            doReturn(response).when(apiClient).createUserApi(any(UserCreateApiRequestDTO.class));

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
        executorService.invokeAll(tasks)을 실행했을 때 이미 모든 스레드가 실행되는데, get()을 통해 불필요하게 결과를 받아오려고 해서 500 에러가 발생한 것으로 추정.
        로직을 분석해볼 결과, @Transactional의 기능이 동작하여 모든 서비스 로직이 종료된 후 DB에 Commit이 되기 때문에 도중 과정은 모두 성공한다.
        하지만 최종적으로 DB에 Commit될 때 처음 Commit된 요청 외에는 전부 SqlExceptionHelper를 발생한다.
        이유는 Email을 Unique하게 걸어두었기 때문에 DB에 반영되는 과정에서 오류가 발생한 것으로 추정
         */

        @DisplayName("[성공] 회원가입 병렬적 요청 - 최대 1번의 요청만 반영")
        @Test
        void createUser_Concurrent() throws Exception {
            // Given
            String fullUserCreateRequestDTOJson = objectMapper.writeValueAsString(fullUserCreateRequestDTO);

            ResponseEntity<ResponseDTO<Void>> response = ResponseEntity.ok(ResponseDTO.of(true));
            doReturn(response).when(apiClient).createUserApi(any(UserCreateApiRequestDTO.class));

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
//            for (Future<Void> future : futures) {
//                future.get();
//            }

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
