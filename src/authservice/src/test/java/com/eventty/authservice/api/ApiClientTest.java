package com.eventty.authservice.api;

import com.eventty.authservice.api.utils.MakeUrlService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.Collections;

import com.eventty.authservice.api.dto.UserCreateRequestDTO;
import com.eventty.authservice.api.exception.ApiException;
import com.eventty.authservice.common.response.ResponseDTO;
import com.eventty.authservice.common.response.SuccessResponseDTO;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@EnableConfigurationProperties
@ExtendWith(MockitoExtension.class)
public class ApiClientTest {

    @InjectMocks
    private ApiClient apiClient;

    @Mock
    private RestTemplate customRestTemplate;

    // properties 가져오기 위해서 @EnableConfigurationProperties 설정과 같이 사용
    @Mock
    private MakeUrlService makeUrlService;

    // Properties value를 Mockito를 실행할 때 못가져 오는 것도 그렇고,
    // 굳이 직접 서버의 주소를 가져다 쓰는 것은 안좋을 것 같아서 null로 고정

    /* 실제 요청을 날리는 Test를 할 때 사용
    @BeforeEach
    public void init() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
    }*/


    @Test
    @DisplayName("[POST] 회원 가입 요청 API 성공")
    public void createUserApi_SUCCESS() {

        // Given
        ResponseDTO responseDTO = ResponseDTO.of(SuccessResponseDTO.of(null));
        ResponseEntity<ResponseDTO> responseEntity = new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
        UserCreateRequestDTO userCreateRequestDTO = createUserCreateRequestDTO();

        // When
        when(customRestTemplate.exchange(
                        null, HttpMethod.POST, createHttpPostEntity(userCreateRequestDTO), ResponseDTO.class)
        ).thenReturn(responseEntity);

        // Then
        assertEquals(apiClient.createUserApi(userCreateRequestDTO), responseEntity);
    }

    @Test
    @DisplayName("[POST] 회원 가입 요청 실패 응답")
    public void createUserApi_FAIL() {

        // Given
        UserCreateRequestDTO userCreateRequestDTO = createUserCreateRequestDTO();

        // When
        doThrow(ApiException.class).when(
                customRestTemplate).exchange(null, HttpMethod.POST, createHttpPostEntity(userCreateRequestDTO), ResponseDTO.class);


        // Then
        assertThrows(ApiException.class, () -> customRestTemplate.exchange(null, HttpMethod.POST, createHttpPostEntity(userCreateRequestDTO), ResponseDTO.class));
    }

    private UserCreateRequestDTO createUserCreateRequestDTO() {
        return UserCreateRequestDTO.builder()
                .authId(1L)
                .name("eventty0")
                .address("강원도 인제군")
                .birth(LocalDate.now())
                .image("NONE")
                .phone("000-0000-0000")
                .build();
    }

    private <T> HttpEntity<T> createHttpPostEntity(T dto) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        //headers.add("X-CSRF-TOKEN", "value");

        return new HttpEntity<>(dto, headers);
    }


}
