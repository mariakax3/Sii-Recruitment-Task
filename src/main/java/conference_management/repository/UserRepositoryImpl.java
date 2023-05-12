package conference_management.repository;

import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryImpl implements UserRepositoryCustom {

    @Override
    public void registerForLecture(String login, String email, Integer pathNumber, Integer lectureNumber) {

    }

    @Override
    public void cancelReservation(String login, Integer pathNumber, Integer lectureNumber) {

    }
}
