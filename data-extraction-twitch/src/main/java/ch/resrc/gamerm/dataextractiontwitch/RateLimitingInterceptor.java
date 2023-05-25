package ch.resrc.gamerm.dataextractiontwitch;

import io.github.bucket4j.ConsumptionProbe;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class RateLimitingInterceptor implements HandlerInterceptor {

    private final RateLimiter rateLimiter;


    public RateLimitingInterceptor(RateLimiter rateLimiter) {
        this.rateLimiter = rateLimiter;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) throws Exception {
        String apiKey = request.getHeader("X-API-KEY");
        if (!invalidApiKey(apiKey)) {
            response.sendError(HttpStatus.UNAUTHORIZED.value(), "Missing Header: X-API-KEY");
            return false;
        }

        ConsumptionProbe probe = rateLimiter.resolveBucket(apiKey).tryConsumeAndReturnRemaining(1);

        if (!probe.isConsumed()) {
            response.sendError(HttpStatus.TOO_MANY_REQUESTS.value(), "You have exhausted your API Request Quota");
            return false;
        }

        response.addHeader("X-Rate-Limit-Remaining", String.valueOf(probe.getRemainingTokens()));

        return true;
    }

    // TODO: check against set of valid api keys
    private boolean invalidApiKey(String apiKey) {
        if (apiKey == null || apiKey.isEmpty()) {
            return false;
        }
        return true;
    }
}
