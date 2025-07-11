package gps_usage.API.service;

import gps_usage.API.dto.RouteDTO;
import gps_usage.API.pojo.Route;

public interface RouteService extends GenericService<Route, Long> {
    RouteDTO postRoute(RouteDTO routeDTO);
}


