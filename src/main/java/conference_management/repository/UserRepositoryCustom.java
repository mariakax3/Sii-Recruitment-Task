package conference_management.repository;

import conference_management.model.LectureEntity;

import java.util.List;

public interface UserRepositoryCustom {

    void registerForLecture(String login, String email, Integer pathNumber, Integer lectureNumber);

    void cancelReservation(String login, Integer pathNumber, Integer lectureNumber);

    List<LectureEntity> getLectures(String login);
}
