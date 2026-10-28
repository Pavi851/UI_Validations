package pages;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utilities.ConfigReader;

public class BasePage {
    protected final WebDriver driver;
    protected final WebDriverWait wait;

    public BasePage(WebDriver driver) {
        if (driver == null) throw new IllegalArgumentException("WebDriver must not be null");
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(Long.parseLong(ConfigReader.getProperty("explicit.wait.seconds", "15"))));
    }

    protected WebElement visible(By locator) { return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)); }
    protected WebElement clickable(By locator) { return wait.until(ExpectedConditions.elementToBeClickable(locator)); }
    protected boolean exists(By locator) {
        try { return !driver.findElements(locator).isEmpty(); }
        catch (NoSuchElementException e) { return false; }
    }
    protected void click(By locator) { clickable(locator).click(); }
    protected void type(By locator, String value) { WebElement e = visible(locator); e.clear(); e.sendKeys(value); }
    protected String text(By locator) { return visible(locator).getText().trim(); }
}
