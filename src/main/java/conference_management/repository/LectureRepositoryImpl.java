package conference_management.repository;

import conference_management.model.LectureEntity;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class LectureRepositoryImpl implements LectureRepositoryCustom {

    @Override
    public Map<LectureEntity, Float> getLectureInterest() {
        return null;
    }

    @Override
    public Map<Integer, Float> getPathInterest() {
        return null;
    }
}
