package com.eventty.authservice.applicaiton.service;

import com.eventty.authservice.applicaiton.dto.FullUserCreateRequestDTO;
import com.eventty.authservice.applicaiton.dto.IsUserDuplicateDTO;
import com.eventty.authservice.common.Enum.SuccessCode;
import com.eventty.authservice.common.response.ResponseDTO;
import com.eventty.authservice.common.response.SuccessResponseDTO;
import com.eventty.authservice.domain.entity.AuthUserEntity;
import com.eventty.authservice.domain.exception.DuplicateEmailException;
import com.eventty.authservice.domain.repository.AuthUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.beans.Encoder;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final AuthUserRepository userRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    // 예외 처리로 할 경우 return void // Boolean 검증으로 처리할 경우 return boolean
    public void isEmailDuplicate(String email) {
        Optional<AuthUserEntity> existingUser = userRepository.findByEmail(email);

        if (existingUser.isPresent()) {
            throw new DuplicateEmailException();
        }
    }

    public void createUser(FullUserCreateRequestDTO fullUserCreateRequestDTO) {
        // 이메일 중복 검사
        String email = fullUserCreateRequestDTO.getEmail();
        isEmailDuplicate(email);

        AuthUserEntity newUser = fullUserCreateRequestDTO.toAuthUserEntity(bCryptPasswordEncoder);
        userRepository.save(newUser);

        // API 요청 로직 + compensating Transaction

    }
}
