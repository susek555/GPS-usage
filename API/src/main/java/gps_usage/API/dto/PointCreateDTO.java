package gps_usage.API.dto;

import gps_usage.API.pojo.Route;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PointCreateDTO {
    private Long routeId;
    private Double latitude;
    private Double longitude;
    private LocalDateTime time;
}
