package testcases;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.*;

import static drivers.DriverHolder.getDriver;
import static util.Utility.generateRandomName;
import static util.Utility.generateRandomZipCode;

public class TC02_AddSuccessfulOrder extends TestBase{

    P01_loginPage p01LoginPage;
    P02_inventoryPage p02InventoryPage;
    P03_cartPage p03CartPage;
    P04_checkOutStepOnePage p04CheckOutStepOnePage;
    P05_checkOutStepTwoPage p05CheckOutStepTwoPage;
    P06_checkOutCompletePage p06CheckOutCompletePage;

    String username = "standard_user";
    String password = "secret_sauce";
    String actualMsg = "THANK YOU FOR YOUR ORDER";

    double totalCalculatedPrice =0;


    @Test(priority = 1,description = "Add Successful Order")
    public void createSuccessfulOrderWithAddProducts_P() throws InterruptedException {

        p01LoginPage = new P01_loginPage(getDriver());
        p02InventoryPage = new P02_inventoryPage(getDriver());
        p03CartPage = new P03_cartPage(getDriver());
        p04CheckOutStepOnePage = new P04_checkOutStepOnePage(getDriver());
        p05CheckOutStepTwoPage = new P05_checkOutStepTwoPage(getDriver());
        p06CheckOutCompletePage = new P06_checkOutCompletePage(getDriver());
        String firstName = generateRandomName();
        String lastName = generateRandomName();
        String zipCode = generateRandomZipCode();

        new P01_loginPage(getDriver()).fillUsername(username).fillPassword(password).clickLoginButton();
        totalCalculatedPrice = p02InventoryPage.addRandomProductsToCart(getDriver());

        new P02_inventoryPage(getDriver()).clickCartButton();
        Thread.sleep(2000);

        new P03_cartPage(getDriver()).clickCheckOutButton();
        Thread.sleep(2000);

        new P04_checkOutStepOnePage(getDriver()).fillFirstNameField(firstName).fillLastNameField(lastName).fillZipCodeField(zipCode).clickContinueButton();
        Thread.sleep(3000);

        //1st Assertion for calculated Price without taxes
        Assert.assertEquals(totalCalculatedPrice , p05CheckOutStepTwoPage.getExpectedSubTotalPrice(),"Prices aren't matched");

        //2nd Assertion for calculated price with Taxes
        Assert.assertEquals(p05CheckOutStepTwoPage.calcActualPriceWithTaxes(),p05CheckOutStepTwoPage.getExpectedPriceWithTaxes(),"Total price with taxes aren't matched");

        new P05_checkOutStepTwoPage(getDriver()).clickFinishButton();
        Thread.sleep(3000);

        //3rd Assertion for successful order msg
        Assert.assertEquals(actualMsg,p06CheckOutCompletePage.getSuccessfulOrderMsg(),"Order Not Completed");


    }
}
