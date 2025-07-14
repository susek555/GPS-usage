package gps_usage.API.mapper;

import gps_usage.API.dto.RouteCreateDTO;
import gps_usage.API.dto.RouteDTO;
import gps_usage.API.pojo.Route;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RouteMapper {
    @Mapping(source = "name", target = "name")
    @Mapping(source = "numberOfPoints", target = "numberOfPoints")
    @Mapping(source = "time", target = "time")
    Route createDTOToRoute(RouteCreateDTO createDTO);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "numberOfPoints", target = "numberOfPoints")
    @Mapping(source = "time", target = "time")
    Route DTOToRoute(RouteDTO dto);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "numberOfPoints", target = "numberOfPoints")
    @Mapping(source = "time", target = "time")
    RouteDTO routeToDTO(Route route);
}
