package conference.management.repository;

import conference.management.repository.entity.LectureEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LectureRepository extends JpaRepository<LectureEntity, Integer> {

    Optional<LectureEntity> findByPathNumberAndLectureNumber(Integer pathNumber, Integer lectureNumber);
}
