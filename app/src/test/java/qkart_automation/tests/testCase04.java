package qkart_automation.tests;

import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import qkart_automation.BaseTest;
import qkart_automation.DriverFactory;
import qkart_automation.pages.Home;
import qkart_automation.pages.SearchResult;

public class testCase04 extends BaseTest {

    @Test(description = "Verify the existence of size chart for certain items and validate contents of size chart", priority = 4, groups = {
            "Regression" })
    public void TestCase04() throws InterruptedException {
        logStatus("TestCase 4", "Start test case : Verify the presence of size Chart",
                "DONE");
        Boolean status;
        WebDriver driver = DriverFactory.getDriver();

        SoftAssert softAssert = new SoftAssert();

        // Visit home page
        Home homePage = new Home(driver);
        homePage.navigateToHome();

        // Search for product and get card content element of search results
        status = homePage.searchForProduct("Running Shoes");
        List<WebElement> searchResults = homePage.getSearchResults();

        // Create expected values
        List<String> expectedTableHeaders = Arrays.asList("Size", "UK/INDIA", "EU", "HEEL TO TOE");
        List<List<String>> expectedTableBody = Arrays.asList(Arrays.asList("6", "6", "40", "9.8"),
                Arrays.asList("7", "7", "41", "10.2"),
                Arrays.asList("8", "8", "42", "10.6"),
                Arrays.asList("9", "9", "43", "11"),
                Arrays.asList("10", "10", "44", "11.5"),
                Arrays.asList("11", "11", "45", "12.2"),
                Arrays.asList("12", "12", "46", "12.6"));

        // Verify size chart presence and content matching for each search result
        for (WebElement webElement : searchResults) {
            SearchResult result = new SearchResult(webElement);

            // Verify if the size chart exists for the search result
            softAssert.assertTrue(result.verifySizeChartExists(),
                    "Test Case Fail. Size Chart Link does not exist");

            logStatus("Step Success",
                    "Successfully validated presence of Size Chart Link",
                    "PASS");

            // Verify if size dropdown exists
            status = result.verifyExistenceofSizeDropdown(driver);
            softAssert.assertTrue(status, "Size Drop Down is not present");
            logStatus("Step Success", "Validated presence of drop down",
                    status ? "PASS" : "FAIL");

            // Open the size chart
            softAssert.assertTrue(result.openSizechart(),
                    "Test Case Fail. Failure to open Size Chart");

            // Verify if the size chart contents matches the expected values

            softAssert.assertTrue(
                    result.validateSizeChartContents(expectedTableHeaders,
                            expectedTableBody, driver),
                    "Failure while validating contents of Size Chart Link");

            // Close the size chart modal
            softAssert.assertTrue(result.closeSizeChart(driver),
                    "Unable to close Size Chart");
        }

        softAssert.assertAll();
    }

}
