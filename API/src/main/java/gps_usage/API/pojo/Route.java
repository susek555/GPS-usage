package gps_usage.API.pojo;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "routes")
public class Route {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @NonNull
    @Column(name = "numberOfPoints")
    private Long numberOfPoints;

    @Column(name = "duration")
    private Long duration;

    public Route(String name, @NonNull Long numberOfPoints, Long duration) {
        this.name = name;
        this.numberOfPoints = numberOfPoints;
        this.duration = duration;
    }
}
