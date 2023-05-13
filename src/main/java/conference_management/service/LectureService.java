package conference_management.service;

import conference_management.model.LectureEntity;

import java.util.List;

public interface LectureService {

    List<LectureEntity> getConferenceAgenda();

    Integer getPathInterest(Integer path);

    LectureEntity findByPathAndLecture(Integer pathNumber, Integer lectureNumber);
}
