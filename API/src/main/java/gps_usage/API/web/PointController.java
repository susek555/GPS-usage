package gps_usage.API.web;

import gps_usage.API.dto.PointCreateDTO;
import gps_usage.API.dto.PointDTO;
import gps_usage.API.pojo.Point;
import gps_usage.API.service.GenericService;
import gps_usage.API.service.PointService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/point")
public class PointController extends GenericController<Point, Long>{
    public PointController(GenericService<Point, Long> service) {
        super(service);
    }

    //post no more than 200 points per single request
    @PostMapping("/post/{routeId}")
    public ResponseEntity<Void> postPoints(@RequestBody List<PointCreateDTO> listCreateDTO, @PathVariable Long routeId) {
        ((PointService) service).postPoints(listCreateDTO, routeId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/get/{routeId}")
    public ResponseEntity<Page<PointDTO>> getPoints(
            @PathVariable Long routeId,
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam (defaultValue = "100") int pageSize) {
        return new ResponseEntity<>(((PointService) service).getPointsByRouteIdPaged(routeId, pageNumber, pageSize), HttpStatus.OK);
    }
}
