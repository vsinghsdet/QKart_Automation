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

public class testCase05 extends BaseTest {

    public static String lastGeneratedUserName;

    @Test(description = "Verify that a new user can add multiple products in to the cart and Checkout", priority = 5, groups = {
            "Sanity" })
    @Parameters({ "productNameToSearchFor", "productNameToSearchFor2", "addressDetails" })
    public void TestCase05(String productNameToSearchFor, String productNameToSearchFor2,
            String addressDetails) throws InterruptedException {
        Boolean status;
        SoftAssert softAssert = new SoftAssert();
        WebDriver driver = DriverFactory.getDriver();
        logStatus("Start TestCase", "Test Case 5: Verify Happy Flow of buying products",
                "DONE");

        // Go to the Register page
        Register registration = new Register(driver);
        registration.navigateToRegisterPage();

        // Register a new user
        status = registration.registerUser("testUser", "abc@123", true);
        softAssert.assertTrue(status,
                "Unable to register, Test Case Failure. Happy Flow Test Failed");

        // Save the username of the newly registered user
        lastGeneratedUserName = registration.lastGeneratedUsername;

        // Go to the login page
        Login login = new Login(driver);
        login.navigateToLoginPage();

        // Login with the newly registered user's credentials
        status = login.performLogin(lastGeneratedUserName, "abc@123");
        softAssert.assertTrue(status, "Not able to login, Test Case 5: Happy Flow Test Failed");

        // Go to the home page
        Home homePage = new Home(driver);
        homePage.navigateToHome();

        // Find required products by searching and add them to the user's cart
        status = homePage.searchForProduct("YONEX");
        homePage.addProductToCart(productNameToSearchFor);
        status = homePage.searchForProduct("Tan");
        homePage.addProductToCart(productNameToSearchFor2);

        // Click on the checkout button
        homePage.clickCheckout();

        // Add a new address on the Checkout page and select it
        Checkout checkoutPage = new Checkout(driver);
        checkoutPage.addNewAddress(addressDetails);
        checkoutPage.selectAddress(addressDetails);

        // Place the order
        checkoutPage.placeOrder();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        wait.until(ExpectedConditions
                .urlToBe("https://crio-qkart-frontend-qa.vercel.app/thanks"));

        // Check if placing order redirected to the Thansk page
        status = driver.getCurrentUrl().endsWith("/thanks");

        // Go to the home page
        homePage.navigateToHome();

        // Log out the user
        homePage.performLogout();

        logStatus("End TestCase", "Test Case 5: Happy Flow Test Completed : ",
                status ? "PASS" : "FAIL");
        
        softAssert.assertAll();
    }

}
