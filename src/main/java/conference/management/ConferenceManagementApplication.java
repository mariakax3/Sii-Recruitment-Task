package conference.management;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Conference Management Service"))
public class ConferenceManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConferenceManagementApplication.class, args);
    }
}
