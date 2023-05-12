package conference_management.service;

import conference_management.model.LectureEntity;
import conference_management.model.UserEntity;

import java.util.List;

public interface UserService {

    UserEntity createUser(String login, String email);

    void updateUser(String email, String newEmail);

    List<UserEntity> getAllUsers();

    void registerForLecture(String login, String email, Integer pathNumber, Integer lectureNumber);

    void cancelReservation(String login, Integer pathNumber, Integer lectureNumber);

    List<LectureEntity> getLectures(String login);
}
