package gps_usage.API.repository;

import gps_usage.API.pojo.Point;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface PointRepository extends JpaRepository<Point, Long>, JpaSpecificationExecutor<Point> {
    Page<Point> findByRouteId(Long routeId, Pageable pageable);
}
