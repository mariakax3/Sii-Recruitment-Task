package conference_management.repository;

import conference_management.model.LectureEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LectureRepository extends CrudRepository<LectureEntity, Integer> {

    List<LectureEntity> findByPathNumber(Integer pathNumber);

    List<LectureEntity> findByPathNumberAndLectureNumber(Integer pathNumber, Integer lectureNumber);
}
