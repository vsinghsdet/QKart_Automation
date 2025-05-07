package qkart_automation.pages;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SearchResult {
    WebElement parentElement;

    public SearchResult(WebElement SearchResultElement) {
        this.parentElement = SearchResultElement;
    }

    public String getTitleofResult() {
        String titleOfSearchResult = "";
        WebElement element = parentElement.findElement(By.className("css-yg30e6"));
        titleOfSearchResult = element.getText();
        return titleOfSearchResult;
    }

    public Boolean openSizechart() {
        try {
            WebElement element = parentElement.findElement(By.tagName("button"));
            element.click();

            Thread.sleep(3000);
            return true;
        } catch (Exception e) {
            System.out.println("Exception while opening Size chart: " + e.getMessage());
            return false;
        }
    }

    public Boolean closeSizeChart(WebDriver driver) {
        try {
            synchronized (driver) {
                driver.wait(2000);
            }
            
            Actions action = new Actions(driver);

            action.sendKeys(Keys.ESCAPE);
            action.perform();

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("MuiDialog-paperScrollPaper")));

            return true;
        } catch (Exception e) {
            System.out.println("Exception while closing the size chart: " + e.getMessage());
            return false;
        }
    }


    public Boolean verifySizeChartExists() {
        Boolean status = false;
        try {
            WebElement element = parentElement.findElement(By.tagName("button"));
            status = element.getText().equals("SIZE CHART");

            return status;
        } catch (Exception e) {
            return status;
        }
    }

    
    public Boolean validateSizeChartContents(List<String> expectedTableHeaders, List<List<String>> expectedTableBody,
            WebDriver driver) {
        Boolean status = true;
        try {
            WebElement sizeChartParent = driver.findElement(By.className("MuiDialog-paperScrollPaper"));
            WebElement tableElement = sizeChartParent.findElement(By.tagName("table"));
            List<WebElement> tableHeader = tableElement.findElement(By.tagName("thead")).findElements(By.tagName("th"));

            // Check table headers match
            String tempHeaderValue;
            for (int i = 0; i < expectedTableHeaders.size(); i++) {
                tempHeaderValue = tableHeader.get(i).getText();

                if (!expectedTableHeaders.get(i).equals(tempHeaderValue)) {
                    System.out.println("Failure in Header Comparison: Expected:  " + expectedTableHeaders.get(i)
                            + " Actual: " + tempHeaderValue);
                    status = false;
                }
            }

            List<WebElement> tableBodyRows = tableElement.findElement(By.tagName("tbody"))
                    .findElements(By.tagName("tr"));

            // Check table body match
            List<WebElement> tempBodyRow;
            for (int i = 0; i < expectedTableBody.size(); i++) {
                tempBodyRow = tableBodyRows.get(i).findElements(By.tagName("td"));

                for (int j = 0; j < expectedTableBody.get(i).size(); j++) {
                    tempHeaderValue = tempBodyRow.get(j).getText();

                    if (!expectedTableBody.get(i).get(j).equals(tempHeaderValue)) {
                        System.out.println("Failure in Body Comparison: Expected:  " + expectedTableBody.get(i).get(j)
                                + " Actual: " + tempHeaderValue);
                        status = false;
                    }
                }
            }
            return status;

        } catch (Exception e) {
            System.out.println("Error while validating chart contents");
            return false;
        }
    }

    public Boolean verifyExistenceofSizeDropdown(WebDriver driver) {
        Boolean status = false;
        try {
            // If the size dropdown exists and is displayed return true, else return false
            WebElement element = driver.findElement(By.className("css-13sljp9"));
            status = element.isDisplayed();
            return status;
        } catch (Exception e) {
            return status;
        }
    }
}