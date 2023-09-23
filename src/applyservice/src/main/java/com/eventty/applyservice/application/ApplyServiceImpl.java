package com.eventty.applyservice.application;

import com.eventty.applyservice.application.dto.CreateApplyDTO;
import com.eventty.applyservice.application.dto.FindByUserIdDTO;
import com.eventty.applyservice.application.dto.request.CreateApplyRequestDTO;
import com.eventty.applyservice.application.dto.response.FindAppicaionListResponseDTO;
import com.eventty.applyservice.application.dto.response.FindEventInfoResponseDTO;
import com.eventty.applyservice.application.dto.response.FindUsingTicketResponseDTO;
import com.eventty.applyservice.domain.ApplyReposiroty;
import com.eventty.applyservice.domain.code.ServerUri;
import com.eventty.applyservice.domain.exception.AlreadyCancelApplyException;
import com.eventty.applyservice.domain.exception.ExceedApplicantsException;
import com.eventty.applyservice.domain.exception.NonExistentIdException;
import com.eventty.applyservice.presentation.dto.ResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class ApplyServiceImpl implements ApplyService{

    private final ApplyReposiroty applyReposiroty;
    private final RestTemplate customRestTemplate;
    private final ServerUri serverUri;

    @Override
    public Long createApply(Long userId, CreateApplyRequestDTO createApplyRequestDTO) {
        // 신청전 유효성 검사
        validateBeforeApply(createApplyRequestDTO);

        return applyReposiroty.insertApply(CreateApplyDTO
                .builder()
                .userId(userId)
                .eventId(createApplyRequestDTO.getEventId())
                .ticketId(createApplyRequestDTO.getTicketId())
                .applicantNum(createApplyRequestDTO.getApplicantNum())
                .phone(createApplyRequestDTO.getPhone())
                .build());
    }

    @Override
    public Long cancelApply(Long applyId) {

        // 신청 취소전 유효성 검증
        validateBeforeCancel(applyId);

        return applyReposiroty.deleteApply(applyId);
    }

    @Override
    public List<FindAppicaionListResponseDTO> findApplicationList(Long userId) {

        List<FindByUserIdDTO> applies = applyReposiroty.findByUserId(userId);
        if(applies.size() == 0 || applies == null)
            return null;

        // Event Server로 보내기위한 TicketIds 중복 제거(Parameter 생성)
        Set<String> ticketIds = new HashSet<>();
        for(FindByUserIdDTO apply : applies){
            ticketIds.add(apply.getTicketId().toString());
        }
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.put("ticketIds", new ArrayList<>(ticketIds));


        // api request----------------------------------------------------
        List<FindEventInfoResponseDTO> eventInfos = apiRequest(params);


        // service logic--------------------------------------------------
        Map<Long, FindEventInfoResponseDTO> eventInfoMap = new HashMap<>();
        for(FindEventInfoResponseDTO eventResponse : eventInfos){
            eventInfoMap.put(eventResponse.getTicketId(), eventResponse);
        }

        List<FindAppicaionListResponseDTO> responses = new ArrayList<>();
        for(FindByUserIdDTO apply : applies){
            FindEventInfoResponseDTO eventResponse = eventInfoMap.get(apply.getTicketId());

            log.debug("EventRespone : {}", eventResponse);

            // 예약 상태(status) & 예약or취소 날짜(date) 설정
            String status;
            LocalDateTime date = null;
            if(apply.getDeleteDate() != null) {
                status = "예약 취소";
                date = apply.getDeleteDate();
            }else if(eventResponse.getEventEndAt().isBefore(LocalDateTime.now())) {
                status = "행사 종료";
                date = apply.getApplyDate();
            }else {
                status = "예약 완료";
                date = apply.getApplyDate();
            }

            // return값 세팅
            responses.add(FindAppicaionListResponseDTO
                                .builder()
                                .ticketName(eventResponse.getTicketName())
                                .title(eventResponse.getTitle())
                                .image(eventResponse.getImage())
                                .ticketPrice(eventResponse.getTicketPrice())
                                .status(status)
                                .date(date)
                                .applyId(apply.getApplyId())
                                .build());
        }
        
        return responses;
    }

    @Override
    public List<FindUsingTicketResponseDTO> getUsingTicketList(Long eventId) {
        return applyReposiroty.findByEventIdGroupByTicket(eventId);
    }

    //------------------------------------------ ApiClient -----------------------------------------------//
    public List<FindEventInfoResponseDTO> apiRequest(MultiValueMap<String, String> params){
        URI uri = makeUri(params,  serverUri.getEventServer() + serverUri.getGET_EVENT_TICKET_INFO());

        log.info("API 호출 From: {} To: {} Purpose: {}", "Apply Server", "Event Server", "Find Events List");
        ResponseEntity<ResponseDTO<List<FindEventInfoResponseDTO>>> response = null;
        try{
            response =  customRestTemplate.exchange(
                    uri,
                    HttpMethod.GET,
                    createHttpPostEntity(null),
                    new ParameterizedTypeReference<ResponseDTO<List<FindEventInfoResponseDTO>>>() {}
            );
        }catch(Exception e){
            log.error(e.getMessage());
        }

        if(response.getBody() == null || response.getBody().getErrorResponseDTO() != null){
            log.error("ApplyServer - response is null or getErrorResponse : {}", response);
            return null;
        }

        log.debug("Resposne : {}", response);
        if(response.getBody() != null && response.getBody().getSuccessResponseDTO() != null){
            for(FindEventInfoResponseDTO responseDTO : response.getBody().getSuccessResponseDTO().getData()){
                log.debug("ResponseDTO : {}", responseDTO);
            }
        }else {
            log.error("Response Error!!!!!");
        }

        return response.getBody().getSuccessResponseDTO().getData();
    }

    public URI makeUri(MultiValueMap<String, String> params, String path){
        return UriComponentsBuilder
                .fromHttpUrl(path)
                .queryParams(params)
                .build()
                .encode()
                .toUri();
    }

    private <T> HttpEntity<T> createHttpPostEntity(T dto) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.add("X-Requires-Auth", "True");

        return new HttpEntity<>(dto, headers);
    }

    //------------------------------------------ validation -----------------------------------------------//

    private void validateBeforeApply(CreateApplyRequestDTO createApplyRequestDTO){
        // 신청 인원수 확인
        Long count = applyReposiroty.getApplyNum(createApplyRequestDTO.getEventId());
        if(count != null && createApplyRequestDTO.getQuantity() <= count)
            throw new ExceedApplicantsException(count);
    }

    private void validateBeforeCancel(Long applyId){
        Boolean result = applyReposiroty.deleteCheck(applyId);
        // 유효하지 않는 appyId
        if(result == null)  throw new NonExistentIdException();
        // 이미 신청 취소한 applyId
        else if(!result)    throw new AlreadyCancelApplyException();
    }
}
