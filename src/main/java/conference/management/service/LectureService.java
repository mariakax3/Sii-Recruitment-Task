package conference.management.service;

import conference.management.mapper.LectureMapper;
import conference.management.model.Lecture;
import conference.management.repository.LectureRepository;
import conference.management.repository.entity.LectureEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LectureService {

    private final UserService userService;
    private final LectureRepository lectureRepository;
    private final LectureMapper lectureMapper;

    public List<Lecture> getConferenceAgenda() {
        return lectureRepository.findAll().stream()
            .map(lectureMapper::toLecture)
            .toList();
    }

    public Map<Integer, Float> getPathInterest() {
        return lectureRepository.findAll().stream()
            .collect(Collectors.toMap(LectureEntity::getPathNumber, lecture -> calculateInterest(lecture.getUsers().size())));
    }

    public Map<Lecture, Float> getLectureInterest() {
        return lectureRepository.findAll().stream()
            .map(lectureMapper::toLecture)
            .collect(Collectors.toMap(Function.identity(), lecture -> calculateInterest(lecture.users().size())));
    }

    private float calculateInterest(int registeredUsers) {
        int totalRegisteredUsers = userService.obtainRegisteredUsers().size();
        return (registeredUsers / (float) totalRegisteredUsers) * 100;
    }
}
