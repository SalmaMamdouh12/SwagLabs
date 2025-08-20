package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class P06_checkOutCompletePage {

    WebDriver driver;

    public  P06_checkOutCompletePage (WebDriver driver){
        this.driver = driver;
    }

    //Locator
    private final By successfulOrderMsg = By.className("complete-header");

    //method
    public String getSuccessfulOrderMsg(){
       return driver.findElement(successfulOrderMsg).getText();
    }

}
