package conference.management.model;

import java.time.OffsetDateTime;
import java.util.Set;

public record Lecture(
		String topic,
		Integer pathNumber,
		Integer lectureNumber,
		OffsetDateTime dateTime,
		Integer capacity,
		Set<User> users
) {
}
