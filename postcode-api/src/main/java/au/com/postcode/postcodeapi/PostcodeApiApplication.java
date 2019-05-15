package au.com.postcode.postcodeapi;

import io.prometheus.client.spring.boot.EnablePrometheusEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

@SpringBootApplication
@Slf4j
@EnablePrometheusEndpoint
@SuppressWarnings("checkstyle:hideutilityclassconstructor")
public class PostcodeApiApplication {

    public static void main(final String[] args) {
        final SpringApplication app = new SpringApplication(PostcodeApiApplication.class);
        final Environment env = app.run(args).getEnvironment();
        logConfiguration(env);
    }

    private static void logConfiguration(final Environment env) {
        log.info("#################################################################");

        final String[] profiles = env.getActiveProfiles();
        if (profiles.length == 0) {
            log.info("No profile configured - using default profile");
        } else {
            for (final String profile : profiles) {
                log.info("Active profile: {}", profile);
            }
        }
        log.info("#################################################################");
    }


}
