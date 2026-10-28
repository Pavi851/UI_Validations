package tests;

import base.BaseTest;
import org.testng.annotations.Test;
import pages.WebTablePage;

public class NavigationValidationTest extends BaseTest {
    @Test
    public void validateSorting() {
        new WebTablePage(driver).validateNavigation();
    }
}
