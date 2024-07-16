package changebrowser;

import org.junit.rules.ExternalResource;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;

import static java.time.temporal.ChronoUnit.SECONDS;

public class Browser extends ExternalResource {

    private WebDriver webDriver;

    protected void after() {
        webDriver.quit();
    }

    public WebDriver getWebDriver(String browserName) {
        switch (browserName) {
            case "CHROME":
                return webDriver = new ChromeDriver();
            case "YANDEX":
                ChromeOptions options = new ChromeOptions();
                //options.addArguments("--headless");
                System.setProperty("webdriver.chrome.driver","src/test/resources/drivers/yandexdriver.exe");
                return webDriver = new ChromeDriver(options=options);
        }
        return null;
    }
}
