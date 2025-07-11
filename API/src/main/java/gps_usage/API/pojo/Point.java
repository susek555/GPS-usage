package gps_usage.API.pojo;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "points")
public class Point {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NonNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "routeId", referencedColumnName = "id")
    private Long routeId;

    @NonNull
    @Column(name = "latitude")
    private Double latitude;

    @NonNull
    @Column(name = "longitude")
    private Double longitude;

    @NonNull
    @Column(name = "time")
    private LocalDate time;

    public Point(
            @NonNull Long routeId,
            @NonNull Double latitude,
            @NonNull Double longitude,
            @NonNull LocalDate time
    ) {
        this.routeId = routeId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.time = time;
    }
}
