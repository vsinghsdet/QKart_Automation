package qkart_automation.tests;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import qkart_automation.BaseTest;
import qkart_automation.DriverFactory;
import qkart_automation.pages.Register;

public class testCase02 extends BaseTest {

    public static String lastGeneratedUserName;

    @Test(description = "Verify re-registering an already registered user fails", priority = 2, groups = { "Sanity" })
    public void TestCase02() throws InterruptedException {
        Boolean status;
        WebDriver driver = DriverFactory.getDriver();
        SoftAssert softAssert = new SoftAssert();
        logStatus("Start Testcase",
                "Test Case 2: Verify User Registration with an existing username ",
                "DONE");

        // Visit the Registration page and register a new user
        Register registration = new Register(driver);
        registration.navigateToRegisterPage();
        status = registration.registerUser("testUser", "abc@123", true);
        logStatus("Test Step", "User Registration : ", status ? "PASS" : "FAIL");
        softAssert.assertTrue(status, "Able to register");

        // Save the last generated username
        lastGeneratedUserName = registration.lastGeneratedUsername;

        // Visit the Registration page and try to register using the previously
        // registered user's credentials
        registration.navigateToRegisterPage();
        status = registration.registerUser(lastGeneratedUserName, "abc@123", false);
        Assert.assertFalse(status, "Able to re-rrgister using existing username");
        // If status is true, then registration succeeded, else registration has
        // failed. In this case registration failure means Success
        logStatus("End TestCase", "Test Case 2: Verify user Registration : ",
                status ? "FAIL" : "PASS");

        softAssert.assertAll();
    }

}
