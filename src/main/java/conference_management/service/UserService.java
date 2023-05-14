package conference_management.service;

import conference_management.model.LectureEntity;
import conference_management.model.UserEntity;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface UserService {

    UserEntity updateUser(String email, String newEmail);

    List<UserEntity> getAllUsers();

    Map<UserEntity, LectureEntity> registerForLecture(String login, String email, LectureEntity lecture);

    void cancelReservation(String email, LectureEntity lecture);

    Set<LectureEntity> getLectures(String login);
}
