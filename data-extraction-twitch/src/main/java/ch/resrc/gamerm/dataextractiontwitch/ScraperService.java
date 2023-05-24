package ch.resrc.gamerm.dataextractiontwitch;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ScraperService {

    private final WebDriver driver;

    public ScraperService() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
    }

    public List<String> extractEmailFromTwitchInfoPage(String streamerId) {
        String channelInfoContent = retrieveChannelInfoContent(streamerId);

        if (channelInfoContent.isBlank()) {
            return List.of();
        }

        return extractEmailAddresses(channelInfoContent);
    }

    String retrieveChannelInfoContent(String streamerId) {
        try {
            driver.get("https://www.twitch.tv/" + streamerId + "/about");

            WebElement element = driver.findElement(By.cssSelector("#root div.channel-root__info"));

            return element.getAttribute("innerHTML");
        } catch (NoSuchElementException e) {
            return "";
        }
    }

    List<String> extractEmailAddresses(String channelInfoContent) {
        String regex = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(channelInfoContent);
        var emailAddresses = new ArrayList<String>();
        while (matcher.find()) {
            emailAddresses.add(matcher.group());
        }
        return emailAddresses;
    }


}
