package com.eventty.userservice.controller;

import com.eventty.userservice.dto.response.SuccessResponse;
import com.eventty.userservice.dto.UserCreateRequestDTO;
import com.eventty.userservice.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/api/users")
@Tag(name = "User", description = "User Server - About Users")
public class UserController {

    UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @Tag(name = "User", description = "User Sever - About User")
    public ResponseEntity<?> register(@RequestBody UserCreateRequestDTO userCreateRequestDTO){
        userService.save(userCreateRequestDTO);

        int status = HttpStatus.CREATED.value();

        return ResponseEntity
                .status(status)
                .body(SuccessResponse.of(status, "회원가입에 성공했습니다.", new HashMap<>()));
    }
}
