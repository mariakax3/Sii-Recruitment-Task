package conference_management.rest;

import conference_management.logic.service.LectureService;
import conference_management.logic.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConferenceController {

    @Autowired
    private UserService userService;

    @Autowired
    private LectureService lectureService;
}
