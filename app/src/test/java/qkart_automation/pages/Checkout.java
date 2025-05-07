package qkart_automation.pages;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import qkart_automation.Wrappers;

public class Checkout {

    WebDriver driver;
    String url = "https://crio-qkart-frontend-qa.vercel.app/checkout";


    @FindBy(id="add-new-btn")
    WebElement addNewAddressButton;

    @FindBy(className = "MuiOutlinedInput-input")
    WebElement addressBox;

    @FindBy(className = "css-177pwqq")
    List<WebElement> buttons;

    @FindBy(xpath = "//button[text()='PLACE ORDER']")
    WebElement placeOrderButton;

    @FindBy(id="notistack-snackbar")
    WebElement alertMessage;


    public Checkout(WebDriver driver){
        this.driver=driver;
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 15), this);
    }


    public void navigateToCheckout() {
        if (!this.driver.getCurrentUrl().equals(this.url)) {
            this.driver.get(this.url);
        }
    }

    public Boolean addNewAddress(String addresString) {
        try {
            
            Wrappers.click(driver, addNewAddressButton);

            Wrappers.sendKeys(driver, addressBox, addresString);

            for (WebElement button : buttons) {
                if (button.getText().equals("ADD")) {
                    button.click();
                    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
                    wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(String.format(
                            "//p[contains(@class,'css-yg30e6') and text()='%s']", addresString))));
                    Thread.sleep(2000);
                    return true;
                }
            }
            return true;
        } catch (Exception e) {
            System.out.println("Exception occurred while entering address: " + e.getMessage());
            return false;

        }
    }

    public Boolean selectAddress(String addressToSelect) {
        try {

            WebElement address = driver.findElement(By.xpath(String.format("//p[contains(@class,'css-yg30e6') and text()= '%s']", addressToSelect)));
            Wrappers.click(driver, address);

            return true;
        } catch (Exception e) {
            System.out.println("Exception Occurred while selecting the given address: " + e.getMessage());
            return false;
        }

    }

    public Boolean placeOrder() {
        try {
            Wrappers.click(driver, placeOrderButton);
            return true;

        } catch (Exception e) {
            System.out.println("Exception while clicking on PLACE ORDER: " + e.getMessage());
            return false;
        }
    }


    public Boolean verifyInsufficientBalanceMessage() {
        try {
            if (alertMessage.isDisplayed()) {
                if (alertMessage.getText().equals("You do not have enough balance in your wallet for this purchase")) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            System.out.println("Exception while verifying insufficient balance message: " + e.getMessage());
            return false;
        }
    }




    
    
}
