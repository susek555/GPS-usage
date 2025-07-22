package gps_usage.API.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PointCreateDTO {
    private Double latitude;
    private Double longitude;
    private Integer time;
}
