package com.eventty.businessservice.domains.event.application;

public interface EventDetailService {
    EventDetailResponseDTO findEventDetailById(Long id);

    //List<EventDetailResponseDTO> findAllEventDetails();
}
