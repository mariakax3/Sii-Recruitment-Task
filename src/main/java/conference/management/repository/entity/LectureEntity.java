package conference.management.repository.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.OffsetDateTime;
import java.util.Set;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "lectureId")
@ToString(of = {"lectureId", "pathNumber", "lectureNumber", "topic", "dateTime"})
@Table(name = "lecture")
public class LectureEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lecture_id")
    private Integer lectureId;

    @Column(name = "topic")
    private String topic;

    @Column(name = "path_number")
    private Integer pathNumber;

    @Column(name = "lecture_number")
    private Integer lectureNumber;

    @Column(name = "date_time")
    private OffsetDateTime dateTime;

    @Column(name = "capacity")
    private Integer capacity;

    @ManyToMany(mappedBy = "lectures")
    @JsonIgnoreProperties("lectures")
    private Set<UserEntity> users;
}
