package com.eventty.applyservice.application;

import com.eventty.applyservice.application.dto.CheckAlreadyApplyUserDTO;
import com.eventty.applyservice.application.dto.request.CreateApplyRequestDTO;
import com.eventty.applyservice.domain.ApplyReposiroty;
import com.eventty.applyservice.domain.exception.AlreadyApplyUserException;
import com.eventty.applyservice.domain.exception.AlreadyCancelApplyException;
import com.eventty.applyservice.domain.exception.ExceedApplicantsException;
import com.eventty.applyservice.domain.exception.NonExistentIdException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ApplyServiceTest {

    @InjectMocks
    private ApplyServiceImpl applyServiceImpl;

    @Mock
    private ApplyReposiroty applyReposiroty;

    @Test
    @DisplayName("[Success] 행사 신청")
    public void createApplyTest(){
        // Assignment
        Long userId = 1000L;
        Long eventId = 2000L;
        Long ticketId = 2L;
        Long participateNum = 125L;

        CreateApplyRequestDTO createApplyRequestDTO = CreateApplyRequestDTO
                        .builder()
                        .ticketId(ticketId)
                        .eventId(eventId)
                        .participateNum(participateNum)
                        .build();

        // Act
        Long id = applyServiceImpl.createApply(userId, createApplyRequestDTO);

        // Assert
        assertNotNull(id);
    }

    @Test
    @DisplayName("[Fail - 이미 신청한 유저] 행사 신청")
    public void alreayApplyUserExceptionOfCreateApplyTest(){
        Long userId = 1L;
        Long eventId = 1L;
        Long ticketId = 2L;
        Long participateNum = 125L;


        CreateApplyRequestDTO createApplyRequestDTO = new CreateApplyRequestDTO(eventId, ticketId, participateNum);
        when(applyReposiroty.checkAlreadyApplyUser(any(CheckAlreadyApplyUserDTO.class))).thenReturn(2L);

        assertThrows(AlreadyApplyUserException.class, () -> applyServiceImpl.createApply(userId, createApplyRequestDTO));
    }

    @Test
    @DisplayName("[Fail - 신청 인원 초과] 행사 신청")
    public void ExceedApplicantsExceptionOfCreateApplyTest(){
        Long userId = 3L;
        Long eventId = 2L;
        Long ticketId = 2L;
        Long participateNum = 2L;

        CreateApplyRequestDTO createApplyRequestDTO = new CreateApplyRequestDTO(eventId, ticketId, participateNum);
        when(applyReposiroty.checkApplyNum(anyLong())).thenReturn(2L);

        assertThrows(ExceedApplicantsException.class, () -> applyServiceImpl.createApply(userId, createApplyRequestDTO));
    }

    @Test
    @DisplayName("[Success] 행사 신청 취소")
    public void cancelApplySuccessTest(){
        Long applyId = 3L;
        when(applyReposiroty.deleteCheck(anyLong())).thenReturn(true);

        Long response = applyServiceImpl.cancelApply(applyId);

        assertNotNull(response);
    }
    
    @Test
    @DisplayName("[Fail - 없는 applyId] 행사 신청 취소")
    public void NonExistentIdExceptionOfCancelApplyTest(){
        Long applyId = 100L;
        when(applyReposiroty.deleteCheck(anyLong())).thenReturn(null);

        assertThrows(NonExistentIdException.class, () -> applyServiceImpl.cancelApply(applyId));
    }

    @Test
    @DisplayName("[Fail - 이미 신청 취소된 id] 행사 신청 취소")
    public void AlreadyCancelApplyExceptionOfCancelApplyTest(){
        Long applyId = 2L;
        when(applyReposiroty.deleteCheck(anyLong())).thenReturn(false);

        assertThrows(AlreadyCancelApplyException.class, () -> applyServiceImpl.cancelApply(applyId));
    }
}
