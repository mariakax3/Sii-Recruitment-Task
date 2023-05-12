package conference_management.service;

import conference_management.model.LectureEntity;
import conference_management.model.UserEntity;
import conference_management.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.StreamSupport;

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
        return StreamSupport.stream(userRepository.findAll().spliterator(), false).toList();
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
    public Set<LectureEntity> getLectures(String login) {
        UserEntity user = userRepository.findByLogin(login);
        return user.getLectures();
    }
}
