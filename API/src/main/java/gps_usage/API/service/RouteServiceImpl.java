package gps_usage.API.service;

import gps_usage.API.dto.RouteDTO;
import gps_usage.API.pojo.Route;
import gps_usage.API.repository.RouteRepository;
import org.springframework.stereotype.Service;

@Service
public class RouteServiceImpl extends GenericServiceImpl<Route, Long> implements RouteService {
//    private final RouteRepository routeRepository;

    public RouteServiceImpl(RouteRepository routeRepository) {
        super(routeRepository);
//        this.routeRepository = routeRepository;
    }

    public RouteDTO postRoute(RouteDTO routeDTO) {
        return null;
    }

    private boolean hasAllRequiredFields(RouteDTO routeDTO) {
        return routeDTO.getNumberOfPoints() != null &&
                routeDTO.getTime() != null;
    }
}
