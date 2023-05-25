package ch.resrc.gamerm.dataextractiontwitch;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Set;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Value("${gamerm.rate-limit.maxRequestsPerMonthAndApiKey}")
	private int maxRequestsPerMonth;

	@Value("${gamerm.rate-limit.apiKeys}")
	private Set<String> apiKeys;

	@Bean
	RateLimiter rateLimiter() {
		return new RateLimiter(maxRequestsPerMonth, apiKeys);
	}
}
