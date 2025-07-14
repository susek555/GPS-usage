package gps_usage.API.controller;

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

    @Autowired
    public RouteControllerIT(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
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
}
