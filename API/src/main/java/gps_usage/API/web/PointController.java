package gps_usage.API.web;

import gps_usage.API.dto.PointCreateDTO;
import gps_usage.API.pojo.Point;
import gps_usage.API.service.GenericService;
import gps_usage.API.service.PointService;
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
    @PostMapping("/post/{id}")
    public ResponseEntity<Void> postPoint(@RequestBody List<PointCreateDTO> listCreateDTO, @PathVariable Long id) {
        ((PointService) service).postPoints(listCreateDTO, id);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
