package qkart_automation.pages;

import java.time.Duration;
import java.util.NoSuchElementException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

import qkart_automation.Wrappers;

public class Login {
    
    WebDriver driver;
    String url = "https://crio-qkart-frontend-qa.vercel.app/login";

    @FindBy(id="username")
    WebElement loginPageUsernameField;

    @FindBy(id="password")
    WebElement loginPagePasswordField;

    @FindBy(xpath = "//button[contains(text(),'Login')]")
    WebElement loginPageLoginButton;

    @FindBy(className = "username-text")
    WebElement loggedInUsername;

    public Login(WebDriver driver){
        this.driver=driver;
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 15), this);
    }

    public void navigateToLoginPage() {
        if (!driver.getCurrentUrl().equals(url)) {
            driver.get(url);
        }
    }

    public Boolean performLogin(String username, String password){
        Wrappers.sendKeys(driver, loginPageUsernameField, username);
        Wrappers.sendKeys(driver, loginPagePasswordField, password);
        Wrappers.click(driver, loginPageLoginButton);

        FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                                    .withTimeout(Duration.ofSeconds(10))
                                    .pollingEvery(Duration.ofMillis(500))
                                    .ignoring(NoSuchElementException.class);
        wait.until(ExpectedConditions.invisibilityOf(loginPageLoginButton));

        return this.verifyUserLoggedIn(username);
    }

    public boolean verifyUserLoggedIn(String username){
        try{
            return loggedInUsername.getText().equals(username);
        }
        catch(Exception e){
            return false;
        }
        
    }




}
