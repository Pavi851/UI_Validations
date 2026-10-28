package tests;
import base.BaseTest;
import org.testng.annotations.Test;
import pages.WebTablePage;
public class PaginationValidationTest extends BaseTest {
    @Test public void validatePagination() { new WebTablePage(driver).validatePagination(); }
}
