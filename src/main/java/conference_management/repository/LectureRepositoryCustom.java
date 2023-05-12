package conference_management.repository;

import conference_management.model.LectureEntity;

import java.util.Map;

public interface LectureRepositoryCustom {

    Map<LectureEntity, Float> getLectureInterest();

    Map<Integer, Float> getPathInterest();
}
