package tests;
import base.BaseTest;
import org.testng.annotations.Test;
import pages.WebTablePage;
public class FilterValidationTest extends BaseTest {
    @Test public void validateFilter() { new WebTablePage(driver).filterAndValidate(); }
}
