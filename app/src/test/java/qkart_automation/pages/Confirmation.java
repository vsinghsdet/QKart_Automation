package qkart_automation.pages;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

import qkart_automation.Wrappers;

public class Confirmation {

    WebDriver driver;

    @FindBy(tagName = "iframe")
    List<WebElement> advertisements;

    @FindBy(xpath = "//iframe[@class='iframe'][1]")
    public WebElement advertisement1;

    @FindBy(xpath = "//iframe[@class='iframe'][2]")
    public WebElement advertisement2;

    @FindBy(xpath = "//button[text()='Buy Now']")
    WebElement buyNowButton;

    public Confirmation(WebDriver driver){
        this.driver=driver;
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 15), this);
    }

    public int totalNumberofAdervtisements(){
        return advertisements.size();
    }

    public Boolean isAdvertisementClickable(WebDriver driver, WebElement advertisement){
        String currentURL = driver.getCurrentUrl();
        
        driver.switchTo().frame(advertisement);
        Wrappers.click(driver, buyNowButton);
        driver.switchTo().parentFrame();

        return !driver.getCurrentUrl().equals(currentURL);
    }


}
