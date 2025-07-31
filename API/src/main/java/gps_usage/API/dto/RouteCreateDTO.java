package gps_usage.API.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class RouteCreateDTO {
    private String name;
//    @JsonProperty("numberOfpoints")
    private Integer numberOfPoints;
    private LocalDate time;
}
