package gps_usage.API.web;

import gps_usage.API.dto.RouteCreateDTO;
import gps_usage.API.dto.RouteDTO;
import gps_usage.API.pojo.Route;
import gps_usage.API.service.RouteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/route")
public class RouteController extends GenericController<Route, Long> {
    public RouteController(RouteService service) {
        super(service);
    }

    @PostMapping("/post")
    public ResponseEntity<RouteDTO> postRoute(@RequestBody RouteCreateDTO createDTO) {
        return new ResponseEntity<>(((RouteService) service).postRoute(createDTO), HttpStatus.CREATED);
    }
}
