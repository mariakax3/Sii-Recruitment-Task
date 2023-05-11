package conference_management.logic.service;

import conference_management.logic.model.LectureEntity;
import conference_management.logic.repository.LectureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class LectureServiceImpl implements LectureService {

    @Autowired
    private LectureRepository lectureRepository;

    @Override
    public List<LectureEntity> getConferenceAgenda() {
        return lectureRepository.getConferenceAgenda();
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
