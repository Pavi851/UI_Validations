package tests;
import base.BaseTest;
import org.testng.annotations.Test;
import pages.WebTablePage;
import utilities.ConfigReader;
public class SearchValidationTest extends BaseTest {
    @Test public void validateSearch() { new WebTablePage(driver).searchAndValidate(ConfigReader.getProperty("search.text", "Laptop")); }
}
