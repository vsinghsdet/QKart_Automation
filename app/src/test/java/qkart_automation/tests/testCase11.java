package qkart_automation.tests;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import qkart_automation.BaseTest;
import qkart_automation.DriverFactory;
import qkart_automation.pages.Checkout;
import qkart_automation.pages.Confirmation;
import qkart_automation.pages.Home;
import qkart_automation.pages.Login;
import qkart_automation.pages.Register;

public class testCase11 extends BaseTest {

    public static String lastGeneratedUserName;

    @Test(description = "Ensure that the Advertisement Links on the QKART page are clickable", priority = 11, groups = {
            "Sanity" })
    public void TestCase11() throws InterruptedException {
        Boolean status = false;
        WebDriver driver = DriverFactory.getDriver();
        SoftAssert softAssert = new SoftAssert();
        logStatus("Start TestCase",
                "Test Case 11: Ensure that the links on the QKART advertisement are clickable",
                "DONE");
       

        Register registration = new Register(driver);
        registration.navigateToRegisterPage();
        status = registration.registerUser("testUser", "abc@123", true);
        Assert.assertTrue(status, "Test Case 11: Registration : Failed");

        lastGeneratedUserName = registration.lastGeneratedUsername;

        Login login = new Login(driver);
        login.navigateToLoginPage();
        status = login.performLogin(lastGeneratedUserName, "abc@123");
        Assert.assertTrue(status, "Test Case 11: Login : Failed");

        Home homePage = new Home(driver);
        homePage.navigateToHome();

        status = homePage.searchForProduct("YONEX Smash Badminton Racquet");
        homePage.addProductToCart("YONEX Smash Badminton Racquet");
        homePage.changeProductQuantityinCart("YONEX Smash Badminton Racquet", 1);
        homePage.clickCheckout();

        Checkout checkoutPage = new Checkout(driver);
        checkoutPage.addNewAddress("Addr line 1  addr Line 2  addr line 3");
        checkoutPage.selectAddress("Addr line 1  addr Line 2  addr line 3");
        checkoutPage.placeOrder();
      
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlContains("thanks"));

        String currentURL = driver.getCurrentUrl();

        Confirmation confirmation = new Confirmation(driver);

        status = confirmation.totalNumberofAdervtisements()==3;
        logStatus("Step ", "Verify that 3 Advertisements are available",
                status ? "PASS" : "FAIL");

        WebElement advertisement1 = confirmation.advertisement1;

        status = confirmation.isAdvertisementClickable(driver, advertisement1);
        softAssert.assertTrue(status, "Advertisemment 1 button is not clickable");
        logStatus("Step ", "Verify that Advertisement 1 is clickable ",
                status ? "PASS" : "FAIL");

        driver.get(currentURL);
        wait.until(ExpectedConditions.urlContains("thanks"));

        WebElement advertisement2 = confirmation.advertisement2;

        status = confirmation.isAdvertisementClickable(driver, advertisement2);
        softAssert.assertTrue(status, "Advertisemment 1 button is not clickable");
        logStatus("Step ", "Verify that Advertisement 2 is clickable ",
                status ? "PASS" : "FAIL");

        logStatus("End TestCase",
                "Test Case 11:  Ensure that the links on the QKART advertisement are clickable",
                status ? "PASS" : "FAIL");

        softAssert.assertAll();

    }

}
