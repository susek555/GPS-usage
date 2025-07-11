package gps_usage.API.repository;

import gps_usage.API.pojo.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PointRepository extends JpaRepository<Point, Long>, JpaSpecificationExecutor<Point> {

}
