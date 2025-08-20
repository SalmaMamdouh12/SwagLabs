package testcases;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.*;

import static drivers.DriverHolder.getDriver;
import static util.Utility.generateRandomName;
import static util.Utility.generateRandomZipCode;

public class TC03_AddRemoveSuccessfulOrder extends TestBase {

    P02_inventoryPage p02InventoryPage;
    P05_checkOutStepTwoPage p05CheckOutStepTwoPage;
    P06_checkOutCompletePage p06CheckOutCompletePage;

    String username = "standard_user";
    String password = "secret_sauce";
    String actualMsg = "THANK YOU FOR YOUR ORDER";
    double totalCalculatedPrice = 0;
    double recalculatedPriceAfterItemsRemoved = 0;
    String firstName = generateRandomName();
    String lastName = generateRandomName();
    String zipCode = generateRandomZipCode();


    @Test(priority = 1, description = "Create Successful Order With Add-Remove")
    public void createSuccessfulOrderWithAddRemoveProducts_P() throws InterruptedException {
        p02InventoryPage = new P02_inventoryPage(getDriver());
        p05CheckOutStepTwoPage = new P05_checkOutStepTwoPage(getDriver());
        p06CheckOutCompletePage = new P06_checkOutCompletePage(getDriver());

//        p01LoginPage.login(username,password);
        new P01_loginPage(getDriver()).fillUsername(username).fillPassword(password).clickLoginButton();
        totalCalculatedPrice = p02InventoryPage.addRandomProductsToCart(getDriver());

        new P02_inventoryPage(getDriver()).clickCartButton();
        Thread.sleep(2000);

        new P03_cartPage(getDriver()).clickCheckOutButton();
        Thread.sleep(2000);

        new P04_checkOutStepOnePage(getDriver()).fillFirstNameField(firstName).fillLastNameField(lastName).fillZipCodeField(zipCode).clickContinueButton();
        Thread.sleep(3000);

        new P05_checkOutStepTwoPage(getDriver()).clickCancelButton();
        Thread.sleep(2000);

        recalculatedPriceAfterItemsRemoved = Math.round((p02InventoryPage.removeRandomProductsFromCart(getDriver())) * 100.0) / 100.0;

        System.out.println("recalculatedPrice" + recalculatedPriceAfterItemsRemoved);

        new P02_inventoryPage(getDriver()).clickCartButton();
        Thread.sleep(2000);

        new P03_cartPage(getDriver()).clickCheckOutButton();
        Thread.sleep(2000);

        new P04_checkOutStepOnePage(getDriver()).fillFirstNameField(firstName).fillLastNameField(lastName).fillZipCodeField(zipCode).clickContinueButton();
        Thread.sleep(3000);

        //1st Assertion for calculated Price without taxes
        Assert.assertEquals(recalculatedPriceAfterItemsRemoved, p05CheckOutStepTwoPage.getExpectedSubTotalPrice(), "Prices aren't matched");

        //2nd Assertion for calculated price with Taxes
        Assert.assertEquals(p05CheckOutStepTwoPage.calcActualPriceWithTaxes(), p05CheckOutStepTwoPage.getExpectedPriceWithTaxes(), "Total price with taxes aren't matched");

        p05CheckOutStepTwoPage.clickFinishButton();
        Thread.sleep(3000);
        //3rd Assertion for successful order msg
        Assert.assertEquals(actualMsg, p06CheckOutCompletePage.getSuccessfulOrderMsg(), "Order Not Completed");


    }
}
