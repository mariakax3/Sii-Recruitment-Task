package conference_management.controller;

import conference_management.model.LectureEntity;
import conference_management.model.UserEntity;
import conference_management.service.LectureService;
import conference_management.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@RestController
public class ConferenceController {

    @Autowired
    private UserService userService;

    @Autowired
    private LectureService lectureService;

    @GetMapping("/lectures")
    @Operation(summary = "Conference agenda")
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
    @Operation(summary = "Registered users")
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

    @GetMapping("/users")
    @Operation(summary = "All users in the system")
    public ResponseEntity<List<UserEntity>> getAllUsers() {
        try {
            List<UserEntity> users = userService.getAllUsers();

            if (users.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/lectures/{login}")
    @Operation(summary = "Conference agenda for particular user")
    public ResponseEntity getMyLectures(@PathVariable(value = "login") String login) {
        try {
            Set<LectureEntity> lectures = userService.getLectures(login);

            if (lectures.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(lectures);
        } catch (ResponseStatusException e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getReason());
        }
    }

    @GetMapping("/paths/interest")
    @Operation(summary = "Percentage of interest in the lectures")
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

    @GetMapping("/lectures/interest")
    @Operation(summary = "Percentage of interest in the thematic paths")
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

    @PostMapping("/users/{email}")
    @Operation(summary = "Update email for particular user")
    public ResponseEntity updateUser(@PathVariable("email") String email, String newEmail) {
        try {
            UserEntity updated = userService.updateUser(email, newEmail);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(updated);
        } catch (ResponseStatusException e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getReason());
        }
    }

    @PutMapping("/register/user")
    @Operation(summary = "Register particular user for selected lecture")
    public ResponseEntity registerForLecture(
            String login, String email, Integer pathNumber, Integer lectureNumber
    ) {
        try {
            log.info("### PATH: {} AND LECTURE: {} PASSED", pathNumber, lectureNumber);
            LectureEntity lecture = lectureService.findByPathAndLecture(pathNumber, lectureNumber);

            if (lecture.getUsers().size() >= lecture.getCapacity()) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "No seats available for selected lecture.");
            }

            Map<UserEntity, LectureEntity> registration = userService.registerForLecture(login, email, lecture);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(registration);

        } catch (ResponseStatusException e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getReason());
        }
    }

    @DeleteMapping("/lectures/cancel/{email}")
    @Operation(summary = "Cancel particular user's registration for a selected lecture")
    public ResponseEntity cancelReservation(@PathVariable(value = "email") String email, Integer pathNumber, Integer lectureNumber) {
        try {
            LectureEntity lecture = lectureService.findByPathAndLecture(pathNumber, lectureNumber);

            userService.cancelReservation(email, lecture);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body("Lecture %s successfully cancelled.".formatted(lecture.getTopic()));
        } catch (ResponseStatusException e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getReason());
        }
    }

    private List<UserEntity> getRegisteredUsersList() {
        return userService.getAllUsers().stream()
                .filter(user -> user.getLectures().size() > 0)
                .toList();
    }
}
