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
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.StreamSupport;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public UserEntity updateUser(String email, String newEmail) {
        if (!validateEmail(newEmail)) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Invalid email provided.");
        }

        Optional<UserEntity> userByEmail = userRepository.findByEmail(email);
        Optional<UserEntity> userByNewEmail = userRepository.findByEmail(newEmail);
        log.info("### USER BY EMAIL {}: {}", email, userByEmail);
        log.info("### USER BY EMAIL {}: {}", newEmail, userByNewEmail);

        if (userByEmail.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Invalid email provided.");
        }

        if (userByNewEmail.isPresent()) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Email " + newEmail + " is already in use.");
        }

        UserEntity user = userByEmail.get();
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
        if (!validateEmail(email)) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Invalid email provided.");
        }

        Optional<UserEntity> userByLogin = userRepository.findByLogin(login);
        Optional<UserEntity> userByLoginAndEmail = userRepository.findByLoginAndEmail(login, email);

        log.info("### USER BY LOGIN {}: {}", login, userByLogin);
        log.info("### USER BY LOGIN {} AND EMAIL {}: {}", login, email, userByLoginAndEmail);

        if (userByLogin.isPresent() && userByLoginAndEmail.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Provided login is already in use.");
        }

        UserEntity user;

        if (userByLoginAndEmail.isPresent()) {
            user = userByLoginAndEmail.get();
            log.info("### USER {} FOUND", user);

            List<LectureEntity> collidingLectures = user.getLectures().stream()
                    .filter(entity -> entity.getDateTime().equals(lecture.getDateTime()))
                    .toList();

            if (collidingLectures.size() != 0) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "You are already enrolled for colliding lecture: " + lecture);
            }

        } else {
            user = UserEntity.builder()
                    .login(login)
                    .email(email)
                    .lectures(new HashSet<>())
                    .build();
            userRepository.save(user);
        }
        user.getLectures().add(lecture);
        userRepository.save(user);
        log.info("### USER {} SAVED", user);
        sendNotification(user, lecture);
        return Map.of(user, lecture);
    }

    @Override
    @Transactional
    public void cancelReservation(String email, LectureEntity lecture) {
        Optional<UserEntity> userByEmail = userRepository.findByEmail(email);
        log.info("### USERS BY EMAIL {}: {}", email, userByEmail);

        if (userByEmail.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Invalid e-mail provided.");
        }

        UserEntity user = userByEmail.get();
        if (!user.getLectures().contains(lecture)) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Invalid path number or lecture number provided.");
        }

        user.getLectures().remove(lecture);
        userRepository.save(user);
    }

    @Override
    public Set<LectureEntity> getLectures(String login) {
        Optional<UserEntity> userByLogin = userRepository.findByLogin(login);
        log.info("### USERS BY LOGIN {}: {}", login, userByLogin);

        if (userByLogin.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Invalid login provided.");
        }
        return userByLogin.get().getLectures();
    }

    private boolean validateEmail(String email) {
        Pattern VALID_EMAIL_ADDRESS_REGEX =
                Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
        return matcher.matches();
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
