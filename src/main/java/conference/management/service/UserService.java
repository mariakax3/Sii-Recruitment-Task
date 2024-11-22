package conference.management.service;

import conference.management.mapper.LectureMapper;
import conference.management.mapper.UserMapper;
import conference.management.model.Lecture;
import conference.management.model.LectureRequest;
import conference.management.model.User;
import conference.management.repository.LectureRepository;
import conference.management.repository.UserRepository;
import conference.management.repository.entity.LectureEntity;
import conference.management.repository.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {

    private final NotificationService notificationService;
    private final UserRepository userRepository;
    private final LectureRepository lectureRepository;
    private final UserMapper userMapper;
    private final LectureMapper lectureMapper;

    public List<User> obtainRegisteredUsers() {
        return userRepository.findAll().stream()
            .filter(user -> !user.getLectures().isEmpty())
            .map(userMapper::toUser)
            .toList();
    }

    public List<User> obtainAllUsers() {
        return userRepository.findAll().stream()
            .map(userMapper::toUser)
            .toList();
    }

    public List<Lecture> obtainLecturesByUser(String login) {
        Optional<UserEntity> possibleUser = userRepository.findByLogin(login);
        if (possibleUser.isEmpty()) {
            throw new IllegalArgumentException("Invalid login provided.");
        }
        return possibleUser.get().getLectures().stream()
            .map(lectureMapper::toLecture)
            .toList();
    }

    @Transactional
    public User updateUser(String email, String newEmail) {
        Optional<UserEntity> possibleUserByEmail = userRepository.findByEmail(email);
        Optional<UserEntity> possibleUserByNewEmail = userRepository.findByEmail(newEmail);

        if (possibleUserByEmail.isEmpty()) {
            throw new IllegalArgumentException("Invalid email provided.");
        }
        if (possibleUserByNewEmail.isPresent()) {
            throw new IllegalArgumentException(String.format("Email %s is already in use.", newEmail));
        }

        UserEntity userEntity = possibleUserByEmail.get();
        userEntity.setEmail(newEmail);
        userRepository.save(userEntity);
        return userMapper.toUser(userEntity);
    }

    @Transactional
    public void registerForLecture(String login, LectureRequest lectureRequest) {
        UserEntity userEntity = findUser(login);
        LectureEntity lectureEntity = findLecture(lectureRequest.pathNumber(), lectureRequest.lectureNumber());

        boolean areThereCollidingLectures = !userEntity.getLectures().stream()
            .filter(lecture -> lecture.getDateTime().equals(lectureEntity.getDateTime()))
            .toList().isEmpty();
        if (areThereCollidingLectures) {
            throw new IllegalArgumentException("You are already enrolled for colliding lecture.");
        }

        Set<LectureEntity> lectures = userEntity.getLectures();
        lectures.add(lectureEntity);
        userEntity.setLectures(lectures);
        userRepository.save(userEntity);

        notificationService.sendNotification(userEntity, lectureEntity);
    }

    @Transactional
    public void cancelReservation(String login, LectureRequest lectureRequest) {
        UserEntity userEntity = findUser(login);
        LectureEntity lectureEntity = findLecture(lectureRequest.pathNumber(), lectureRequest.lectureNumber());

        Set<LectureEntity> lectures = userEntity.getLectures();
        lectures.remove(lectureEntity);
        userEntity.setLectures(lectures);
        userRepository.save(userEntity);
    }

    private LectureEntity findLecture(Integer pathNumber, Integer lectureNumber) {
        Optional<LectureEntity> possibleLecture = lectureRepository.findByPathNumberAndLectureNumber(pathNumber, lectureNumber);
        if (possibleLecture.isEmpty()) {
            throw new IllegalArgumentException("Invalid path number or lecture number provided.");
        }
        return possibleLecture.get();
    }

    private UserEntity findUser(String login) {
        Optional<UserEntity> possibleUserByLogin = userRepository.findByLogin(login);
        if (possibleUserByLogin.isEmpty()) {
            throw new IllegalArgumentException("Invalid login provided.");
        }
        return possibleUserByLogin.get();
    }
}
