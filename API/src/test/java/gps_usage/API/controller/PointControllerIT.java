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
public class PointControllerIT {
    private final MockMvc mockMvc;
    private final ObjectMapper mapper;

    @Autowired
    public PointControllerIT(MockMvc mockMvc, ObjectMapper mapper) {
        this.mockMvc = mockMvc;
        this.mapper = mapper;
    }

    @Test
    @Transactional
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

}
