//package com.eventty.applyservice.domain;
//
//import com.eventty.applyservice.application.dto.CheckAlreadyApplyUserDTO;
//import com.eventty.applyservice.application.dto.CreateApplyDTO;
//import com.eventty.applyservice.application.dto.FindByUserIdDTO;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@ExtendWith(SpringExtension.class)
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // 테스트용 인메모리 DB
//@MybatisTest
//public class ApplyRepositoryTest {
//    @Autowired
//    private ApplyReposiroty applyReposiroty;
//
//    @Test
//    @DisplayName("현재 신청 인원수")
//    public void getApplyNumTest(){
//        Long eventId = 2L;
//
//        Long response = applyReposiroty.getApplyNum(eventId);
//
//        assertNotNull(response);
//    }
//
//
//    @Test
//    @DisplayName("행사 신청")
//    public void insertApplyTest(){
//        Long userId = 100L;
//        Long eventId = 100L;
//        Long ticktetId = 100L;
//        Long applicantNum = 3L;
//        String phone = "010-1234-1234";
//
//        CreateApplyDTO createApplyDTO = CreateApplyDTO
//                .builder()
//                .userId(userId)
//                .eventId(eventId)
//                .ticketId(ticktetId)
//                .applicantNum(applicantNum)
//                .phone(phone)
//                .build();
//        Long response = applyReposiroty.insertApply(createApplyDTO);
//
//        assertNotNull(response);
//    }
//
//    @Test
//    @DisplayName("행사 신청 취소 - applyId가 없을 경우")
//    public void deleteApplyNonExistAppyIdFailTest(){
//        Long eventId = 100L;
//
//        Boolean response = applyReposiroty.deleteCheck(eventId);
//
//        assertNull(response);
//    }
//
//    @Test
//    @DisplayName("행사 신청 취소 - 이미 신청한 경우")
//    public void deleteApplyAlreadyCancelApplyFailTest(){
//        Long eventId = 2L;
//
//        Boolean response = applyReposiroty.deleteCheck(eventId);
//
//        assertEquals(false, response);
//    }
//
//    @Test
//    @DisplayName("행사 신청 취소")
//    public void deleteApplyTest(){
//        Long eventId = 1L;
//
//        Long response = applyReposiroty.deleteApply(eventId);
//
//        assertNotNull(response);
//    }
//
//    @Test
//    @DisplayName("행사 조회")
//    public void findByUserIdTest(){
//        Long userId = 100L;
//        Long ticketId = 1L;
//        Long eventId = 100L;
//        Long applicantNum = 5L;
//        String phone = "010-7777-8888";
//
//        CreateApplyDTO createApplyDTO = CreateApplyDTO
//                .builder()
//                .userId(userId)
//                .ticketId(ticketId)
//                .eventId(eventId)
//                .applicantNum(applicantNum)
//                .phone(phone)
//                .build();
//        Long id = applyReposiroty.insertApply(createApplyDTO);
//        applyReposiroty.deleteApply(id);
//        applyReposiroty.insertApply(createApplyDTO);
//
//        List<FindByUserIdDTO> response = applyReposiroty.findByUserId(userId);
//
//        assertEquals(1, response.size());
//        assertEquals(eventId, response.get(0).getEventId());
//        assertEquals(ticketId, response.get(0).getTicketId());
//        assertEquals(applicantNum, response.get(0).getApplicantNum());
//        assertEquals(phone, response.get(0).getPhone());
//        assertNull(response.get(0).getDeleteDate());
//    }
//}
