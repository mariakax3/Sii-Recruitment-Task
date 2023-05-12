package conference_management.service;

import conference_management.model.LectureEntity;
import conference_management.repository.LectureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
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
    public Map<LectureEntity, Float> getLectureInterest() {
        return lectureRepository.getLectureInterest();
    }

    @Override
    public Map<Integer, Float> getPathInterest() {
        return lectureRepository.getPathInterest();
    }
}
