//package com.eventty.applyservice.application;
//
//import com.eventty.applyservice.application.dto.FindByUserIdDTO;
//import com.eventty.applyservice.application.dto.request.CreateApplyRequestDTO;
//import com.eventty.applyservice.application.dto.response.FindAppicaionListResponseDTO;
//import com.eventty.applyservice.application.dto.response.FindEventInfoResponseDTO;
//import com.eventty.applyservice.domain.ApplyReposiroty;
//import com.eventty.applyservice.domain.code.ServerUri;
//import com.eventty.applyservice.domain.exception.AlreadyCancelApplyException;
//import com.eventty.applyservice.domain.exception.ExceedApplicantsException;
//import com.eventty.applyservice.domain.exception.NonExistentIdException;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.web.client.RestTemplate;
//
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.concurrent.CountDownLatch;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//@Slf4j
//@ExtendWith(MockitoExtension.class)
//public class ApplyServiceTest {
//
//    @InjectMocks
//    private ApplyServiceImpl applyServiceImpl;
//
//    @Mock
//    private ApplyReposiroty applyReposiroty;
//
//    @Mock
//    private RestTemplate customRestTemplate;
//
//    @Mock
//    private ApiService apiService;
//
//    @Mock
//    private ServerUri serverUri;
//
//    @Test
//    @DisplayName("[Fail - 신청 인원 초과] 행사 신청")
//    public void ExceedApplicantsExceptionOfCreateApplyTest(){
//        Long userId = 3L;
//        Long eventId = 2L;
//        Long ticketId = 2L;
//        Long applicantNum = 2L;
//        String phone = "010-7898-4565";
//        Long quantity = 2L;
//
//        CreateApplyRequestDTO createApplyRequestDTO = CreateApplyRequestDTO
//                .builder()
//                .eventId(eventId)
//                .ticketId(ticketId)
//                .applicantNum(applicantNum)
//                .phone(phone)
//                .quantity(quantity)
//                .build();
//        when(applyReposiroty.getApplyNum(anyLong())).thenReturn(2L);
//
//        assertThrows(ExceedApplicantsException.class, () -> applyServiceImpl.createApply(userId, createApplyRequestDTO));
//    }
//
//    @Test
//    @DisplayName("[Success] 행사 신청")
//    public void createApplyTest(){
//        // Assignment
//        Long userId = 1000L;
//        Long eventId = 2000L;
//        Long ticketId = 2L;
//        Long quantity = 125L;
//        String phone = "010-1234-7895";
//        Long applicantNum = 3L;
//
//        CreateApplyRequestDTO createApplyRequestDTO = CreateApplyRequestDTO
//                        .builder()
//                        .ticketId(ticketId)
//                        .eventId(eventId)
//                        .quantity(quantity)
//                        .phone(phone)
//                        .applicantNum(applicantNum)
//                        .build();
//
//        // Act
//        Long id = applyServiceImpl.createApply(userId, createApplyRequestDTO);
//
//        // Assert
//        assertNotNull(id);
//    }
//
//    @Test
//    @DisplayName("[Fail - 없는 applyId] 행사 신청 취소")
//    public void NonExistentIdExceptionOfCancelApplyTest(){
//        Long applyId = 100L;
//        when(applyReposiroty.deleteCheck(anyLong())).thenReturn(null);
//
//        assertThrows(NonExistentIdException.class, () -> applyServiceImpl.cancelApply(applyId));
//    }
//
//    @Test
//    @DisplayName("[Fail - 이미 신청 취소된 id] 행사 신청 취소")
//    public void AlreadyCancelApplyExceptionOfCancelApplyTest(){
//        Long applyId = 2L;
//        when(applyReposiroty.deleteCheck(anyLong())).thenReturn(false);
//
//        assertThrows(AlreadyCancelApplyException.class, () -> applyServiceImpl.cancelApply(applyId));
//    }
//
//    @Test
//    @DisplayName("[Success] 행사 신청 취소")
//    public void cancelApplySuccessTest(){
//        Long applyId = 3L;
//        when(applyReposiroty.deleteCheck(anyLong())).thenReturn(true);
//
//        Long response = applyServiceImpl.cancelApply(applyId);
//
//        assertNotNull(response);
//    }
//
//    @Test
//    @DisplayName("[Success - null] 신청 목록 조회")
//    public void viewApplicationListNullSuccessTest(){
//        Long userId = 2L;
//        when(applyReposiroty.findByUserId(anyLong())).thenReturn(new ArrayList<>());
//
//        List<FindAppicaionListResponseDTO> response = applyServiceImpl.findApplicationList(userId);
//
//        assertNull(response);
//    }
//
//    @Test
//    @DisplayName("[Success]신청 목록 조회")
//    public void MultiValueMapTest(){
//        Long userId = 2L;
//
//        FindByUserIdDTO findByUserIdDTO = FindByUserIdDTO
//                .builder()
//                .ticketId(100L)
//                .eventId(100L)
//                .phone("010-7777-8888")
//                .applicantNum(3L)
//                .applyId(100L)
//                .applyDate(LocalDateTime.now())
//                .build();
//
//        List<FindByUserIdDTO> findByUserIdDTOList = new ArrayList<>();
//        findByUserIdDTOList.add(findByUserIdDTO);
//        when(applyReposiroty.findByUserId(anyLong())).thenReturn(findByUserIdDTOList);
//
//        FindEventInfoResponseDTO eventInfo = FindEventInfoResponseDTO
//                .builder()
//                .ticketPrice(90000L)
//                .image("src/src/image.jpg")
//                .title("title1")
//                .ticketName("ticketName")
//                .eventEndAt(LocalDateTime.of(2023, 11, 15, 00, 00))
//                .eventId(100L)
//                .ticketId(100L)
//                .build();
//        List<FindEventInfoResponseDTO> eventInfos = new ArrayList<>();
//        eventInfos.add(eventInfo);
//        when(apiService.apiRequest()).thenReturn(eventInfos);
//
//        List<FindAppicaionListResponseDTO> response =  applyServiceImpl.findApplicationList(userId);
//
//        assertNotNull(response);
//        assertEquals("예약 완료", response.get(0).getStatus());
//    }
//}
