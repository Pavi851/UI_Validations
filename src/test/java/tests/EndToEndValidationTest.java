package tests;

import base.BaseTest;
import org.testng.annotations.Test;
import pages.WebTablePage;

public class EndToEndValidationTest extends BaseTest {

    @Test
    public void validateSearchFilteringPaginationAndNavigation() {

        new WebTablePage(driver)
                .validateEndToEnd();
    }
}
