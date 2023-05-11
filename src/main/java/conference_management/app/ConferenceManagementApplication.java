package conference_management.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "pl.sii.conference_management.*")
public class ConferenceManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(conference_management.app.ConferenceManagementApplication.class, args);
    }
}
