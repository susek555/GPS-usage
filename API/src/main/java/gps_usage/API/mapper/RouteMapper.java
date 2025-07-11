package gps_usage.API.mapper;

import gps_usage.API.dto.RouteDTO;
import gps_usage.API.pojo.Route;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RouteMapper {
    Route DTOToRoute(RouteDTO dto);
    RouteDTO routeToDTO(Route route);
}
