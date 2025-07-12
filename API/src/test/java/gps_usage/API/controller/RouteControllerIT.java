package gps_usage.API.controller;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

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
                    "
                """;
    }
}
