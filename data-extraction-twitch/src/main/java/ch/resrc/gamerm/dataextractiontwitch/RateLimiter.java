package ch.resrc.gamerm.dataextractiontwitch;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RateLimiter {

    private final int maxRequestsPerMonth;
    private final Map<String, Bucket> cache = new ConcurrentHashMap<>();

    public RateLimiter(int maxRequestsPerMonth) {
        this.maxRequestsPerMonth = maxRequestsPerMonth;
    }

    public Bucket resolveBucket(String apiKey) {
        return cache.computeIfAbsent(apiKey, this::createBucket);
    }

    private Bucket createBucket(String apiKey) {
        return Bucket.builder()
                .addLimit(Bandwidth.simple(maxRequestsPerMonth, Duration.ofDays(30)))
                .build();
    }
}
