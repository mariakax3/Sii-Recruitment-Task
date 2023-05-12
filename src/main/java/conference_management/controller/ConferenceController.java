package conference_management.controller;

import conference_management.model.LectureEntity;
import conference_management.model.UserEntity;
import conference_management.service.LectureService;
import conference_management.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
public class ConferenceController {

    @Autowired
    private UserService userService;

    @Autowired
    private LectureService lectureService;

    @GetMapping("/lectures")
    public ResponseEntity<List<LectureEntity>> getConferenceAgenda() {
        try {
            List<LectureEntity> lectures = lectureService.getConferenceAgenda();

            if (lectures.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(lectures, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/registered_users")
    public ResponseEntity<List<UserEntity>> getRegisteredUsers() {
        try {
            List<UserEntity> registeredUsers = getRegisteredUsersList();

            if (registeredUsers.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(registeredUsers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/lectures/{login}")
    public ResponseEntity<Set<LectureEntity>> getMyLectures(@PathVariable(value = "login") String login) {
        try {
            Set<LectureEntity> lectures = userService.getLectures(login);

            if (lectures.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(lectures, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/path_interest")
    public ResponseEntity<Map<Integer, Float>> getPathInterest() {
        try {
            int registeredUsers = getRegisteredUsersList().size();

            Map<Integer, Float> pathInterest = new HashMap<>();
            for(int i = 1; i <= 3; i++) {
                pathInterest.put(i, ((float) lectureService.getPathInterest(i) / registeredUsers) * 100);
            }

            return new ResponseEntity<>(pathInterest, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/lecture_interest")
    public ResponseEntity<Map<LectureEntity, Float>> getLectureInterest() {
        try {
            int registeredUsers = getRegisteredUsersList().size();

            Map<LectureEntity, Float> lectureInterest = lectureService.getConferenceAgenda().stream()
                    .collect(Collectors.toMap(
                            lecture -> lecture,
                            lecture -> (lecture.getUsers().size() / (float) registeredUsers) * 100
                    ));

            return new ResponseEntity<>(lectureInterest, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private List<UserEntity> getRegisteredUsersList() {
        return userService.getAllUsers().stream()
                .filter(user -> user.getLectures().size() > 0)
                .toList();
    }
}
