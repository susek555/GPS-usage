package gps_usage.API.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class RouteDTO {
    private Long id;
    private String name;
    private Integer numberOfPoints;
    private LocalDate time;
}
