package conference_management.repository;

import conference_management.model.UserEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Integer>, UserRepositoryCustom {

    @Modifying
    @Query("update UserEntity u set u.email = :newEmail where u.email = :email")
    void updateUser(String email, String newEmail);

    UserEntity findByLogin(String login);
}
