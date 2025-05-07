package qkart_automation.pages;

import java.time.Duration;
import java.util.ArrayList;
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

public class Home {
    
    WebDriver driver;
    
    String url = "https://crio-qkart-frontend-qa.vercel.app";

    @FindBy(xpath = "//button[text()='Login']")
    WebElement homePageLoginButton;

    @FindBy(xpath = "//button[text()='Register']")
    WebElement homePageRegisterButton;

    @FindBy(xpath = "//button[text()='Logout']")
    WebElement logoutButton;

    @FindBy(xpath = "//input[@name='search']")
    WebElement searchInputBox;

    @FindBy(className = "css-1qw96cp")
    List<WebElement> searchResults;

    @FindBy(tagName = "h4")
    WebElement noProductsFound;

    @FindBy(className = "css-sycj1h")
    List<WebElement> gridContent;

    @FindBy(className = "css-yg30e6")
    WebElement productTitle;

    @FindBy(className = "checkout-btn")
    WebElement checkoutButton;

    @FindBy(xpath = "//*[@class='MuiBox-root css-1gjj37g']/div[1]")
    List<WebElement> cartContents;

    public Home(WebDriver driver){
        this.driver=driver;
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 15), this);
    }

    public void navigateToHome() {
        if (!this.driver.getCurrentUrl().equals(this.url)) {
            this.driver.get(this.url);
        }
    }

    public Boolean performLogout(){
        try{
            Wrappers.click(driver, logoutButton);
            return true;
        }
        catch(Exception e){
            return false;
        }
        
    }

    public Boolean searchForProduct(String product) {
        try {
            Wrappers.sendKeys(driver, searchInputBox, product);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
            wait.until(ExpectedConditions.or(ExpectedConditions.textToBePresentInElement(productTitle, product),
            ExpectedConditions.presenceOfElementLocated(By.tagName("h4"))));
            //Thread.sleep(3000);
            return true;
        } catch (Exception e) {
            System.out.println("Error while searching for a product: " + e.getMessage());
            return false;
        }
    }

    public List<WebElement> getSearchResults() {
        try {
            return searchResults;
        } catch (Exception e) {
            System.out.println("There were no search results: " + e.getMessage());
            return searchResults;
        }
    }


    public Boolean isNoResultFound() {
        Boolean status = false;
        try {
            return noProductsFound.isDisplayed();
        } catch (Exception e) {
            return status;
        }
    }

    public Boolean addProductToCart(String productName) {
        try {
            for (WebElement cell : gridContent) {
                if (productName.contains(cell.findElement(By.className("css-yg30e6")).getText())) {
                    cell.findElement(By.tagName("button")).click();
                    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
                    wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(
                            String.format("//div[contains(@class,'css-1gjj37g')]/div[1][text()='%s']", productName))));
                    return true;
                }
            }
            System.out.println("Unable to find the given product: " + productName);
            return false;
        } catch (Exception e) {
            System.out.println("Exception while performing add to cart: " + e.getMessage());
            return false;
        }
    }


    public Boolean clickCheckout() {
        Boolean status = false;
        try {
            Wrappers.click(driver, checkoutButton);
            status = true;
            return status;
        } catch (Exception e) {
            System.out.println("Exception while clicking on Checkout: " + e.getMessage());
            return status;
        }
    }

    public Boolean changeProductQuantityinCart(String productName, int quantity) {
        try {
            WebElement cartParent = driver.findElement(By.className("cart"));
            List<WebElement> cartContents = cartParent.findElements(By.className("css-zgtx0t"));

            int currentQty;
            for (WebElement item : cartContents) {
                if (productName.contains(
                    item.findElement(By.xpath("//*[@class='MuiBox-root css-1gjj37g']/div[1]")).getText())) {
                    
                    currentQty = Integer.valueOf(item.findElement(By.className("css-olyig7")).getText());

                    while (currentQty != quantity) {
                        if (currentQty < quantity) {
                            item.findElements(By.tagName("button")).get(1).click();

                        } else {
                            item.findElements(By.tagName("button")).get(0).click();
                        }

                        synchronized (driver) {
                            driver.wait(2000);
                        }

                        currentQty = Integer
                                .valueOf(item.findElement(By.xpath("//div[@data-testid=\"item-qty\"]")).getText());
                    }

                    return true;
                }
            }

            return false;
        } catch (Exception e) {
            if (quantity == 0)
                return true;
            System.out.println(("exception occurred when updating cart"));
            return false;
        }
    }

    public Boolean verifyCartContents(List<String> expectedCartContents) {
        try {
            ArrayList<String> actualCartContents = new ArrayList<String>();
            for (WebElement cartItem : cartContents) {
                actualCartContents.add(cartItem.getText());
            }

            for (String expected : expectedCartContents) {
                if (!actualCartContents.contains(expected.trim())) {
                    return false;
                }
            }
            return true;

        } catch (Exception e) {
            System.out.println("Exception while verifying cart contents: " + e.getMessage());
            return false;
        }
    }








}
