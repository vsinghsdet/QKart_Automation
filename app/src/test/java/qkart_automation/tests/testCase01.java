package qkart_automation.tests;

import java.io.IOException;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import qkart_automation.BaseTest;
import qkart_automation.DriverFactory;
import qkart_automation.pages.Home;
import qkart_automation.pages.Login;
import qkart_automation.pages.Register;

public class testCase01 extends BaseTest {

    public static String lastGeneratedUserName;

    @Test(description = "Verify registration happens correctly", priority = 1, groups = { "Sanity" })
    @Parameters({ "username", "password" })
    public void TestCase01(@Optional("testUser") String username,
            @Optional("abc@123") String password) throws InterruptedException, IOException {
        WebDriver driver = DriverFactory.getDriver();
        Boolean status;
        SoftAssert softAssert = new SoftAssert();
        logStatus("Start TestCase", "Test Case 1: Verify User Registration", "DONE");
       

        // Visit the Registration page and register a new user
        Register registration = new Register(driver);
        registration.navigateToRegisterPage();
        status = registration.registerUser(username, password, true);
        softAssert.assertTrue(status, "Failed to register new user");

        // Save the last generated username
        lastGeneratedUserName = registration.lastGeneratedUsername;

        // Visit the login page and login with the previuosly registered user
        Login login = new Login(driver);
        login.navigateToLoginPage();
        status = login.performLogin(lastGeneratedUserName, "abc@123");
        logStatus("Test Step", "User Perform Login: ", status ? "PASS" : "FAIL");
        softAssert.assertTrue(status, "Failed to login with registered user");

        // Visit the home page and log out the logged in user
        Home home = new Home(driver);
        status = home.performLogout();

        logStatus("End TestCase", "Test Case 1: Verify user Registration : ",
                status ? "PASS" : "FAIL");
        
        softAssert.assertAll();
    }
}
