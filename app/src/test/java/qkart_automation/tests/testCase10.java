package qkart_automation.tests;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import qkart_automation.BaseTest;
import qkart_automation.DriverFactory;
import qkart_automation.pages.Home;

public class testCase10 extends BaseTest {
    @Test(description = "Verify that the contact us dialog works fine", priority = 10, groups = { "Regression" })
    public void TestCase10() throws InterruptedException {
        Boolean status;
        WebDriver driver = DriverFactory.getDriver();
        logStatus("Start TestCase",
                "Test Case 10: Verify that contact us option is working correctly ",
                "DONE");

        Home homePage = new Home(driver);
        homePage.navigateToHome();

        status = homePage.verifyContactUsDialogBox(driver, "Vishal", "abc@gmail.com", "Happy Learning");
        Assert.assertFalse(status, "Unable to verify invisibility of contact us modal");
        logStatus("End TestCase",
                "Test Case 10: Verify that contact us option is working correctly ",
                "PASS");

    }

}
