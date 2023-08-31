package com.eventty.businessservice.presentation;

import com.eventty.businessservice.application.dto.request.EventFullCreateRequestDTO;
import com.eventty.businessservice.application.dto.response.EventDetailFindByIdResponseDTO;
import com.eventty.businessservice.application.dto.response.EventFindByIdWithDetailResponseDTO;
import com.eventty.businessservice.application.dto.response.EventFindAllResponseDTO;
import com.eventty.businessservice.application.service.EventService;
import com.eventty.businessservice.presentation.EventController;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private EventService eventService;

    @Test
    @DisplayName("특정 행사 조회 테스트")
    public void findEventByIdTest() throws Exception {
        // Given
        Long eventId = 1L;
        EventFindByIdWithDetailResponseDTO MockEvent = createEventWithDetailDTO(eventId);
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
        List<EventFindAllResponseDTO> mockEventList = createEventRespnseDTOList(3L);
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

    @Test
    @DisplayName("행사 생성 테스트")
    public void createEventTest() throws Exception {
        // Given
        Long newEventId = 10L;
        EventFullCreateRequestDTO eventFullCreateRequestDTO = createEventFullCreateRequestDTO();
        when(eventService.createEvent(eventFullCreateRequestDTO)).thenReturn(newEventId);

        // When & Then
        mockMvc.perform(post("/api/events")
                .content(objectMapper.writeValueAsString(eventFullCreateRequestDTO))  // JSON 데이터 추가
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.code").value("0"))
                .andExpect(jsonPath("$.message").value("Event created successfully"));
    }

    @Test
    @DisplayName("행사 삭제 테스트")
    public void deleteEventTest() throws Exception {
        // Given
        Long eventId = 1L;
        when(eventService.deleteEvent(eventId)).thenReturn(eventId);

        // When & Then
        mockMvc.perform(delete("/api/events/{eventId}", eventId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.code").value("0"))
                .andExpect(jsonPath("$.message").value("Event deleted successfully"));

        verify(eventService, times(1)).deleteEvent(eventId);
    }

    private static EventFullCreateRequestDTO createEventFullCreateRequestDTO() {
        return EventFullCreateRequestDTO.builder()
                //.id(1L)
                .hostId(1L)
                .title("Event Title")
                .image("event_image.jpg")
                .eventStartAt(Timestamp.valueOf("2023-09-01 10:00:00"))
                .eventEndAt(Timestamp.valueOf("2023-09-01 18:00:00"))
                .participateNum(100L)
                .location("Event Location")
                .category("Event Category")
                .content("Event Content")
                .applyStartAt(Timestamp.valueOf("2023-08-15 10:00:00"))
                .applyEndAt(Timestamp.valueOf("2023-08-31 18:00:00"))
                .views(500L)
                .build();
    }

    private static EventFindAllResponseDTO createEventResponseDTO(Long id){
        return EventFindAllResponseDTO.builder()
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

    private static EventFindByIdWithDetailResponseDTO createEventWithDetailDTO(Long id){
        return EventFindByIdWithDetailResponseDTO.builder()
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

    private static List<EventFindAllResponseDTO> createEventRespnseDTOList(Long count) {
        List<EventFindAllResponseDTO> eventFindAllResponseDTOList = new ArrayList<>();

        for (Long i = 0L; i < count; i++) {
            EventFindAllResponseDTO eventFindAllResponseDTO = createEventResponseDTO(i);
            eventFindAllResponseDTOList.add(eventFindAllResponseDTO);
        }

        return eventFindAllResponseDTOList;
    }
}
