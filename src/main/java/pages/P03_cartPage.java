package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class P03_cartPage {

    WebDriver driver;

    //constructor
    public P03_cartPage(WebDriver driver){
        this.driver=driver;
    }

    //locator
    private final By checkOutButton = By.className("checkout_button");

    //method
    public void clickCheckOutButton(){
        driver.findElement(checkOutButton).click();
    }
}
