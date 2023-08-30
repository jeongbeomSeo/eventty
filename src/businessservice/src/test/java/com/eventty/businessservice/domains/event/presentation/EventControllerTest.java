package com.eventty.businessservice.domains.event.presentation;

import com.eventty.businessservice.application.dto.response.EventDetailResponseDTO;
import com.eventty.businessservice.application.dto.response.EventWithDetailDTO;
import com.eventty.businessservice.application.dto.response.EventResponseDTO;
import com.eventty.businessservice.application.service.EventDetailService;
import com.eventty.businessservice.application.service.EventService;
import com.eventty.businessservice.presentation.EventController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EventController.class) // This annotation includes @Autowired for MockMvc
public class EventControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EventService eventService;

    @MockBean
    private EventDetailService eventDetailService;

    @Test
    @DisplayName("특정 행사 조회 테스트")
    public void findEventByIdTest() throws Exception {
        // Given
        Long eventId = 1L;
        EventWithDetailDTO MockEvent = createEventWithDetailDTO(eventId);
        when(eventService.findEventById(eventId)).thenReturn(MockEvent);

        // When & Then
        mockMvc.perform(get("/api/events/{eventId}", eventId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.code").value("0"))
                .andExpect(jsonPath("$.data.id", equalTo(eventId.intValue())))
                .andExpect(jsonPath("$.data.title", equalTo("Sample Event")));

        verify(eventService, times(1)).findEventById(eventId);
    }

    @Test
    @DisplayName("전체 행사 조회 테스트")
    public void findAllEventsTest() throws Exception {
        // Given
        List<EventResponseDTO> mockEventList = createEventRespnseDTOList(3L);
        when(eventService.findAllEvents()).thenReturn(mockEventList);

        // When & Then
        mockMvc.perform(get("/api/events"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.code").value("0"))
                .andExpect(jsonPath("$.message").value("Event retrieved successfully"))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data.length()").value(mockEventList.size()));

        verify(eventService, times(1)).findAllEvents();
    }

    private static EventResponseDTO createEventResponseDTO(Long id){
        return EventResponseDTO.builder()
            .id(id)
            .hostId(1L)
            .title("Sample Event")
            .image("sample.jpg")
            .eventStartAt(Timestamp.valueOf("2023-08-21 10:00:00"))
            .eventEndAt(Timestamp.valueOf("2023-08-21 15:00:00"))
            .participateNum(100L)
            .location("Sample Location")
            .category("Sample Category")
            .isActive(true)
            .isDeleted(false)
            .build();
    }

    private static EventDetailResponseDTO createEventDetailDTO(Long id){
        return EventDetailResponseDTO.builder()
                .id(id)
                .content("Sample content")
                .applyStartAt(Timestamp.valueOf("2023-08-21 10:00:00"))
                .applyEndAt(Timestamp.valueOf("2023-08-21 15:00:00"))
                .views(100L)
                .deleteDate(Timestamp.valueOf("2023-08-21 12:00:00"))
                .updateDate(Timestamp.valueOf("2023-08-21 13:00:00"))
                .createDate(Timestamp.valueOf("2023-08-21 10:30:00"))
                .build();
    }

    private static EventWithDetailDTO createEventWithDetailDTO(Long id){
        return EventWithDetailDTO.builder()
                .id(id)
                .hostId(1L)
                .title("Sample Event")
                .image("sample.jpg")
                .eventStartAt(Timestamp.valueOf("2023-08-21 10:00:00"))
                .eventEndAt(Timestamp.valueOf("2023-08-21 15:00:00"))
                .participateNum(100L)
                .location("Sample Location")
                .category("Sample Category")
                .isActive(true)
                .isDeleted(false)
                .content("Sample content")
                .applyStartAt(Timestamp.valueOf("2023-08-21 10:00:00"))
                .applyEndAt(Timestamp.valueOf("2023-08-21 15:00:00"))
                .views(100L)
                .build();
    }

    private static List<EventResponseDTO> createEventRespnseDTOList(Long count) {
        List<EventResponseDTO> eventResponseDTOList = new ArrayList<>();

        for (Long i = 0L; i < count; i++) {
            EventResponseDTO eventResponseDTO = createEventResponseDTO(i);
            eventResponseDTOList.add(eventResponseDTO);
        }

        return eventResponseDTOList;
    }
}
