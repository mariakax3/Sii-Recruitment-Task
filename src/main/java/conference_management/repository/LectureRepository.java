package conference_management.repository;

import conference_management.model.LectureEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LectureRepository extends CrudRepository<LectureEntity, Integer>, LectureRepositoryCustom {

}
