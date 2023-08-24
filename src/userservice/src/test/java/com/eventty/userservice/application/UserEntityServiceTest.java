package com.eventty.userservice.application;

import com.eventty.userservice.application.dto.UserCreateRequestDTO;
import com.eventty.userservice.application.dto.UserCreateResponseDTO;
import com.eventty.userservice.application.dto.UserFindByIdResponseDTO;
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
    public void userCreateSuccessTest(){
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
        UserCreateResponseDTO response =  userService.userCreate(request);

        // Then
        assertTrue(response.getId() instanceof Long);
    }

    @Test
    @DisplayName("[Success] 내 정보 조회")
    @Transactional
    public void userFindByIdTest(){
        // Given
        Long userId = 1L;

        // When
        UserFindByIdResponseDTO response =  userService.userFindById(userId);

        // Then
        assertNotNull(response);
        assertEquals(userId, response.getId());
    }
}
