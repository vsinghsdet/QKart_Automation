package qkart_automation.tests;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
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

        driver.findElement(By.xpath("//*[text()='Contact us']")).click();

        WebElement name = driver.findElement(By.xpath("//input[@placeholder='Name']"));
        name.sendKeys("user");
        WebElement email = driver.findElement(By.xpath("//input[@placeholder='Email']"));
        email.sendKeys("user@gmail.com");
        WebElement message = driver.findElement(By.xpath("//input[@placeholder='Message']"));
        message.sendKeys("Testing the contact us page");

        WebElement contactUs = driver.findElement(By.xpath("//button[text()=' Contact Now']"));
        Assert.assertTrue(contactUs.isEnabled(), "Contact Us is not clickable");
        contactUs.click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        status = wait.until(ExpectedConditions.invisibilityOf(contactUs));
        Assert.assertTrue(status, "Unable to verify invisibility of contact us modal");
        logStatus("End TestCase",
                "Test Case 10: Verify that contact us option is working correctly ",
                "PASS");

        
    }

}
