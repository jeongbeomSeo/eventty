package com.eventty.businessservice.presentation;

import com.eventty.businessservice.event.presentation.EventController;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

@WebMvcTest(EventController.class) // This annotation includes @Autowired for MockMvc
public class EventControllerTest {

    /*
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private EventBasicService eventBasicService;

    @Test
    @DisplayName("특정 행사 조회 테스트")
    public void findEventByIdTest() throws Exception {
        // Given
        Long eventId = 1L;
        FullEventFindByIdResponseDTO MockEvent = createEventWithDetailDTO(eventId);
        when(eventBasicService.findEventById(eventId)).thenReturn(MockEvent);

        // When & Then
        mockMvc.perform(get("/events/{eventId}", eventId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.successResponseDTO.data.id", equalTo(eventId.intValue())))
                .andExpect(jsonPath("$.successResponseDTO.data.title", equalTo("Sample Event")));

        verify(eventBasicService, times(1)).findEventById(eventId);
    }

    @Test
    @DisplayName("전체 행사 조회 테스트")
    public void findAllEventsTest() throws Exception {
        // Given
        List<EventBasicWithoutHostInfoResponseDTO> mockEventList = createEventRespnseDTOList(3L);
        when(eventBasicService.findAllEvents()).thenReturn(mockEventList);

        // When & Then
        mockMvc.perform(get("/events"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.successResponseDTO.data").isArray())
                .andExpect(jsonPath("$.successResponseDTO.data.length()").value(mockEventList.size()));

        verify(eventBasicService, times(1)).findAllEvents();
    }

    @Test
    @DisplayName("행사 생성 테스트")
    public void createEventTest() throws Exception {
        // Given
        Long newEventId = 10L;
        EventCreateRequestDTO eventCreateRequestDTO = createEventFullCreateRequestDTO();
        when(eventBasicService.createEvent(eventCreateRequestDTO)).thenReturn(newEventId);

        // When & Then
        mockMvc.perform(post("/events")
                .content(objectMapper.writeValueAsString(eventCreateRequestDTO))  // JSON 데이터 추가
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true));
    }

    @Test
    @DisplayName("주최자가 등록한 행사 조회 테스트")
    public void findEventsByHostIdTest() throws Exception {
        // Given
        Long hostId = 1L;
        List<EventBasicWithoutHostInfoResponseDTO> mockEventList = createEventRespnseDTOList(3L);
        when(eventBasicService.findEventsByHostId(hostId)).thenReturn(mockEventList);

        // When & Then
        mockMvc.perform(get("/events/host/{hostId}", hostId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.successResponseDTO.data").isArray())
                .andExpect(jsonPath("$.successResponseDTO.data.length()").value(mockEventList.size()));

        verify(eventBasicService, times(1)).findEventsByHostId(hostId);
    }

    @Test
    @DisplayName("행사 삭제 테스트")
    public void deleteEventTest() throws Exception {
        // Given
        Long eventId = 1L;
        when(eventBasicService.deleteEvent(eventId)).thenReturn(eventId);

        // When & Then
        mockMvc.perform(delete("/events/{eventId}", eventId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success").value(true));

        verify(eventBasicService, times(1)).deleteEvent(eventId);
    }

    @Test
    @DisplayName("카테고리 별 행사 조회 테스트")
    public void findEventsByCategoryTest() throws Exception {
        // Given
        Category category = Category.콘서트;
        when(eventBasicService.findEventsByCategory(category)).thenReturn(createEventRespnseDTOList(5L));

        // When & Then
        mockMvc.perform(get("/events/category/{category}", category))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success").value(true));

        verify(eventBasicService, times(1)).findEventsByCategory(category);
    }

    @Test
    @DisplayName("행사 검색 테스트")
    public void searchEventsTest() throws Exception {
        // Given
        String keyword = "Sample";
        when(eventBasicService.findEventsBySearch(keyword)).thenReturn(createEventRespnseDTOList(5L));

        // When & Then
        mockMvc.perform(get("/events/search")
                .param("keyword", keyword))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success").value(true));

        verify(eventBasicService, times(1)).findEventsBySearch(keyword);
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

    private static EventBasicWithoutHostInfoResponseDTO createEventResponseDTO(Long id){
        return EventBasicWithoutHostInfoResponseDTO.builder()
            .id(id)
            .hostId(1L)
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

    private static FullEventFindByIdResponseDTO createEventWithDetailDTO(Long id){
        return FullEventFindByIdResponseDTO.builder()
                .id(id)
                .hostId(1L)
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

    private static List<EventBasicWithoutHostInfoResponseDTO> createEventRespnseDTOList(Long count) {
        List<EventBasicWithoutHostInfoResponseDTO> eventBasicFindAllResponseDTOList = new ArrayList<>();

        for (Long i = 0L; i < count; i++) {
            EventBasicWithoutHostInfoResponseDTO eventBasicFindAllResponseDTO = createEventResponseDTO(i);
            eventBasicFindAllResponseDTOList.add(eventBasicFindAllResponseDTO);
        }

        return eventBasicFindAllResponseDTOList;
    }

     */
}
