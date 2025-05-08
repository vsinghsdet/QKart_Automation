package qkart_automation.tests;

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

public class testCase03 extends BaseTest{

    @Test(description = "Verify the functionality of search text box", priority = 3, groups = { "Sanity" })
    public void TestCase03() throws InterruptedException {
        logStatus("TestCase 3", "Start test case : Verify functionality of search box ",
                "DONE");
        Boolean status;
        WebDriver driver = DriverFactory.getDriver();
        SoftAssert softAssert = new SoftAssert();
        // Visit the home page
        Home homePage = new Home(driver);
        homePage.navigateToHome();

        // Search for the "yonex" product
        status = homePage.searchForProduct("YONEX");
        softAssert.assertTrue(status, "Not able to Search with YONEX");

        // Fetch the search results
        List<WebElement> searchResults = homePage.getSearchResults();

        // Verify the search results are available
        softAssert.assertTrue(searchResults.size()!=0, "Test Case Failure. There were no results for the given search string");

        for (WebElement webElement : searchResults) {
            // Create a SearchResult object from the parent element
            SearchResult resultelement = new SearchResult(webElement);

            // Verify that all results contain the searched text
            String elementText = resultelement.getTitleofResult();
            softAssert.assertEquals(true, elementText.toUpperCase().contains("YONEX"),
                    "Test Case Failure. Test Results contains un-expected values: "
                            + elementText);
        }

        logStatus("Step Success", "Successfully validated the search results ", "PASS");
        // Search for product
        status = homePage.searchForProduct("Gesundheit");
        softAssert.assertFalse(status, "Products are showing up for invalid keyword");

        // Verify no search results are found
        searchResults = homePage.getSearchResults();

        if (searchResults.size() == 0) {
            softAssert.assertTrue(homePage.isNoResultFound(),
                    "Not able to verify No products found message");
            logStatus("TestCase 3",
                    "Test Case PASS. Verified that no search results were found for the given text",
                    "PASS");
        }
    }

}
