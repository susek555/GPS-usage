package gps_usage.API.mapper;

import gps_usage.API.dto.PointCreateDTO;
import gps_usage.API.dto.PointDTO;
import gps_usage.API.dto.PointDatabaseInsertDTO;
import gps_usage.API.dto.RouteCreateDTO;
import gps_usage.API.pojo.Point;
import gps_usage.API.pojo.Route;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PointMapper {
//    @Mapping(source = "route", target = "route")
//    @Mapping(source = "longitude", target = "longitude")
//    @Mapping(source = "latitude", target = "latitude")
//    @Mapping(source = "time", target = "time")
    Point databaseInsertDTOToPoint(PointDatabaseInsertDTO insertDTO);

//    @Mapping(source = "longitude", target = "longitude")
//    @Mapping(source = "latitude", target = "latitude")
//    @Mapping(source = "time", target = "time")
    PointDatabaseInsertDTO createDTOToDatabaseInsertDTO(PointCreateDTO createDTO);

    @Mapping(source = "route.id", target = "routeId")
//    @Mapping(source = "longitude", target = "longitude")
//    @Mapping(source = "latitude", target = "latitude")
//    @Mapping(source = "time", target = "time")
    PointDTO pointToDTO(Point point);
}
