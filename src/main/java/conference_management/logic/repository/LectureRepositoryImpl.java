package conference_management.logic.repository;

import conference_management.logic.model.LectureEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class LectureRepositoryImpl implements LectureRepositoryCustom {
    @Override
    public List<LectureEntity> getConferenceAgenda() {
        return null;
    }

    @Override
    public Map<LectureEntity, Float> getLectureInterest() {
        return null;
    }

    @Override
    public Map<Integer, Float> getPathInterest() {
        return null;
    }
}
