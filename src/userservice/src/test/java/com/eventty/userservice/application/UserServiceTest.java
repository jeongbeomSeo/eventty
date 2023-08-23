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
public class UserServiceTest {
    @Autowired
    private UserService userService;

    @Test
    @DisplayName("[Success] 회원가입")
    @Transactional
    public void userCreateSuccessTest(){
        // Assignment
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

        // Act
        UserCreateResponseDTO response =  userService.userCreate(request);

        // Assert
        assertTrue(response.getId() instanceof Long);
    }

    @Test
    @DisplayName("[Success] 내 정보 조회")
    @Transactional
    public void userFindByIdTest(){
        // Assignment
        Long userId = 2L;

        // Act
        UserFindByIdResponseDTO response =  userService.userFindById(userId);

        // Assert
        assertNotNull(response);
        assertEquals(userId, response.getId());
    }
}
