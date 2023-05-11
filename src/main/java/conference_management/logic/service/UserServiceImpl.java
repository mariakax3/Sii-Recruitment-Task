package conference_management.logic.service;

import conference_management.logic.model.LectureEntity;
import conference_management.logic.model.UserEntity;
import conference_management.logic.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserEntity createUser(String login, String email) {
        return userRepository.save(UserEntity.builder().login(login).email(email).build());
    }

    @Override
    public void updateUser(String email, String newEmail) {
        userRepository.updateUser(email, newEmail);
    }

    @Override
    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void registerForLecture(String login, String email, Integer pathNumber, Integer lectureNumber) {
        userRepository.registerForLecture(login, email, pathNumber, lectureNumber);
    }

    @Override
    public void cancelReservation(String login, Integer pathNumber, Integer lectureNumber) {
        userRepository.cancelReservation(login, pathNumber, lectureNumber);
    }

    @Override
    public List<LectureEntity> getLectures(String login) {
        return userRepository.getLectures(login);
    }
}
