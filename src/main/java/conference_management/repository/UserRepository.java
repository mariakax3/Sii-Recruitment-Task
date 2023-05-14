package conference_management.repository;

import conference_management.model.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Integer> {

    List<UserEntity> findByEmail(String email);

    List<UserEntity> findByLogin(String login);

    List<UserEntity> findByLoginAndEmail(String login, String email);
}
