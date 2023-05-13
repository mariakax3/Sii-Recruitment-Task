package conference_management.repository;

import conference_management.model.LectureEntity;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryImpl implements UserRepositoryCustom {

    @Override
    public void registerForLecture(String login, String email, LectureEntity lecture) {

    }

    @Override
    public void cancelReservation(String login, Integer pathNumber, Integer lectureNumber) {

    }
}
