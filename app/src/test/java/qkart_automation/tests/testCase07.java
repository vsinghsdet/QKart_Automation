package qkart_automation.tests;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import qkart_automation.BaseTest;
import qkart_automation.DriverFactory;
import qkart_automation.pages.Checkout;
import qkart_automation.pages.Home;
import qkart_automation.pages.Login;
import qkart_automation.pages.Register;

public class testCase07 extends BaseTest {

    public String lastGeneratedUserName;

    @Test(description = "Verify that insufficient balance error is thrown when the wallet balance is not enough", priority = 7, groups = {
            "Sanity" })
    @Parameters({ "productName", "qty", "addressDetails" })
    public void TestCase07(String productName, int qty, String addressDetails)
            throws InterruptedException {
        Boolean status;
        SoftAssert softAssert = new SoftAssert();
        WebDriver driver = DriverFactory.getDriver();
        logStatus("Start TestCase",
                "Test Case 7: Verify that insufficient balance error is thrown when the wallet balance is not enough",
                "DONE");

        Register registration = new Register(driver);
        registration.navigateToRegisterPage();
        status = registration.registerUser("testUser", "abc@123", true);
        softAssert.assertTrue(status, "Test Case 7: Registration : Failed");
        lastGeneratedUserName = registration.lastGeneratedUsername;

        Login login = new Login(driver);
        login.navigateToLoginPage();
        status = login.performLogin(lastGeneratedUserName, "abc@123");
        softAssert.assertTrue(status, "Test Case 7:  Login : Failed");

        Home homePage = new Home(driver);
        homePage.navigateToHome();
        status = homePage.searchForProduct("Stylecon");
        homePage.addProductToCart(productName);

        homePage.changeProductQuantityinCart(productName, qty);

        homePage.clickCheckout();

        Checkout checkoutPage = new Checkout(driver);
        checkoutPage.addNewAddress(addressDetails);
        checkoutPage.selectAddress(addressDetails);

        checkoutPage.placeOrder();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        wait.until(ExpectedConditions.visibilityOf(checkoutPage.alertMessage));

        status = checkoutPage.verifyInsufficientBalanceMessage();
        softAssert.assertTrue(status, "Unable to verify insufficient balance message");

        logStatus("End TestCase",
                "Test Case 7: Verify that insufficient balance error is thrown when the wallet balance is not enough: ",
                status ? "PASS" : "FAIL");
        
        softAssert.assertAll();
    }

}
