package qkart_automation.tests;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import qkart_automation.BaseTest;
import qkart_automation.DriverFactory;
import qkart_automation.pages.Home;
import qkart_automation.pages.Login;
import qkart_automation.pages.Register;

public class testCase08 extends BaseTest {

    public String lastGeneratedUserName;


    @Test(description = "Verify that a product added to a cart is available when a new tab is added", priority = 8, groups = {
            "Regression" })
    public void TestCase08() throws InterruptedException {
        Boolean status = false;
        WebDriver driver = DriverFactory.getDriver();

        logStatus("Start TestCase",
                "Test Case 8: Verify that product added to cart is available when a new tab is opened",
                "DONE");
        //takeScreenshot(driver, "StartTestCase", "TestCase08");

        Register registration = new Register(driver);
        registration.navigateToRegisterPage();
        status = registration.registerUser("testUser", "abc@123", true);
        Assert.assertTrue(status, "Test Case 8: Registration : Failed");
        lastGeneratedUserName = registration.lastGeneratedUsername;

        Login login = new Login(driver);
        login.navigateToLoginPage();
        status = login.performLogin(lastGeneratedUserName, "abc@123");
        Assert.assertTrue(status, "Test Case 8:  Login : Failed");

        Home homePage = new Home(driver);
        homePage.navigateToHome();

        status = homePage.searchForProduct("YONEX");
        status = homePage.addProductToCart("YONEX Smash Badminton Racquet");
        Assert.assertTrue(status, "Unable to add YONEX Badminton Racquet");
        String currentURL = driver.getCurrentUrl();

        driver.findElement(By.linkText("Privacy policy")).click();
        Set<String> handles = driver.getWindowHandles();
        driver.switchTo().window(handles.toArray(new String[handles.size()])[1]);

        driver.get(currentURL);
        Thread.sleep(2000);

        List<String> expectedResult = Arrays.asList("YONEX Smash Badminton Racquet");
        status = homePage.verifyCartContents(expectedResult);
        Assert.assertTrue(status, "Unable to veify same cart content");

        driver.close();

        driver.switchTo().window(handles.toArray(new String[handles.size()])[0]);

        logStatus("End TestCase",
                "Test Case 8: Verify that product added to cart is available when a new tab is opened",
                status ? "PASS" : "FAIL");
       // takeScreenshot(driver, "EndTestCase", "TestCase08");
    }

}
