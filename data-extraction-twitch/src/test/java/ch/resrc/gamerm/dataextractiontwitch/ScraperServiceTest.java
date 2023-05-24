package ch.resrc.gamerm.dataextractiontwitch;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ScraperServiceTest {

    @Test
    void shouldBeAbleToRetrieveChannelInfoContent() {
        var scraper = new ScraperService();
        var twitchStreamerId = "monadart";

        var infoPage = scraper.retrieveChannelInfoContent(twitchStreamerId);

        assertFalse(infoPage.isBlank());
    }


    @Test
    void shouldBeAbleToExtractEmailAddresses() {
        var scraper = new ScraperService();
        var channelInfoContent = "<p>Business inquiries should be forwarded towards monadart87@gmail.com .</p>";

        var emailAddresses = scraper.extractEmailAddresses(channelInfoContent);

        assertEquals(1, emailAddresses.size());
        assertEquals("monadart87@gmail.com", emailAddresses.get(0));
    }

    @Test
   void shouldBeAbleToExtractEmailFromTwitchInfoPage() {

        var scraper = new ScraperService();
        var twitchStreamerId = "monadart";

        var emailAddresses = scraper.extractEmailFromTwitchInfoPage(twitchStreamerId);

        assertEquals(1, emailAddresses.size());
        assertEquals("monadart87@gmail.com", emailAddresses.get(0));
    }
}
