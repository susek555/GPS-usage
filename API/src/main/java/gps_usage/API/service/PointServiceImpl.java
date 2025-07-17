package gps_usage.API.service;

import gps_usage.API.dto.PointCreateDTO;
import gps_usage.API.dto.RouteCreateDTO;
import gps_usage.API.dto.RouteDTO;
import gps_usage.API.exceptions.RequiredFieldsMissingException;
import gps_usage.API.mapper.PointMapper;
import gps_usage.API.mapper.RouteMapper;
import gps_usage.API.pojo.Point;
import gps_usage.API.pojo.Route;
import gps_usage.API.repository.PointRepository;
import gps_usage.API.repository.RouteRepository;

import java.util.List;

public class PointServiceImpl extends GenericServiceImpl<Point, Long> implements PointService{
    private final PointMapper mapper;

    public PointServiceImpl(PointRepository pointRepository, PointMapper pointMapper) {
        super(pointRepository);
        this.mapper = pointMapper;
    }

    @Override
    public void PostPoints(List<PointCreateDTO> points) {

    }

    //utils
//    public void postSinglePoint(RouteCreateDTO createDTO) {
//        if (!hasAllRequiredFields(createDTO)) {
//            throw new RequiredFieldsMissingException();
//        }
//        Route routeToSave = mapper.createDTOToRoute(createDTO);
//        Route savedRoute = repository.save(routeToSave);
//        return mapper.routeToDTO(savedRoute);
//    }
//
//    private boolean hasAllRequiredFields(PointCreateDTO createDTO) {
//        return createDTO.get() != null &&
//                createDTO.getTime() != null;
//    }
}
