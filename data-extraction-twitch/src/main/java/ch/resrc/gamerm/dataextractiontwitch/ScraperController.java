package ch.resrc.gamerm.dataextractiontwitch;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ScraperController {
    private final ScraperService scraperService;

    public ScraperController(ScraperService twitchService) {
        this.scraperService = twitchService;
    }

    @GetMapping("/twitch/{streamerId}")
    public List<String> getEmail(@PathVariable String streamerId) {
        return scraperService.extractEmailFromTwitchInfoPage(streamerId);
    }
}
