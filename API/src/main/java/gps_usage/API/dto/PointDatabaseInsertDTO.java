package gps_usage.API.dto;

import gps_usage.API.pojo.Route;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PointDatabaseInsertDTO {
    private Route route;
    private Double latitude;
    private Double longitude;
    private Integer time;
}
