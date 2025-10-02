package gps_usage.API.service;

import gps_usage.API.dto.RouteCreateDTO;
import gps_usage.API.dto.RouteDTO;
import gps_usage.API.exceptions.RequiredFieldsMissingException;
import gps_usage.API.mapper.RouteMapper;
import gps_usage.API.pojo.Route;
import gps_usage.API.repository.RouteRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
        Route savedRoute = repository.save(mapper.createDTOToRoute(createDTO));
        return mapper.routeToDTO(savedRoute);
    }

    @Override
    public Page<RouteDTO> getAllRoutesPaged(int pageNumber, int pageSize) {
        Pageable pageable = (Pageable) PageRequest.of(pageNumber, pageSize, Sort.by("id").ascending());
        return ((RouteRepository) repository).findAll(pageable).map(mapper::routeToDTO);
    }


    // utils
    private boolean hasAllRequiredFields(RouteCreateDTO createDTO) {
        return createDTO.getNumberOfPoints() != null &&
                createDTO.getTime() != null;
    }
}
