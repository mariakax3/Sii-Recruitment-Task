package conference.management.mapper;

import conference.management.model.User;
import conference.management.repository.entity.UserEntity;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface UserMapper {

	User toUser(UserEntity userEntity);
}
