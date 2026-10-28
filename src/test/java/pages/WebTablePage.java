package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import utilities.ConfigReader;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class WebTablePage extends BasePage {

    private final By table =
            By.id("productTable");

    private final By tableRows =
            By.cssSelector("#productTable tbody tr");

    private final By productNames =
            By.cssSelector("#productTable tbody tr td:nth-child(2)");

    private final By paginationLinks =
            By.cssSelector("#pagination a");

    private final By countryDropdown =
            By.id("country");

    private final By homeLink =
            By.xpath("(//a[normalize-space()='Home'])[1]");

    public WebTablePage(WebDriver driver) {
        super(driver);
    }

    private void ensureTablePresent() {

        Assert.assertTrue(
                exists(table),
                "Product table not found");

        wait.until(d ->
                !d.findElements(tableRows).isEmpty());
    }

    private List<String> currentPageProductNames() {

        return driver.findElements(productNames)
                .stream()
                .map(WebElement::getText)
                .map(String::trim)
                .filter(s -> !s.isBlank())
                .collect(Collectors.toList());
    }

    // SEARCH VALIDATION

    public void searchAndValidate(String searchText) {

        ensureTablePresent();

        List<String> products =
                currentPageProductNames();

        boolean found =
                products.stream()
                        .anyMatch(product ->
                                product.toLowerCase(Locale.ROOT)
                                        .contains(searchText.toLowerCase(Locale.ROOT)));

        if (!found) {

            List<WebElement> pages =
                    driver.findElements(paginationLinks);

            for (WebElement page : pages) {

                page.click();

                wait.until(d ->
                        !d.findElements(tableRows).isEmpty());

                products = currentPageProductNames();

                found =
                        products.stream()
                                .anyMatch(product ->
                                        product.toLowerCase(Locale.ROOT)
                                                .contains(searchText.toLowerCase(Locale.ROOT)));

                if (found) {
                    break;
                }
            }
        }

        Assert.assertTrue(
                found,
                "Product not found: " + searchText);
    }

    // FILTER VALIDATION

    public void filterAndValidate() {

        String country =
                ConfigReader.getProperty(
                        "filter.country",
                        "India");

        Assert.assertTrue(
                exists(countryDropdown),
                "Country dropdown not found");

        Select select =
                new Select(
                        visible(countryDropdown));

        select.selectByVisibleText(country);

        Assert.assertEquals(
                select.getFirstSelectedOption()
                        .getText()
                        .trim(),
                country,
                "Country selection failed");
    }

    // PAGINATION VALIDATION

    public void validatePagination() {

        ensureTablePresent();

        String firstPageProduct =
                driver.findElement(
                                By.xpath(
                                        "//table[@id='productTable']//tbody/tr[1]/td[2]"))
                        .getText();

        List<WebElement> pages =
                driver.findElements(paginationLinks);

        Assert.assertTrue(
                pages.size() > 1,
                "Pagination links not available");

        pages.get(1).click();

        wait.until(d ->
                !driver.findElement(
                                By.xpath(
                                        "//table[@id='productTable']//tbody/tr[1]/td[2]"))
                        .getText()
                        .equals(firstPageProduct));

        String secondPageProduct =
                driver.findElement(
                                By.xpath(
                                        "//table[@id='productTable']//tbody/tr[1]/td[2]"))
                        .getText();

        Assert.assertNotEquals(
                firstPageProduct,
                secondPageProduct,
                "Pagination failed");
    }

    // NAVIGATION VALIDATION

    public void validateNavigation() {

        Assert.assertTrue(
                exists(homeLink),
                "Home link not found");

        click(homeLink);

        Assert.assertTrue(
                driver.getCurrentUrl()
                        .contains("blogspot"),
                "Navigation failed");
    }

    // END TO END

    public void validateEndToEnd() {

        searchAndValidate(
                ConfigReader.getProperty(
                        "search.text",
                        "Laptop"));

        filterAndValidate();

        validatePagination();

        driver.get(
                ConfigReader.getProperty("base.url"));

        validateNavigation();
    }
}