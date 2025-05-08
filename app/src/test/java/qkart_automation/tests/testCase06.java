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

public class testCase06 extends BaseTest {
    
    public static String lastGeneratedUserName;

    @Test(description = "Verify that the contents of the cart can be edited", priority = 6, groups = { "Regression" })
    @Parameters({ "productNameToSearch1", "productNameToSearch2", "addressDetails" })
    public void TestCase06(String productNameToSearch1, String productNameToSearch2,
            String addressDetails) throws InterruptedException {
        Boolean status;
        WebDriver driver = DriverFactory.getDriver();
        SoftAssert softAssert = new SoftAssert();
        logStatus("Start TestCase", "Test Case 6: Verify that cart can be edited", "DONE");
        Home homePage = new Home(driver);
        Register registration = new Register(driver);
        Login login = new Login(driver);

        registration.navigateToRegisterPage();
        status = registration.registerUser("testUser", "abc@123", true);
        softAssert.assertTrue(status, "Test Case 6:  Verify that cart can be edited: Failed");

        lastGeneratedUserName = registration.lastGeneratedUsername;

        login.navigateToLoginPage();
        status = login.performLogin(lastGeneratedUserName, "abc@123");
        softAssert.assertTrue(status, "Test Case 6:  Verify that cart can be edited: Failed");

        homePage.navigateToHome();
        status = homePage.searchForProduct("Xtend");
        homePage.addProductToCart(productNameToSearch1);

        status = homePage.searchForProduct("Yarine");
        homePage.addProductToCart(productNameToSearch2);

        // update watch quantity to 2
        homePage.changeProductQuantityinCart(productNameToSearch1, 2);

        // update table lamp quantity to 0
        homePage.changeProductQuantityinCart(productNameToSearch2, 0);

        // update watch quantity again to 1
        homePage.changeProductQuantityinCart(productNameToSearch1, 1);

        homePage.clickCheckout();

        Checkout checkoutPage = new Checkout(driver);
        checkoutPage.addNewAddress(addressDetails);
        checkoutPage.selectAddress(addressDetails);

        checkoutPage.placeOrder();

        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(15));
        status = wait.until(ExpectedConditions
                .urlToBe("https://crio-qkart-frontend-qa.vercel.app/thanks"));
        softAssert.assertTrue(status, "URL doesn't contains thanks");

        status = driver.getCurrentUrl().endsWith("/thanks");
        homePage.navigateToHome();
        homePage.performLogout();

        logStatus("End TestCase", "Test Case 6: Verify that cart can be edited: ",
                status ? "PASS" : "FAIL");
        
        softAssert.assertAll();
    }

}
