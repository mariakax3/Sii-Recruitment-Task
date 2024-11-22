package conference.management.service;

import conference.management.repository.entity.LectureEntity;
import conference.management.repository.entity.UserEntity;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class NotificationService {

	private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd', 'HH:mm:ss");

	void sendNotification(UserEntity user, LectureEntity lecture) {
		String filename = "src/main/resources/notifications.txt";
		File notificationsFile = new File(filename);
		try {
			notificationsFile.createNewFile();
		} catch (IOException e) {
			System.err.println("### Error creating" + notificationsFile + "file.");
		}

		String notification = String.format("to: %s; timestamp: %s; content: " +
						"You are successfully enrolled for lecture %s, date and time: %s",
				user.getEmail(), formatter.format(OffsetDateTime.now()), lecture.getTopic(), formatter.format(lecture.getDateTime()));

		try (BufferedWriter writer = Files.newBufferedWriter(Path.of(filename), StandardCharsets.UTF_8, StandardOpenOption.APPEND)) {
			writer.write(notification);
			writer.newLine();
			writer.flush();
		} catch (IOException e) {
			System.err.println("### Error writing to" + notificationsFile + "file.");
		}
	}
}
