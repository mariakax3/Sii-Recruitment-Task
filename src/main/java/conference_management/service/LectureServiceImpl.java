package conference_management.service;

import conference_management.model.LectureEntity;
import conference_management.repository.LectureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.StreamSupport;

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
}
