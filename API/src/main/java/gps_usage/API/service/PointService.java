package gps_usage.API.service;

import gps_usage.API.dto.PointCreateDTO;
import gps_usage.API.pojo.Point;

import java.util.List;

public interface PointService extends GenericService<Point, Long>{
    void postPoints(List<PointCreateDTO> points, Long id);
}
