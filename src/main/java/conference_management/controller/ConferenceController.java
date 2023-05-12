package conference_management.controller;

import conference_management.model.LectureEntity;
import conference_management.service.LectureService;
import conference_management.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ConferenceController {

    @Autowired
    private UserService userService;

    @Autowired
    private LectureService lectureService;

    @GetMapping("/lectures")
    @ResponseBody
    public List<LectureEntity> getConferenceAgenda() {
        return lectureService.getConferenceAgenda();
    }
}
