package conference_management.repository;

public interface UserRepositoryCustom {

    void registerForLecture(String login, String email, Integer pathNumber, Integer lectureNumber);

    void cancelReservation(String login, Integer pathNumber, Integer lectureNumber);
}
