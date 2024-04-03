package com.eventty.authservice.presentation;

import com.eventty.authservice.applicaiton.dto.LoginSuccessDTO;
import com.eventty.authservice.applicaiton.dto.SessionTokensDTO;
import com.eventty.authservice.applicaiton.service.Facade.UserServiceImpl;
import com.eventty.authservice.applicaiton.service.utils.token.TokenEnum;
import com.eventty.authservice.domain.Enum.UserRole;
import com.eventty.authservice.global.response.ResponseDTO;
import com.eventty.authservice.global.response.SuccessResponseDTO;
import com.eventty.authservice.infrastructure.config.BasicSecurityConfig;
import com.eventty.authservice.infrastructure.utils.CookieUtils;
import com.eventty.authservice.presentation.dto.request.FullUserCreateRequestDTO;
import com.eventty.authservice.presentation.dto.request.UserLoginRequestDTO;
import com.eventty.authservice.presentation.dto.response.LoginResponseDTO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.nimbusds.jose.shaded.gson.Gson;
import com.nimbusds.jose.shaded.gson.reflect.TypeToken;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import static com.eventty.authservice.application.service.utils.token.TokenTestUtil.*;

@ExtendWith(MockitoExtension.class)
@Import(BasicSecurityConfig.class)
@WebMvcTest
public class AuthControllerTest {
    private final String HEADER_CSRF = "X-Csrf-Token";
    @Autowired
    MockMvc mockMvc;
    @MockBean
    UserServiceImpl userService;
    @SpyBean
    CookieUtils cookieUtils;
    @Autowired
    ObjectMapper objectMapper;
    /*
    @WebMvcTest의 경우 컨트롤러 수준에서 동작하는 모듈들이 정상적으로 동작한다.
    예를 들면, 필터, 인터셉터 리졸버 등과 같은 모듈들이 정상적으로 동작한다.
    그렇기 때문에 Security를 사용한다면 테스트용 Security 설정을 별도로 하지 않는다면 default 설정을 가져가게 된다.
    이로 인해서, 발생할 수 있는 것은 1. csrf 검사 2. URL에 대한 사용자 권한 검사 이와 같은 것들이 동작한다.
    또한, 파라미터 바인딩 및 데이터 유효성 검증 어노테이션이 정상적으로 동작하기 때문에 신경써야 한다.
     */
    @DisplayName("[성공] 회원가입 성공")
    @Test
    void createUser_SUCCESS() throws Exception {
        // Given
        FullUserCreateRequestDTO fullUserCreateRequestDTO = createFullUserCreateRequestDTO();
        UserRole userRole = UserRole.USER;

        // JSON으로 변환
        String userCreateRequestDTOJson = objectMapper.writeValueAsString(fullUserCreateRequestDTO);

        Long userId = 1L;
        // userRole을 그대로 쓸 경우 UnfinishedStubbingException 발생
        // UserRole의 경우, 커스텀 리졸버로 PathVariable에서 꺼내서 바인딩 되는데, 해당 과정에서 생성되는 Enum 객체가 해당 테스트 코드에서 생성한 userRole 객체와 다르기 때문일 것이라고 추측
        doReturn(userId).when(userService).createUser(any(FullUserCreateRequestDTO.class), any(UserRole.class));

        // When
        mockMvc.perform(post("/me/{userRole}", userRole.name())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(userCreateRequestDTOJson))
                .andExpect(status().isCreated());
    }

    @DisplayName("[성공] 로그인 성공")
    @Test
    void login() throws Exception {
        // Given
        final Long userId = 1L;
        final String email = "Email@mm.mm";
        final String password = "123123";
        UserLoginRequestDTO userLoginRequestDTO = new UserLoginRequestDTO(email, password);

        SessionTokensDTO sessionTokensDTO = createSessionTokensDTO(email, userId);
        LoginResponseDTO loginResponseDTO = new LoginResponseDTO(userId, email, "role", "", "");
        String csrfToken = "csrf_token";
        LoginSuccessDTO loginSuccessDTO = new LoginSuccessDTO(sessionTokensDTO, loginResponseDTO, csrfToken);
        when(userService.login(any(UserLoginRequestDTO.class))).thenReturn(loginSuccessDTO);

        // When
        ResultActions resultActions = mockMvc.perform(post("/login")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(new Gson().toJson(userLoginRequestDTO)));

        // Then
        MvcResult mvcResult = resultActions.andExpect(status().isOk()).andReturn();
        List<String> setCookieHeaders = mvcResult.getResponse().getHeaders(HttpHeaders.SET_COOKIE);

        // 특정 쿠키 확인하기
        boolean jwtCookieFound = setCookieHeaders.stream()
                .anyMatch(header -> header.startsWith(TokenEnum.ACCESS_TOKEN.getName()));
        boolean refreshTokenCookieFound = setCookieHeaders.stream()
                .anyMatch(header -> header.startsWith(TokenEnum.REFRESH_TOKEN.getName()));

        assertTrue(jwtCookieFound);
        assertTrue(refreshTokenCookieFound);

        // Response Body 확인하기 -> 역직렬화가 재대로 이루어지지 않고 있음.
        TypeReference<ResponseDTO<SuccessResponseDTO<LoginResponseDTO>>> responseType = new TypeReference<>() {};
        ResponseDTO<SuccessResponseDTO<LoginResponseDTO>> response = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), responseType);
        System.out.println(response.toString());
        // 결과: ResponseDTO(isSuccess=true, errorResponseDTO=null, successResponseDTO=SuccessResponseDTO(data=SuccessResponseDTO(data=null)))
        assertTrue(response.getIsSuccess());
        // assertEquals(userId, response.getSuccessResponseDTO().getData().getData().getUserId());
        // assertEquals(email, response.getSuccessResponseDTO().getData().getData().getEmail());
    }

    private FullUserCreateRequestDTO createFullUserCreateRequestDTO() {
        final String email = "신규_회원_이메일@mm.mm";
        final String password = "암호화 되지 않은 비밀번호";
        final String name = "회원 이름";
        final String phone = "000-0000-0000";
        final LocalDate birth = LocalDate.now();
        final String address = "회원 주소";
        return new FullUserCreateRequestDTO(email, password, name, phone, birth, address);
    }
}
