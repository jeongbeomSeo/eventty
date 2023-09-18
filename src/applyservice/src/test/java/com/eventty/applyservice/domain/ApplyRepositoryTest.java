package com.eventty.applyservice.domain;

import com.eventty.applyservice.application.dto.CheckAlreadyApplyUserDTO;
import com.eventty.applyservice.application.dto.CreateApplyDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // 테스트용 인메모리 DB
@MybatisTest
public class ApplyRepositoryTest {
    @Autowired
    private ApplyReposiroty applyReposiroty;

    @Test
    @DisplayName("행사 신청")
    public void insertApplyTest(){
        Long userId = 1L;
        Long eventId = 1L;
        Long ticktetId = 1L;

        CreateApplyDTO createApplyDTO = new CreateApplyDTO(userId, eventId, ticktetId);
        Long response = applyReposiroty.insertApply(createApplyDTO);

        assertNotNull(response);
    }

    @Test
    @DisplayName("이미 신청한 유저 체크")
    public void checkAlreadyApplyUserTest(){
        Long userId = 2L;
        Long eventId = 2L;

        CheckAlreadyApplyUserDTO checkAlreadyApplyUserDTO = new CheckAlreadyApplyUserDTO(userId, eventId);
        Long response = applyReposiroty.checkAlreadyApplyUser(checkAlreadyApplyUserDTO);

        assertNotNull(response);
    }

    @Test
    @DisplayName("현재 신청 인원수")
    public void checkApplyNumTest(){
        Long eventId = 2L;

        Long response = applyReposiroty.checkApplyNum(eventId);

        assertNotNull(response);
    }
}
