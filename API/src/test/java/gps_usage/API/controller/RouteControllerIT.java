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

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.isA;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
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

    @Test
    void testGetAll_EmptyDatabase() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/route/get/all"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isEmpty());
    }

    @Test
    void testGetAll_SingleRoute() throws Exception {
        String jsonBody = """
                {
                    "name": "Test",
                    "numberOfPoints": 10,
                    "time": "2025-07-06"
                }
                """;
        mockMvc.perform(MockMvcRequestBuilders.post("/route/post")
                .contentType("application/json")
                .content(jsonBody));
        mockMvc.perform(MockMvcRequestBuilders.get("/route/get/all"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].id", isA(Number.class)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].name").value("Test"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].numberOfPoints").value(10))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].time").value("2025-07-06"));
    }

    @Test
    void testGetAll_MultipleRoutes() throws Exception {
        String jsonBody1 = """
                {
                    "name": "Test1",
                    "numberOfPoints": 11,
                    "time": "2025-07-07"
                }
                """;
        mockMvc.perform(MockMvcRequestBuilders.post("/route/post")
                .contentType("application/json")
                .content(jsonBody1));
        String jsonBody2 = """
                {
                    "name": "Test2",
                    "numberOfPoints": 12,
                    "time": "2025-07-08"
                }
                """;
        mockMvc.perform(MockMvcRequestBuilders.post("/route/post")
                .contentType("application/json")
                .content(jsonBody2));
        String jsonBody3 = """
                {
                    "name": "Test3",
                    "numberOfPoints": 13,
                    "time": "2025-07-09"
                }
                """;
        mockMvc.perform(MockMvcRequestBuilders.post("/route/post")
                .contentType("application/json")
                .content(jsonBody3));
        mockMvc.perform(MockMvcRequestBuilders.get("/route/get/all"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].id", isA(Number.class)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].name").value("Test1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].numberOfPoints").value(11))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].time").value("2025-07-07"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[1].id", isA(Number.class)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[1].name").value("Test2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[1].numberOfPoints").value(12))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[1].time").value("2025-07-08"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[2].id", isA(Number.class)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[2].name").value("Test3"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[2].numberOfPoints").value(13))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[2].time").value("2025-07-09"));
    }

    @Test
    void testGetAll_Pages() throws Exception {
        String jsonBody1 = """
                {
                    "name": "Test1",
                    "numberOfPoints": 11,
                    "time": "2025-07-07"
                }
                """;
        mockMvc.perform(MockMvcRequestBuilders.post("/route/post")
                .contentType("application/json")
                .content(jsonBody1));
        String jsonBody2 = """
                {
                    "name": "Test2",
                    "numberOfPoints": 12,
                    "time": "2025-07-08"
                }
                """;
        mockMvc.perform(MockMvcRequestBuilders.post("/route/post")
                .contentType("application/json")
                .content(jsonBody2));
        String jsonBody3 = """
                {
                    "name": "Test3",
                    "numberOfPoints": 13,
                    "time": "2025-07-09"
                }
                """;
        mockMvc.perform(MockMvcRequestBuilders.post("/route/post")
                .contentType("application/json")
                .content(jsonBody3));
        mockMvc.perform(MockMvcRequestBuilders.get("/route/get/all")
                        .param("pageNumber", "0")
                        .param("pageSize", "2"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].id", isA(Number.class)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].name").value("Test1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].numberOfPoints").value(11))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].time").value("2025-07-07"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[1].id", isA(Number.class)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[1].name").value("Test2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[1].numberOfPoints").value(12))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[1].time").value("2025-07-08"));
        mockMvc.perform(MockMvcRequestBuilders.get("/route/get/all")
                        .param("pageNumber", "1")
                        .param("pageSize", "2"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].id", isA(Number.class)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].name").value("Test3"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].numberOfPoints").value(13))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].time").value("2025-07-09"));
    }
}
