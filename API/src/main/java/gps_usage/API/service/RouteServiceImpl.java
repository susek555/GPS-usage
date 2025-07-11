package gps_usage.API.service;

import gps_usage.API.dto.RouteDTO;
import gps_usage.API.exceptions.RequiredFieldsMissingException;
import gps_usage.API.mapper.RouteMapper;
import gps_usage.API.pojo.Route;
import gps_usage.API.repository.RouteRepository;
import org.springframework.stereotype.Service;

@Service
public class RouteServiceImpl extends GenericServiceImpl<Route, Long> implements RouteService {
    private final RouteMapper mapper;

    public RouteServiceImpl(RouteRepository routeRepository, RouteMapper routeMapper) {
        super(routeRepository);
        this.mapper = routeMapper;
    }

    public RouteDTO postRoute(RouteDTO routeDTO) {
        if (!hasAllRequiredFields(routeDTO)) {
            throw new RequiredFieldsMissingException();
        }
        Route savedRoute = repository.save(mapper.DTOToRoute(routeDTO));
        return mapper.routeToDTO(savedRoute);
    }

    // utils
    private boolean hasAllRequiredFields(RouteDTO routeDTO) {
        return routeDTO.getNumberOfPoints() != null &&
                routeDTO.getTime() != null;
    }
}
