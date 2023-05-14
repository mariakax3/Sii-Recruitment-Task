package conference_management.service;

import conference_management.model.LectureEntity;
import conference_management.model.UserEntity;
import conference_management.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.StreamSupport;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public UserEntity updateUser(String email, String newEmail) {
        List<UserEntity> users = userRepository.findByEmail(email);
        log.info("### USERS BY EMAIL {}: {}", email, users);

        if (users.size() == 0) {
            throw new RuntimeException("Invalid email provided.");
        }
        UserEntity user = users.get(0);
        user.setEmail(newEmail);
        return userRepository.save(user);
    }

    @Override
    public List<UserEntity> getAllUsers() {
        return StreamSupport.stream(userRepository.findAll().spliterator(), false).toList();
    }

    @Override
    @Transactional
    public Map<UserEntity, LectureEntity> registerForLecture(String login, String email, LectureEntity lecture) {
        List<UserEntity> usersByLogin = userRepository.findByLogin(login);
        List<UserEntity> usersByLoginAndEmail = userRepository.findByLoginAndEmail(login, email);

        log.info("### USERS BY LOGIN {}: {}", login, usersByLogin);
        log.info("### USERS BY LOGIN {} AND EMAIL {}: {}", login, email, usersByLoginAndEmail);

        if (usersByLogin.size() != usersByLoginAndEmail.size()) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Provided login is already in use.");
        }

        if (usersByLoginAndEmail.size() == 1) {
            UserEntity user = usersByLoginAndEmail.get(0);

            List<LectureEntity> collidingLectures = user.getLectures().stream()
                    .filter(entity -> entity.getDateTime().equals(lecture.getDateTime()))
                    .toList();

            if (collidingLectures.size() != 0) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "You are already enrolled for colliding lecture: " + lecture);
            } else {
                user.getLectures().add(lecture);
                userRepository.save(user);

                sendNotification(user, lecture);

                return Map.of(user, lecture);
            }
        }
        return null;
    }

    @Override
    @Transactional
    public void cancelReservation(String email, LectureEntity lecture) {
        List<UserEntity> users = userRepository.findByEmail(email);
        log.info("### USERS BY EMAIL {}: {}", email, users);

        UserEntity user = users.get(0);
        user.getLectures().remove(lecture);
        userRepository.save(user);
    }

    @Override
    public Set<LectureEntity> getLectures(String login) {
        List<UserEntity> users = userRepository.findByLogin(login);
        log.info("### USERS BY LOGIN {}: {}", login, users);

        if (users.size() == 0) {
            throw new RuntimeException("Invalid login provided.");
        }
        return users.get(0).getLectures();
    }

    private void sendNotification(UserEntity user, LectureEntity lecture) {
        final String filename = "src/main/resources/notifications.txt";
        File notificationsFile = new File(filename);
        try {
            notificationsFile.createNewFile();
        } catch (IOException e) {
            log.error("### Error creating {} file.", notificationsFile);
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd', 'HH:mm:ss");
        String notification = String.format("to: %s; timestamp: %s; content: " +
                        "You are successfully enrolled for lecture %s, date and time: %s",
                user.getEmail(), formatter.format(OffsetDateTime.now()), lecture.getTopic(), formatter.format(lecture.getDateTime()));

        try (BufferedWriter writer = Files.newBufferedWriter(Path.of(filename), StandardCharsets.UTF_8, StandardOpenOption.APPEND)) {
            writer.write(notification);
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            log.error("### Error writing to {} file.", notificationsFile);
        }
    }
}
