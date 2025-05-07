package qkart_automation.pages;

import java.sql.Timestamp;
import java.time.Duration;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import qkart_automation.Wrappers;

public class Register {
    
    WebDriver driver;
    String url = "https://crio-qkart-frontend-qa.vercel.app/register";
    public String lastGeneratedUsername = "";

    @FindBy(id="username")
    WebElement username_txt_box;

    @FindBy(id="password")
    WebElement password_txt_box;

    @FindBy(id="confirmPassword")
    WebElement confirm_password_txt_box;
    
    @FindBy(className = "button")
    WebElement register_now_button;

    public Register(WebDriver driver){
        this.driver=driver;
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 15), this);
    }

    public void navigateToRegisterPage() {
        if (!driver.getCurrentUrl().equals(this.url)) {
            driver.get(this.url);
        }
    }

    public Boolean registerUser(String Username, String Password, Boolean makeUsernameDynamic){
        
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        String test_data_username;
        if (makeUsernameDynamic)
            test_data_username = Username + "_" + String.valueOf(timestamp.getTime());
        else
            test_data_username = Username;

        Wrappers.sendKeys(driver, username_txt_box, test_data_username);

        String test_data_password = Password;
        Wrappers.sendKeys(driver, password_txt_box, test_data_password);

        Wrappers.sendKeys(driver, confirm_password_txt_box, test_data_password);

        Wrappers.click(driver, register_now_button);

        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
            wait.until(ExpectedConditions.or(
                ExpectedConditions.urlToBe("https://crio-qkart-frontend-qa.vercel.app/login")));
        } catch (TimeoutException e) {
            return false;
        }

        this.lastGeneratedUsername = test_data_username;

        return this.driver.getCurrentUrl().endsWith("/login");
    }


}
