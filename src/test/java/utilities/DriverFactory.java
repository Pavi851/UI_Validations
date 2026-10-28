package utilities;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import io.github.bonigarcia.wdm.WebDriverManager;

public final class DriverFactory {
    private static WebDriver driver;
    private DriverFactory() {}
    public static synchronized WebDriver getDriver() {
        if (driver == null) {
            String browser = ConfigReader.getProperty("browser", "chrome").trim().toLowerCase();
            if (!browser.equals("chrome")) throw new IllegalArgumentException("This project currently supports Chrome only. Configure browser=chrome.");
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--start-maximized", "--disable-notifications", "--remote-allow-origins=*");
            if (Boolean.parseBoolean(ConfigReader.getProperty("headless", "false"))) options.addArguments("--headless=new", "--window-size=1920,1080");
            driver = new ChromeDriver(options);
        }
        return driver;
    }
    public static synchronized void quitBrowser() {
        if (driver != null) { try { driver.quit(); } finally { driver = null; } }
    }
}
