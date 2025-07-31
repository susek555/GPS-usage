package gps_usage.API.pojo;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;


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
    @Column(name = "numberofpoints")
    private Integer numberOfPoints;

    @NonNull
    @Column(name = "time")
    private LocalDate time;

    public Route(
            String name,
            @NonNull Integer numberOfPoints,
            LocalDate time
    ) {
        this.name = name;
        this.numberOfPoints = numberOfPoints;
        this.time = time;
    }
}
