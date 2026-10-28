package utilities;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public final class WaitUtils {
    private WaitUtils() {}
    public static WebDriverWait explicitWait(WebDriver driver) {
        return new WebDriverWait(driver, Duration.ofSeconds(Long.parseLong(ConfigReader.getProperty("explicit.wait.seconds", "15"))));
    }
    public static void sleep(long milliseconds) {
        try { Thread.sleep(milliseconds); } catch (InterruptedException e) { Thread.currentThread().interrupt(); throw new RuntimeException("Wait interrupted", e); }
    }
}
