package conference.management.controller;

import conference.management.model.Lecture;
import conference.management.service.LectureService;
import conference.management.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class LectureController {

    private final UserService userService;
    private final LectureService lectureService;

    @GetMapping("/lectures")
    @Operation(summary = "Conference agenda")
    public List<Lecture> getConferenceAgenda() {
        return lectureService.getConferenceAgenda();
    }

    @GetMapping("/paths/interest")
    @Operation(summary = "Percentage of interest in the lectures")
    public Map<Integer, Float> getPathInterest() {
        return lectureService.getPathInterest();
    }

    @GetMapping("/lectures/interest")
    @Operation(summary = "Percentage of interest in the thematic paths")
    public Map<Lecture, Float> getLectureInterest() {
        return lectureService.getLectureInterest();
    }
}
