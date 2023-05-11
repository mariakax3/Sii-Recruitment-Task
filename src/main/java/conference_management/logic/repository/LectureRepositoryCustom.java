package conference_management.logic.repository;

import conference_management.logic.model.LectureEntity;

import java.util.List;
import java.util.Map;

public interface LectureRepositoryCustom {

    List<LectureEntity> getConferenceAgenda();

    Map<LectureEntity, Float> getLectureInterest();

    Map<Integer, Float> getPathInterest();
}
