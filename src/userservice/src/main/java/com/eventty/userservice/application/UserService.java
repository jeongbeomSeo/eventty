package com.eventty.userservice.application;

import com.eventty.userservice.application.dto.UserCreateRequestDTO;
import com.eventty.userservice.application.dto.UserCreateResponseDTO;
import com.eventty.userservice.application.dto.UserFindByIdResponseDTO;
import com.eventty.userservice.domain.exception.UserInfoNotFoundException;
import com.eventty.userservice.domain.UserJPARepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.PropertyValueException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserJPARepository userJPARepository;

    /**
     * 회원가입
     *
     * @author khg
     * @param userCreateRequestDTO
     * @return void
     */
    public UserCreateResponseDTO userCreate(UserCreateRequestDTO userCreateRequestDTO){
        return new UserCreateResponseDTO(userJPARepository.save(userCreateRequestDTO.toEntity()));
    }

    /**
     * ID, PW 제외한 회원 정보 조회
     *
     * @author khg
     * @param id
     * @return ResponseEntity<UserFindByIdResponseDTO>
     */
    public UserFindByIdResponseDTO userFindById(Long id){
        return new UserFindByIdResponseDTO(userJPARepository.findById(id).orElseThrow(() -> UserInfoNotFoundException.EXCEPTION));
    }
}
