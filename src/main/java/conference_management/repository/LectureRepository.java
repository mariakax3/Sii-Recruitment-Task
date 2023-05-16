package conference_management.repository;

import conference_management.model.LectureEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LectureRepository extends CrudRepository<LectureEntity, Integer> {

    List<LectureEntity> findByPathNumber(Integer pathNumber);

    Optional<LectureEntity> findByPathNumberAndLectureNumber(Integer pathNumber, Integer lectureNumber);
}
