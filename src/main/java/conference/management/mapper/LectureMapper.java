package conference.management.mapper;

import conference.management.model.Lecture;
import conference.management.repository.entity.LectureEntity;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface LectureMapper {

	Lecture toLecture(LectureEntity lectureEntity);
}
