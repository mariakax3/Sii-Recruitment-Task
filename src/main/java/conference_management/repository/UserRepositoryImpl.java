package conference_management.repository;

import conference_management.model.LectureEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepositoryCustom {

    @Override
    public void registerForLecture(String login, String email, Integer pathNumber, Integer lectureNumber) {

    }

    @Override
    public void cancelReservation(String login, Integer pathNumber, Integer lectureNumber) {

    }

    @Override
    public List<LectureEntity> getLectures(String login) {
        return null;
    }
}
