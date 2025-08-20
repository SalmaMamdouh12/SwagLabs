package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static util.Utility.generateRandomName;
import static util.Utility.generateRandomZipCode;

public class P04_checkOutStepOnePage {

    WebDriver driver;

    public P04_checkOutStepOnePage(WebDriver driver) {
        this.driver = driver;
    }

    //Locator
    private final By firstNameField = By.id("first-name");
    private final By lastNameField = By.id("last-name");
    private final By zipCodeField = By.id("postal-code");
    private final By continueButton = By.className("cart_button");

    //method

    public P04_checkOutStepOnePage fillFirstNameField(String firstName){
        driver.findElement(this.firstNameField).sendKeys(firstName);
        return this;
    }
    public P04_checkOutStepOnePage fillLastNameField(String lastName) {
         driver.findElement(this.lastNameField).sendKeys(lastName);
         return this;
    }
    public P04_checkOutStepOnePage fillZipCodeField(String zipCode) {
        driver.findElement(this.zipCodeField).sendKeys(zipCode);
        return this;
    }

    public void clickContinueButton(){
        driver.findElement(this.continueButton).click();
    }
}
