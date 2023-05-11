package conference_management.logic.repository;

import conference_management.logic.model.LectureEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public void updateUser(String email, String newEmail) {

    }

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
