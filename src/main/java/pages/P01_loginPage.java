package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class P01_loginPage {

    //Driver Declaration
    WebDriver driver;

    //Constructor to Driver Initialization
    public P01_loginPage(WebDriver driver) {
       this.driver=driver;
    }

    // Locators
    private final By username = By.id("user-name");
    private final By password = By.id("password");
    private final By loginButton = By.id("login-button");
    private final By homePageTitle = By.xpath("//div[@class='product_label']");

    //action methods
    public P01_loginPage fillUsername(String username){
        driver.findElement(this.username).sendKeys(username);
        return this;
    }
    public P01_loginPage fillPassword(String password){
        driver.findElement(this.password).sendKeys(password);
        return this;
    }
    public P01_loginPage clickLoginButton(){
        driver.findElement(this.loginButton).click();
        return this;
    }

    public Boolean isHomePageTitleShown(String homePageTitle){
        return driver.findElement(this.homePageTitle).getText().equals(homePageTitle);
    }

}