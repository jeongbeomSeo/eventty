package com.eventty.businessservice.presentation;

import com.eventty.businessservice.domains.event.application.dto.request.EventCreateRequestDTO;
import com.eventty.businessservice.domains.event.application.dto.response.EventWithTicketsFindByIdResponseDTO;
import com.eventty.businessservice.domains.event.application.dto.response.EventBasicFindAllResponseDTO;
import com.eventty.businessservice.domains.event.application.Facade.EventService;
import com.eventty.businessservice.domains.event.presentation.EventController;
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

//    @Test
//    @DisplayName("특정 행사 조회 테스트")
//    public void findEventByIdTest() throws Exception {
//        // Given
//        Long eventId = 1L;
//        EventWithTicketsFindByIdResponseDTO MockEvent = createEventWithDetailDTO(eventId);
//        when(eventService.findEventById(eventId)).thenReturn(MockEvent);
//
//        // When & Then
//        mockMvc.perform(get("/api/events/{eventId}", eventId))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.success").value(true))
//                .andExpect(jsonPath("$.successResponseDTO.data.id", equalTo(eventId.intValue())))
//                .andExpect(jsonPath("$.successResponseDTO.data.title", equalTo("Sample Event")));
//
//        verify(eventService, times(1)).findEventById(eventId);
//    }

    @Test
    @DisplayName("전체 행사 조회 테스트")
    public void findAllEventsTest() throws Exception {
        // Given
        List<EventBasicFindAllResponseDTO> mockEventList = createEventRespnseDTOList(3L);
        when(eventService.findAllEvents()).thenReturn(mockEventList);

        // When & Then
        mockMvc.perform(get("/events"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.successResponseDTO.data").isArray())
                .andExpect(jsonPath("$.successResponseDTO.data.length()").value(mockEventList.size()));

        verify(eventService, times(1)).findAllEvents();
    }

    @Test
    @DisplayName("행사 생성 테스트")
    public void createEventTest() throws Exception {
        // Given
        Long newEventId = 10L;
        EventCreateRequestDTO eventCreateRequestDTO = createEventFullCreateRequestDTO();
        when(eventService.createEvent(eventCreateRequestDTO)).thenReturn(newEventId);

        // When & Then
        mockMvc.perform(post("/events")
                .content(objectMapper.writeValueAsString(eventCreateRequestDTO))  // JSON 데이터 추가
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true));
    }

    @Test
    @DisplayName("행사 삭제 테스트")
    public void deleteEventTest() throws Exception {
        // Given
        Long eventId = 1L;
        when(eventService.deleteEvent(eventId)).thenReturn(eventId);

        // When & Then
        mockMvc.perform(delete("/events/{eventId}", eventId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success").value(true));

        verify(eventService, times(1)).deleteEvent(eventId);
    }

    @Test
    @DisplayName("카테고리 별 행사 조회 테스트")
    public void findEventsByCategoryTest() throws Exception {
        // Given
        Long categoryId = 3L;
        when(eventService.findEventsByCategory(categoryId)).thenReturn(createEventRespnseDTOList(5L));

        // When & Then
        mockMvc.perform(get("/events/category/{categoryId}", categoryId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success").value(true));

        verify(eventService, times(1)).findEventsByCategory(categoryId);
    }

    private static EventCreateRequestDTO createEventFullCreateRequestDTO() {
        return EventCreateRequestDTO.builder()
                //.id(1L)
                .userId(1L)
                .title("Event Title")
                .image("event_image.jpg")
                .eventStartAt(Timestamp.valueOf("2023-09-01 10:00:00").toLocalDateTime())
                .eventEndAt(Timestamp.valueOf("2023-09-01 18:00:00").toLocalDateTime())
                .participateNum(100L)
                .location("Event Location")
                .category(1L)
                .content("Event Content")
                .applyStartAt(Timestamp.valueOf("2023-08-15 10:00:00").toLocalDateTime())
                .applyEndAt(Timestamp.valueOf("2023-08-31 18:00:00").toLocalDateTime())
                //.views(500L)
                .build();
    }

    private static EventBasicFindAllResponseDTO createEventResponseDTO(Long id){
        return EventBasicFindAllResponseDTO.builder()
            .id(id)
            .userId(1L)
            .title("Sample Event")
            .image("sample.jpg")
            .eventStartAt(Timestamp.valueOf("2023-08-21 10:00:00").toLocalDateTime())
            .eventEndAt(Timestamp.valueOf("2023-08-21 15:00:00").toLocalDateTime())
            .participateNum(100L)
            .location("Sample Location")
            .category(1L)
            .isActive(true)
            .isDeleted(false)
            .build();
    }

    private static EventWithTicketsFindByIdResponseDTO createEventWithDetailDTO(Long id){
        return EventWithTicketsFindByIdResponseDTO.builder()
                .id(id)
                .userId(1L)
                .title("Sample Event")
                .image("sample.jpg")
                .eventStartAt(Timestamp.valueOf("2023-08-21 10:00:00").toLocalDateTime())
                .eventEndAt(Timestamp.valueOf("2023-08-21 15:00:00").toLocalDateTime())
                .participateNum(100L)
                .location("Sample Location")
                .categoryName("Music")
                .isActive(true)
                .isDeleted(false)
                .content("Sample content")
                .applyStartAt(Timestamp.valueOf("2023-08-21 10:00:00").toLocalDateTime())
                .applyEndAt(Timestamp.valueOf("2023-08-21 15:00:00").toLocalDateTime())
                .views(100L)
                .build();
    }

    private static List<EventBasicFindAllResponseDTO> createEventRespnseDTOList(Long count) {
        List<EventBasicFindAllResponseDTO> eventBasicFindAllResponseDTOList = new ArrayList<>();

        for (Long i = 0L; i < count; i++) {
            EventBasicFindAllResponseDTO eventBasicFindAllResponseDTO = createEventResponseDTO(i);
            eventBasicFindAllResponseDTOList.add(eventBasicFindAllResponseDTO);
        }

        return eventBasicFindAllResponseDTOList;
    }
}
