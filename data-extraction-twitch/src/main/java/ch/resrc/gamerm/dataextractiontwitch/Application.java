package ch.resrc.gamerm.dataextractiontwitch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	RateLimiter rateLimiter() {
		return new RateLimiter(2000);
	}
}
