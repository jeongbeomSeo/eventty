package com.eventty.applyservice.application;

import com.eventty.applyservice.application.dto.CreateApplyDTO;
import com.eventty.applyservice.application.dto.FindByUserIdDTO;
import com.eventty.applyservice.application.dto.request.CreateApplyRequestDTO;
import com.eventty.applyservice.application.dto.response.FindAppicaionListResponseDTO;
import com.eventty.applyservice.application.dto.response.FindEventInfoResponseDTO;
import com.eventty.applyservice.application.dto.response.FindUsingTicketResponseDTO;
import com.eventty.applyservice.domain.ApplyReposiroty;
import com.eventty.applyservice.domain.exception.AlreadyCancelApplyException;
import com.eventty.applyservice.domain.exception.ExceedApplicantsException;
import com.eventty.applyservice.domain.exception.NonExistentIdException;
import com.eventty.applyservice.presentation.dto.ResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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
//    private final RestTemplate customRestTemplate;
    private final RestTemplate basicRestTemplate;
    private final ObjectMapper objectMapper;

    @Value("${server.base.url.eventServer}")
    private String uri = "/api/events";

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

        List<FindByUserIdDTO> findByUserIdDTOList = applyReposiroty.findByUserId(userId);
        if(findByUserIdDTOList.size() == 0 || findByUserIdDTOList == null)
            return null;

        Map<String, FindByUserIdDTO> map = new HashMap<>();
        for(FindByUserIdDTO findByUserIdDTO : findByUserIdDTOList){
            map.put(findByUserIdDTO.getEventId().toString(), findByUserIdDTO);
        }

        // uri && paramer create
        MultiValueMap<String, String> params = makeFindEventsListQueryParam(findByUserIdDTOList);
        URI findEventInfoListUri = makeFindEventsListUri(params);

        // api request
        List<FindEventInfoResponseDTO> eventResponses = apiExchange(findEventInfoListUri);

        // service logic
        List<FindAppicaionListResponseDTO> responses = new ArrayList<>();
        for(int i = 0; i<findByUserIdDTOList.size(); i++){
            FindEventInfoResponseDTO eventResponse = eventResponses.get(i);
            
            // 정합성 불일치
            FindByUserIdDTO findByUserIdDTO = map.get(eventResponse.getEventId().toString());
            if(findByUserIdDTO == null) {
                // 추가 로직 필요
            }

            String status = "";
            LocalDateTime date = null;
            if(findByUserIdDTO.getDeleteDate() != null) {
                status = "예약 취소";
                date = findByUserIdDTO.getDeleteDate();
            }else if(eventResponse.getEventEndAt().isBefore(LocalDateTime.now())) {
                status = "행사 종료";
                date = findByUserIdDTO.getApplyDate();
            }else {
                status = "예약 완료";
                date = findByUserIdDTO.getApplyDate();
            }

            FindAppicaionListResponseDTO findAppicaionListResponseDTO =
                    FindAppicaionListResponseDTO
                            .builder()
                            .title(eventResponse.getTitle())
                            .ticketName(eventResponse.getTicketName())
                            .ticketPrice(eventResponse.getTicketPrice())
                            .image(eventResponse.getImage())
                            .status(status)
                            .date(date)
                            .applyId(findByUserIdDTO.getApplyId())
                            .build();
            responses.add(findAppicaionListResponseDTO);
        }
        return responses;
    }

    @Override
    public List<FindUsingTicketResponseDTO> getUsingTicketList(Long eventId) {
        return applyReposiroty.findByEventIdGroupByTicket(eventId);
    }


    public MultiValueMap<String, String> makeFindEventsListQueryParam(List<FindByUserIdDTO> findByUserIdDTOList){
        List<String> eventId = new ArrayList<>();
        List<String> ticketId = new ArrayList<>();

        for(int i=0; i<findByUserIdDTOList.size(); i++){
            FindByUserIdDTO findByUserIdDTO =  findByUserIdDTOList.get(i);

            eventId.add(findByUserIdDTO.getEventId().toString());
            ticketId.add(findByUserIdDTO.getTicketId().toString());
        }

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.put("eventId", eventId);
        params.put("ticketId", ticketId);

        return params;
    }

    public URI makeFindEventsListUri(MultiValueMap<String, String> params){
        return UriComponentsBuilder
                .fromHttpUrl(uri + "/api/events")
                .queryParams(params)
                .build()
                .encode()
                .toUri();
    }

    public List<FindEventInfoResponseDTO> apiExchange(URI uri){
        log.info("API 호출 From: {} To: {} Purpose: {}", "Apply Server", "Event Server", "Find Events List");
        ResponseEntity<ResponseDTO<List<FindEventInfoResponseDTO>>> response = null;
        try{
            response =  basicRestTemplate.exchange(
                    uri,
                    HttpMethod.GET,
                    createHttpPostEntity(null),
                    new ParameterizedTypeReference<ResponseDTO<List<FindEventInfoResponseDTO>>>() {}
            );
        }catch(Exception e){
            e.printStackTrace();
//            log.error(e.getMessage());
        }

        return response.getBody().getSuccessResponseDTO().getData();
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

    private <T> HttpEntity<T> createHttpPostEntity(T dto) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        return new HttpEntity<>(dto, headers);
    }
}
