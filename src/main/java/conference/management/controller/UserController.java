package conference.management.controller;

import conference.management.model.Lecture;
import conference.management.model.LectureRequest;
import conference.management.model.User;
import conference.management.service.LectureService;
import conference.management.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;
	private final LectureService lectureService;

	@GetMapping("/registered-users")
	@Operation(summary = "Registered users")
	public List<User> getRegisteredUsers() {
		return userService.obtainRegisteredUsers();
	}

	@GetMapping("/users")
	@Operation(summary = "All users in the system")
	public List<User> getAllUsers() {
		return userService.obtainAllUsers();
	}

	@GetMapping("/users/{login}/lectures")
	@Operation(summary = "Conference agenda for particular user")
	public List<Lecture> getLecturesByUser(@PathVariable("login") String login) {
		return userService.obtainLecturesByUser(login);
	}

	@PutMapping("/users/{email}")
	@Operation(summary = "Update email for particular user")
	public User updateUser(@PathVariable("email") String email, @RequestBody @Email String newEmail) {
		return userService.updateUser(email, newEmail);
	}

	@PostMapping("/user/{login}/lectures/register")
	@ResponseStatus(HttpStatus.CREATED)
	@Operation(summary = "Register particular user for selected lecture")
	public void registerForLecture(@PathVariable("login") String login, @RequestBody @Valid LectureRequest lectureRequest) {
		userService.registerForLecture(login, lectureRequest);
	}

	@DeleteMapping("/users/{login}/lectures/cancel")
	@ResponseStatus(HttpStatus.OK)
	@Operation(summary = "Cancel particular user's registration for a selected lecture")
	public void cancelReservation(@PathVariable("login") String login, @RequestBody @Valid LectureRequest lectureRequest) {
		userService.cancelReservation(login, lectureRequest);

	}
}
