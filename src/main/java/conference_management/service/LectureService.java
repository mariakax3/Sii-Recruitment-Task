package conference_management.service;

import conference_management.model.LectureEntity;

import java.util.List;
import java.util.Map;

public interface LectureService {

    List<LectureEntity> getConferenceAgenda();

    Map<LectureEntity, Float> getLectureInterest();

    Map<Integer, Float> getPathInterest();
}
