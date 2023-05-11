package conference_management.logic.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.Set;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "lectureId")
@ToString(of = {"lectureId", "thematicPathNumber", "dateTime"})
@Table(name = "lecture")
public class LectureEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lecture_id")
    private Integer lectureId;

    @Column(name = "thematic_path_number")
    private Integer thematicPathNumber;

    @Column(name = "date_time")
    private OffsetDateTime dateTime;

    @Column(name = "capacity")
    private Integer capacity;

    @ManyToMany(mappedBy = "lectures")
    private Set<UserEntity> users;
}
