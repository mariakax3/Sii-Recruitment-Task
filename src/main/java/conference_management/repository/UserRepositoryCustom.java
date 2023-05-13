package conference_management.repository;

import conference_management.model.LectureEntity;

public interface UserRepositoryCustom {

    void registerForLecture(String login, String email, LectureEntity lecture);

    void cancelReservation(String login, Integer pathNumber, Integer lectureNumber);
}
