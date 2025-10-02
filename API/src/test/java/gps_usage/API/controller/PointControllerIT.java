package gps_usage.API.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import gps_usage.API.dto.RouteDTO;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class PointControllerIT {
    private final MockMvc mockMvc;
    private final ObjectMapper mapper;

    @Autowired
    public PointControllerIT(MockMvc mockMvc, ObjectMapper mapper) {
        this.mockMvc = mockMvc;
        this.mapper = mapper;
    }

    @Test
    void testPost_SinglePointAllFieldsProvided() throws Exception {
        String jsonBody = """
                {
                    "name": "Test",
                    "numberOfPoints": 10,
                    "time": "2025-07-06"
                }
                """;
        String routeJSON = mockMvc.perform(MockMvcRequestBuilders.post("/route/post")
                        .contentType("application/json")
                        .content(jsonBody))
                .andReturn().getResponse().getContentAsString();
        RouteDTO route = mapper.readValue(routeJSON, RouteDTO.class);
        String routeId = route.getId().toString();
        String jsonBodyPoint = """
                [
                    {
                        "longitude": 50.444518,
                        "latitude": 23.426729,
                        "time": 1
                    }
                ]
                """;
        mockMvc.perform(MockMvcRequestBuilders.post("/point/post/".concat(routeId))
                        .contentType("application/json")
                        .content(jsonBodyPoint))
                .andExpect(MockMvcResultMatchers.status().isCreated());
        mockMvc.perform(MockMvcRequestBuilders.get("/point/get/".concat(routeId)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].longitude").value(50.444518))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].latitude").value(23.426729))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].time").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].routeId").value(routeId));
    }

    @Test
    void testPost_SinglePointMissingFields() throws Exception {
        String jsonBody = """
                {
                    "name": "Test",
                    "numberOfPoints": 10,
                    "time": "2025-07-06"
                }
                """;
        String routeJSON = mockMvc.perform(MockMvcRequestBuilders.post("/route/post")
                        .contentType("application/json")
                        .content(jsonBody))
                .andReturn().getResponse().getContentAsString();
        RouteDTO route = mapper.readValue(routeJSON, RouteDTO.class);
        String routeId = route.getId().toString();
        String jsonBodyPoint = """
                [
                    {
                        "longitude": 50.444518
                    }
                ]
                """;
        mockMvc.perform(MockMvcRequestBuilders.post("/point/post/".concat(routeId))
                        .contentType("application/json")
                        .content(jsonBodyPoint))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void testPostGet_MultiplePointsAllFieldsProvided() throws Exception {
        String jsonBody = """
                {
                    "name": "Test",
                    "numberOfPoints": 10,
                    "time": "2025-07-06"
                }
                """;
        String routeJSON = mockMvc.perform(MockMvcRequestBuilders.post("/route/post")
                        .contentType("application/json")
                        .content(jsonBody))
                .andReturn().getResponse().getContentAsString();
        RouteDTO route = mapper.readValue(routeJSON, RouteDTO.class);
        String routeId = route.getId().toString();
        String jsonBodyPoint = """
                [
                    {
                        "longitude": 50.444518,
                        "latitude": 23.426729,
                        "time": 1
                    },
                    {
                        "longitude": 50.444629,
                        "latitude": 23.426830,
                        "time": 2
                    },
                    {
                        "longitude": 50.444730,
                        "latitude": 23.426941,
                        "time": 3
                    }
                ]
                """;
        mockMvc.perform(MockMvcRequestBuilders.post("/point/post/".concat(routeId))
                        .contentType("application/json")
                        .content(jsonBodyPoint))
                .andExpect(MockMvcResultMatchers.status().isCreated());
        mockMvc.perform(MockMvcRequestBuilders.get("/point/get/".concat(routeId)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].longitude").value(50.444518))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].latitude").value(23.426729))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].time").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].routeId").value(routeId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[1].longitude").value(50.444629))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[1].latitude").value(23.426830))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[1].time").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[1].routeId").value(routeId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[2].longitude").value(50.444730))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[2].latitude").value(23.426941))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[2].time").value(3))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[2].routeId").value(routeId));
    }

    @Test
    void testGet_Pages() throws Exception {
        String jsonBody = """
                {
                    "name": "Test",
                    "numberOfPoints": 10,
                    "time": "2025-07-06"
                }
                """;
        String routeJSON = mockMvc.perform(MockMvcRequestBuilders.post("/route/post")
                        .contentType("application/json")
                        .content(jsonBody))
                .andReturn().getResponse().getContentAsString();
        RouteDTO route = mapper.readValue(routeJSON, RouteDTO.class);
        String routeId = route.getId().toString();
        String jsonBodyPoint = """
                [
                    {
                        "longitude": 50.444518,
                        "latitude": 23.426729,
                        "time": 1
                    },
                    {
                        "longitude": 50.444629,
                        "latitude": 23.426830,
                        "time": 2
                    },
                    {
                        "longitude": 50.444730,
                        "latitude": 23.426941,
                        "time": 3
                    }
                ]
                """;
        mockMvc.perform(MockMvcRequestBuilders.post("/point/post/".concat(routeId))
                        .contentType("application/json")
                        .content(jsonBodyPoint))
                .andExpect(MockMvcResultMatchers.status().isCreated());
        mockMvc.perform(MockMvcRequestBuilders.get("/point/get/".concat(routeId))
                        .param("pageNumber", "0")
                        .param("pageSize", "2"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].longitude").value(50.444518))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].latitude").value(23.426729))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].time").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].routeId").value(routeId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[1].longitude").value(50.444629))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[1].latitude").value(23.426830))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[1].time").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[1].routeId").value(routeId));
        mockMvc.perform(MockMvcRequestBuilders.get("/point/get/".concat(routeId))
                        .param("pageNumber", "1")
                        .param("pageSize", "2"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].longitude").value(50.444730))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].latitude").value(23.426941))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].time").value(3))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].routeId").value(routeId));
    }

    @Test
    void testPost_MultiplePointsPartiallyMissingFields() throws Exception {
        String jsonBody = """
                {
                    "name": "Test",
                    "numberOfPoints": 10,
                    "time": "2025-07-06"
                }
                """;
        String routeJSON = mockMvc.perform(MockMvcRequestBuilders.post("/route/post")
                        .contentType("application/json")
                        .content(jsonBody))
                .andReturn().getResponse().getContentAsString();
        RouteDTO route = mapper.readValue(routeJSON, RouteDTO.class);
        String routeId = route.getId().toString();
        String jsonBodyPoint = """
                [
                    {
                        "longitude": 50.444518,
                        "latitude": 23.426729,
                        "time": 1
                    },
                    {
                        "longitude": 50.444629
                    },
                    {
                        "longitude": 50.444730,
                        "latitude": 23.426941,
                        "time": 3
                    }
                ]
                """;
        mockMvc.perform(MockMvcRequestBuilders.post("/point/post/".concat(routeId))
                        .contentType("application/json")
                        .content(jsonBodyPoint))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}
