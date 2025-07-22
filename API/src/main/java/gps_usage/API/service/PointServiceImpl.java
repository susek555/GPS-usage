package gps_usage.API.service;

import gps_usage.API.dto.PointCreateDTO;
import gps_usage.API.dto.PointDTO;
import gps_usage.API.dto.PointDatabaseInsertDTO;
import gps_usage.API.exceptions.RequiredFieldsMissingException;
import gps_usage.API.mapper.PointMapper;
import gps_usage.API.pojo.Point;
import gps_usage.API.pojo.Route;
import gps_usage.API.repository.PointRepository;
import gps_usage.API.repository.RouteRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PointServiceImpl extends GenericServiceImpl<Point, Long> implements PointService{
    private final PointMapper mapper;
    private final RouteRepository routeRepository;

    public PointServiceImpl(
            PointRepository pointRepository,
            RouteRepository routeRepository,
            PointMapper pointMapper) {
        super(pointRepository);
        this.routeRepository = routeRepository;
        this.mapper = pointMapper;
    }

    @Override
    public void postPoints(List<PointCreateDTO> points, Long id) {
        Route route = routeRepository.getReferenceById(id);
        List<PointDatabaseInsertDTO> pointsToInsert = points.stream()
            .map( point -> {
                PointDatabaseInsertDTO dto = mapper.createDTOToDatabaseInsertDTO(point);
                dto.setRoute(route);
                return dto;
            })
            .toList();
        for(PointDatabaseInsertDTO point : pointsToInsert) {
            postSinglePoint(point);
        }
    }

    @Override
    public Page<PointDTO> getPointsByRouteIdPaged(Long routeId, int pageNumber, int pageSize) {
        Pageable pageable = (Pageable) PageRequest.of(pageNumber, pageSize, Sort.by("time").ascending());
        return ((PointRepository) repository).findByRouteId(routeId, pageable).map(mapper::pointToDTO);
    }

    //utils
    public void postSinglePoint(PointDatabaseInsertDTO insertDTO) {
        if (!hasAllRequiredFields(insertDTO)) {
            throw new RequiredFieldsMissingException();
        }
        repository.save(mapper.databaseInsertDTOToPoint(insertDTO));
    }

    private boolean hasAllRequiredFields(PointDatabaseInsertDTO createDTO) {
        return createDTO.getLongitude() != null &&
                createDTO.getLatitude() != null &&
                createDTO.getTime() != null;
    }
}
