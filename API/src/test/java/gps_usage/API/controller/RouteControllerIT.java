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
public class RouteControllerIT {
    private final MockMvc mockMvc;
    private final ObjectMapper mapper;

    @Autowired
    public RouteControllerIT(MockMvc mockMvc, ObjectMapper mapper) {
        this.mockMvc = mockMvc;
        this.mapper = mapper;
    }

    @Test
    @Transactional
    void testPost_AllFieldsProvided() throws Exception {
        String jsonBody = """
                {
                    "name": "Test",
                    "numberOfPoints": 10,
                    "time": "2025-07-06"
                }
                """;
        mockMvc.perform(MockMvcRequestBuilders.post("/route/post")
                .contentType("application/json")
                .content(jsonBody))
            .andExpect(MockMvcResultMatchers.status().isCreated())
            .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Test"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.numberOfPoints").value(10))
            .andExpect(MockMvcResultMatchers.jsonPath("$.time").value("2025-07-06"));
    }

    @Test
    @Transactional
    void testPost_OnlyNecessaryFieldsProvided() throws Exception {

        String jsonBody = """
                {
                    "numberOfPoints": 10,
                    "time": "2025-07-06"
                }
                """;
        mockMvc.perform(MockMvcRequestBuilders.post("/route/post")
                        .contentType("application/json")
                        .content(jsonBody))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.numberOfPoints").value(10))
                .andExpect(MockMvcResultMatchers.jsonPath("$.time").value("2025-07-06"));
    }

    @Test
    void testPost_EmptyBody() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/route/post")
                .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void testPost_MissingFields() throws Exception {
        String jsonBody = """
                {
                    "name": "Test",
                    "time": "2025-07-06"
                }
                """;
        mockMvc.perform(MockMvcRequestBuilders.post("/route/post")
                        .contentType("application/json")
                        .content(jsonBody))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @Transactional
    void testGet_RoutePresentInDatabase() throws Exception {
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
        mockMvc.perform(MockMvcRequestBuilders.get("/route/".concat(route.getId().toString())))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Test"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.numberOfPoints").value(10))
                .andExpect(MockMvcResultMatchers.jsonPath("$.time").value("2025-07-06"));
    }

    @Test
    void testGet_RouteNotPresentInDatabase() throws Exception {
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
        mockMvc.perform(MockMvcRequestBuilders.delete("/route/delete/".concat(route.getId().toString())));
        mockMvc.perform(MockMvcRequestBuilders.get("/route/".concat(route.getId().toString())))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    void testDelete_RoutePresentInDatabase() throws Exception {
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
        mockMvc.perform(MockMvcRequestBuilders.delete("/route/delete/".concat(route.getId().toString())))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    void testDelete_RouteNotPresentInDatabase() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/route/delete/1"));
        mockMvc.perform(MockMvcRequestBuilders.delete("/route/delete/1"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}
