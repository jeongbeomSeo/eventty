//package com.eventty.applyservice.application;
//
//import com.eventty.applyservice.application.dto.CheckAlreadyApplyUserDTO;
//import com.eventty.applyservice.application.dto.FindByUserIdDTO;
//import com.eventty.applyservice.application.dto.request.CreateApplyRequestDTO;
//import com.eventty.applyservice.application.dto.request.FindEventInfoRequestDTO;
//import com.eventty.applyservice.application.dto.response.FindEventInfoResponseDTO;
//import com.eventty.applyservice.domain.ApplyReposiroty;
//import com.eventty.applyservice.domain.exception.AlreadyApplyUserException;
//import com.eventty.applyservice.domain.exception.AlreadyCancelApplyException;
//import com.eventty.applyservice.domain.exception.ExceedApplicantsException;
//import com.eventty.applyservice.domain.exception.NonExistentIdException;
//import com.eventty.applyservice.presentation.dto.ResponseDTO;
//import com.eventty.applyservice.presentation.dto.SuccessResponseDTO;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.core.ParameterizedTypeReference;
//import org.springframework.http.*;
//import org.springframework.util.MultiValueMap;
//import org.springframework.web.client.RestTemplate;
//
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
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
//        when(applyReposiroty.findByUserId(anyLong())).thenReturn(null);
//
//        List<FindEventInfoResponseDTO> response = applyServiceImpl.findApplicationList(userId);
//
//        assertNull(response);
//    }
//
//    @Test
//    @DisplayName("[Sub - 파라미터 생성] 신청 목록 조회")
//    public void MultiValueMapTest(){
//        FindByUserIdDTO findByUserIdDTO = new FindByUserIdDTO();
//        findByUserIdDTO.setEventId(1L);
//        findByUserIdDTO.setTicketId(1L);
//
//        List<FindByUserIdDTO> findByUserIdDTOList = new ArrayList<>();
//        findByUserIdDTOList.add(findByUserIdDTO);
//
//        MultiValueMap<String, String> params =  applyServiceImpl.makeFindEventsListQueryParam(findByUserIdDTOList);
//
//        assertNotNull(params);
//    }
//
////    @Test
////    @DisplayName("[Success] 신청 목록 조회")
////    public void viewApplicationListSuccessTest(){
////        Long userId = 2L;
////
////        List<FindByUserIdDTO> findByUserIdDTOList = new ArrayList<>();
////        FindByUserIdDTO findByUserIdDTO = FindByUserIdDTO
////                .builder()
////                .applyDate(LocalDateTime.now())
////                .deleteDate(null)
////                .eventId(2L)
////                .ticketId(1L)
////                .applicantNum(3L)
////                .phone("010-7777-8888")
////                .build();
////        findByUserIdDTOList.add(findByUserIdDTO);
////
////        List<FindEventInfoResponseDTO> responseDTOList = new ArrayList<>();
////        FindEventInfoResponseDTO responseDTO = FindEventInfoResponseDTO
////                .builder()
////                .image("asldkjfa;slkdjfaskjdfalksfjasdkf")
////                .title("title")
////                .ticketName("VVIP")
////                .ticketPrice(280000L)
////                .build();
////        responseDTOList.add(responseDTO);
////
////        HttpHeaders headers = new HttpHeaders();
////        headers.setContentType(MediaType.APPLICATION_JSON);
////        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
////
////        ResponseEntity<ResponseDTO<List<FindEventInfoResponseDTO>>> responseEntity = new ResponseEntity(ResponseDTO.of(SuccessResponseDTO.of(responseDTOList)), HttpStatus.OK);
////
////        when(applyReposiroty.findByUserId(anyLong())).thenReturn(findByUserIdDTOList);
////        when(customRestTemplate.exchange(
////                anyString(),
////                HttpMethod.GET,
////                any(HttpEntity.class),
////                new ParameterizedTypeReference<ResponseDTO<List<FindEventInfoResponseDTO>>>() {}
////        ))
////                .thenReturn(responseEntity);
////
////        List<FindEventInfoResponseDTO> response = applyServiceImpl.viewApplicationList(userId);
////
////        assertNotNull(response);
////    }
//}
