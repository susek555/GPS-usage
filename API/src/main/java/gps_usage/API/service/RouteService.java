package gps_usage.API.service;

import gps_usage.API.dto.RouteCreateDTO;
import gps_usage.API.dto.RouteDTO;
import gps_usage.API.pojo.Route;
import org.springframework.data.domain.Page;

public interface RouteService extends GenericService<Route, Long> {
    RouteDTO postRoute(RouteCreateDTO createDTO);
    Page<RouteDTO> getAllRoutesPaged(int pageNumber, int pageSize);
}


