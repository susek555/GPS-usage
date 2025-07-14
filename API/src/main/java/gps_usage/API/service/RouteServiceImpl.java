package gps_usage.API.service;

import gps_usage.API.dto.RouteCreateDTO;
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

    public RouteDTO postRoute(RouteCreateDTO createDTO) {
        if (!hasAllRequiredFields(createDTO)) {
            throw new RequiredFieldsMissingException();
        }
        Route routeToSave = mapper.createDTOToRoute(createDTO);
        Route savedRoute = repository.save(routeToSave);
        return mapper.routeToDTO(savedRoute);
    }

    // utils
    private boolean hasAllRequiredFields(RouteCreateDTO createDTO) {
        return createDTO.getNumberOfPoints() != null &&
                createDTO.getTime() != null;
    }
}
