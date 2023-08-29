package com.eventty.userservice.application;

import com.eventty.userservice.application.dto.request.UserCreateRequestDTO;
import com.eventty.userservice.application.dto.response.UserCreateAndUpdateResponseDTO;
import com.eventty.userservice.application.dto.response.UserFindByIdResponseDTO;
import com.eventty.userservice.application.dto.request.UserUpdateRequestDTO;
import com.eventty.userservice.domain.exception.UserInfoNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserEntityServiceTest {
    @Autowired
    private UserService userService;

    @Test
    @DisplayName("[Success] 회원가입")
    @Transactional
    public void userCreateTest(){
        // Given
        String name = "홍박사";
        String address = "서울특별시 강남구 테헤란로";
        LocalDate birth = LocalDate.of(1988, 5, 3);
        Boolean isHost = true;
        String image = "image.jpg";
        String phone = "01045628526";

        UserCreateRequestDTO request = UserCreateRequestDTO
                                                    .builder()
                                                    .name(name)
                                                    .address(address)
                                                    .birth(birth)
                                                    .isHost(isHost)
                                                    .image(image)
                                                    .phone(phone)
                                                    .build();

        // When
        UserCreateAndUpdateResponseDTO response =  userService.userCreate(request);

        // Then
        assertTrue(response.getId() instanceof Long);
    }

    @Test
    @DisplayName("[Success] 내 정보 조회")
    @Transactional
    public void userFindByIdTest(){
        // Given
        String name = "홍박사";
        String address = "서울특별시 강남구 테헤란로";
        LocalDate birth = LocalDate.of(1988, 5, 3);
        Boolean isHost = true;
        String image = "image.jpg";
        String phone = "01045628526";

        UserCreateRequestDTO createRequest = UserCreateRequestDTO
                .builder()
                .name(name)
                .address(address)
                .birth(birth)
                .isHost(isHost)
                .image(image)
                .phone(phone)
                .build();

        // When
        UserCreateAndUpdateResponseDTO createResponse =  userService.userCreate(createRequest);
        Long userId = createResponse.getId();

        // When
        UserFindByIdResponseDTO response =  userService.userFindById(userId);

        // Then
        assertNotNull(response);
        assertEquals(userId, response.getId());
    }

    @Test
    @DisplayName("[Fail][UserInfoNotFoundException] 내 정보 조회")
    @Transactional
    public void userInfoNotFoundExceptionTest(){
        // Given
        Long userId = 10000L;

        // When, Then
        assertThrows(UserInfoNotFoundException.class, () -> userService.userFindById(userId));
    }

    @Test
    @DisplayName("[Success] 내정보 수정")
    @Transactional
    public void userUpdateTest(){
        // Given
        String name = "홍박사";
        LocalDate birth = LocalDate.of(1988, 5, 3);
        Boolean isHost = true;
        String image = "image.jpg";
        String phone = "01045628526";

        UserCreateRequestDTO createRequest = UserCreateRequestDTO
                .builder()
                .name(name)
                .birth(birth)
                .isHost(isHost)
                .image(image)
                .phone(phone)
                .build();

        UserCreateAndUpdateResponseDTO createResponse =  userService.userCreate(createRequest);

        Long userId = createResponse.getId();
        name = "남동구";
        String address = "인천광역시 남동구";
        birth = LocalDate.of(1988, 12, 4);

        UserUpdateRequestDTO updateRequest = new UserUpdateRequestDTO();
        updateRequest.setName(name);
        updateRequest.setAddress(address);
        updateRequest.setBirth(birth);

        // When
        UserCreateAndUpdateResponseDTO updateResponse =  userService.userUpdate(userId, updateRequest);

        // Then
        assertEquals(userId, updateResponse.getId());
        assertEquals(name, userService.userFindById(userId).getName());
        assertEquals(address, userService.userFindById(userId).getAddress());
        assertEquals(birth, userService.userFindById(userId).getBirth());
    }
}
