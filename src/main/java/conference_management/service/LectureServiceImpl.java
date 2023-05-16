package conference_management.service;

import conference_management.model.LectureEntity;
import conference_management.repository.LectureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Slf4j
@Service
public class LectureServiceImpl implements LectureService {

    @Autowired
    private LectureRepository lectureRepository;

    @Override
    public List<LectureEntity> getConferenceAgenda() {
        return StreamSupport.stream(lectureRepository.findAll().spliterator(), false).toList();
    }

    @Override
    public Integer getPathInterest(Integer path) {
        return lectureRepository.findByPathNumber(path).stream()
                .mapToInt(lecture -> lecture.getUsers().size())
                .sum();
    }

    @Override
    public LectureEntity findByPathAndLecture(Integer pathNumber, Integer lectureNumber) {
        Optional<LectureEntity> lecture = lectureRepository.findByPathNumberAndLectureNumber(pathNumber, lectureNumber);
        log.info("### LECTURES BY PATH {} AND LECTURE {}: {}", pathNumber, lectureNumber, lecture);

        if (lecture.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Invalid path number or lecture number provided.");
        }
        return lecture.get();
    }
}
