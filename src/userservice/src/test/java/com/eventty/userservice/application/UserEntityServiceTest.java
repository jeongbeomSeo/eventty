package com.eventty.userservice.application;

import com.eventty.userservice.application.dto.request.UserCreateRequestDTO;
import com.eventty.userservice.application.dto.response.UserFindByIdResponseDTO;
import com.eventty.userservice.application.dto.request.UserUpdateRequestDTO;
import com.eventty.userservice.domain.UserEntity;
import com.eventty.userservice.domain.exception.UserInfoNotFoundException;
import jakarta.persistence.EntityManager;
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

    @Autowired
    EntityManager em;

    @Test
    @DisplayName("[Success] 회원가입")
    @Transactional
    public void createUserTest(){
        // Given
        Long userId = 1L;
        String name = "홍박사";
        String address = "서울특별시 강남구 테헤란로";
        LocalDate birth = LocalDate.of(1988, 5, 3);
        String phone = "01045628526";

        UserCreateRequestDTO request = UserCreateRequestDTO
                                                    .builder()
                                                    .userId(userId)
                                                    .name(name)
                                                    .address(address)
                                                    .birth(birth)
                                                    .phone(phone)
                                                    .build();

        // When
        UserEntity userEntity = userService.createUser(request);

        // Then
        assertEquals(userEntity.getUserId(), userId);
        assertEquals(userEntity.getName(), name);
        assertEquals(userEntity.getAddress(), address);
        assertEquals(userEntity.getBirth(), birth);
        assertEquals(userEntity.getPhone(), phone);
    }

    @Test
    @DisplayName("[Success] 내 정보 조회")
    @Transactional
    public void userFindByIdTest(){
        // Given
        Long userId = 1L;
        String name = "홍박사";
        String address = "서울특별시 강남구 테헤란로";
        LocalDate birth = LocalDate.of(1988, 5, 3);
        String image = "image.jpg";
        String phone = "01045628526";

        UserEntity user = UserEntity
                .builder()
                .userId(userId)
                .name(name)
                .address(address)
                .birth(birth)
                .image(image)
                .phone(phone)
                .build();

        em.persist(user);

        // When
        UserFindByIdResponseDTO response =  userService.findUserById(userId);

        // Then
        assertEquals(response.getUserId(), userId);
        assertEquals(response.getName(), name);
        assertEquals(response.getAddress(), address);
        assertEquals(response.getBirth(), birth);
        assertEquals(response.getPhone(), phone);
        assertEquals(response.getImage(), image);
    }

    @Test
    @DisplayName("[Fail][UserInfoNotFoundException] 내 정보 조회")
    @Transactional
    public void userInfoNotFoundExceptionTest(){
        // Given
        Long userId = 1000000000L;

        // When, Then
        assertThrows(UserInfoNotFoundException.class, () -> userService.findUserById(userId));
    }

    @Test
    @DisplayName("[Success] 내정보 수정")
    @Transactional
    public void userUpdateTest(){
        // Given
        Long userId = 1L;
        String name = "홍박사";
        LocalDate birth = LocalDate.of(1988, 5, 3);
        String image = "image.jpg";
        String phone = "01045628526";

        UserEntity user = UserEntity
                .builder()
                .userId(userId)
                .name(name)
                .birth(birth)
                .image(image)
                .phone(phone)
                .build();

        em.persist(user);

        String address = "인천광역시 남동구";                           // null -> new 값
        birth = LocalDate.of(1988, 12, 4);     // 값 -> new 값
        image = "";                                                  // 값 -> "" 값

        UserUpdateRequestDTO updateRequest = new UserUpdateRequestDTO();
        updateRequest.setAddress(address);
        updateRequest.setBirth(birth);
        updateRequest.setImage(image);

        // When
        UserEntity userEntity =  userService.updateUser(userId, updateRequest);

        // Then
        assertEquals(image, em.find(UserEntity.class, userId).getImage());
        assertEquals(address, em.find(UserEntity.class, userId).getAddress());
        assertEquals(birth, em.find(UserEntity.class, userId).getBirth());
    }
}
