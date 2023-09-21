package com.eventty.businessservice.event.api.dto.response;


import lombok.Getter;

@Getter
public class QueryAppliesCountResponseDTO {
    Long ticketId;
    Long appliedTicketCount;

}