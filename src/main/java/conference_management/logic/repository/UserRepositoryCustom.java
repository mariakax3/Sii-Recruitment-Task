package conference_management.logic.repository;

import conference_management.logic.model.LectureEntity;

import java.util.List;

public interface UserRepositoryCustom {

    void updateUser(String email, String newEmail);

    void registerForLecture(String login, String email, Integer pathNumber, Integer lectureNumber);

    void cancelReservation(String login, Integer pathNumber, Integer lectureNumber);

    List<LectureEntity> getLectures(String login);
}
