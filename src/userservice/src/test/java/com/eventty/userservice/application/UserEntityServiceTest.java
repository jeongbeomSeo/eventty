package com.eventty.userservice.application;

import com.eventty.userservice.application.dto.request.UserCreateRequestDTO;
import com.eventty.userservice.application.dto.response.UserCreateAndUpdateResponseDTO;
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
        Long authId = 1L;
        String name = "홍박사";
        String address = "서울특별시 강남구 테헤란로";
        LocalDate birth = LocalDate.of(1988, 5, 3);
        String image = "image.jpg";
        String phone = "01045628526";

        UserCreateRequestDTO request = UserCreateRequestDTO
                                                    .builder()
                                                    .authId(authId)
                                                    .name(name)
                                                    .address(address)
                                                    .birth(birth)
                                                    .image(image)
                                                    .phone(phone)
                                                    .build();

        // When
        UserCreateAndUpdateResponseDTO response =  userService.createUser(request);

        // Then
        assertTrue(response.getId() instanceof Long);
    }

    @Test
    @DisplayName("[Success] 내 정보 조회")
    @Transactional
    public void userFindByIdTest(){
        // Given
        Long authId = 1L;
        String name = "홍박사";
        String address = "서울특별시 강남구 테헤란로";
        LocalDate birth = LocalDate.of(1988, 5, 3);
        String image = "image.jpg";
        String phone = "01045628526";

        UserEntity user = UserEntity
                .builder()
                .authId(authId)
                .name(name)
                .address(address)
                .birth(birth)
                .image(image)
                .phone(phone)
                .build();

        em.persist(user);
        Long userId = user.getId();

        // When
        UserFindByIdResponseDTO response =  userService.findUserById(userId);

        // Then
        assertNotNull(response);
        assertEquals(userId, response.getId());
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
        Long authId = 1L;
        String name = "홍박사";
        LocalDate birth = LocalDate.of(1988, 5, 3);
        String image = "image.jpg";
        String phone = "01045628526";

        UserEntity user = UserEntity
                .builder()
                .authId(authId)
                .name(name)
                .birth(birth)
                .image(image)
                .phone(phone)
                .build();

        em.persist(user);

        Long userId = user.getId();
        String address = "인천광역시 남동구";                           // null -> new 값
        birth = LocalDate.of(1988, 12, 4);     // 값 -> new 값
        image = "";                                                  // 값 -> "" 값

        UserUpdateRequestDTO updateRequest = new UserUpdateRequestDTO();
        updateRequest.setAddress(address);
        updateRequest.setBirth(birth);
        updateRequest.setImage(image);

        // When
        UserCreateAndUpdateResponseDTO updateResponse =  userService.updateUser(userId, updateRequest);

        // Then
        assertEquals(userId, updateResponse.getId());
        assertEquals(name, em.find(UserEntity.class, userId).getName());
        assertEquals(address, em.find(UserEntity.class, userId).getAddress());
        assertEquals(birth, em.find(UserEntity.class, userId).getBirth());
    }
}
