package ch.resrc.gamerm.dataextractiontwitch;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;

import java.time.Duration;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class RateLimiter {

    private final int maxRequestsPerMonth;
    private final Map<String, Bucket> cache = new ConcurrentHashMap<>();
    private final Set<String> apiKeys;

    public RateLimiter(int maxRequestsPerMonth, Set<String> apiKeys) {
        this.maxRequestsPerMonth = maxRequestsPerMonth;
        this.apiKeys = apiKeys;
    }

    public Bucket resolveBucket(String apiKey) {
        return cache.computeIfAbsent(apiKey, this::createBucket);
    }

    private Bucket createBucket(String apiKey) {
        return Bucket.builder()
                .addLimit(Bandwidth.simple(maxRequestsPerMonth, Duration.ofDays(30)))
                .build();
    }

    public boolean isValidApiKey(String apiKey) {
        if (apiKey == null || apiKey.isEmpty()) {
            return false;
        }
        return apiKeys.contains(apiKey);
    }

    public boolean invalidApiKey(String apiKey) {
        return !isValidApiKey(apiKey);
    }
}
