package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static java.lang.Double.parseDouble;

public class P05_checkOutStepTwoPage {

    WebDriver driver;
    Double tax;
    Double actualPriceWithTaxes;
    Double expectedSubTotal;
    Double subTotalValue;
    Double Summation;
    Double roundedSummation;
    String subTotalText;
    String totalText;

    public P05_checkOutStepTwoPage (WebDriver driver){
        this.driver = driver;
    }

    //Locator
    private final By subTotal = By.className("summary_subtotal_label");
    private final By taxValue = By.className("summary_tax_label");
    private final By totalValue = By.className("summary_total_label");
    private final By finishButton = By.className("cart_button");
    private final By cancelButton = By.className("btn_secondary");


    //methods
    //get expected price from screen
    public Double getExpectedSubTotalPrice(){

        subTotalText = driver.findElement(subTotal).getText();
        expectedSubTotal = Double.parseDouble(subTotalText.replaceAll("[^\\d.]", ""));
        System.out.println("ExpectedSubTotal:" + expectedSubTotal);
        return Math.round(expectedSubTotal * 100.0)/100.0;
    }

    //get expected price with Taxes then get it from screen
    public Double getExpectedPriceWithTaxes(){

        totalText = driver.findElement(totalValue).getText();
        actualPriceWithTaxes = Double.parseDouble(totalText.replaceAll("[^\\d.]", ""));
        System.out.println("ActualPriceWithTaxes:" + actualPriceWithTaxes);

        return Math.round(actualPriceWithTaxes * 100.0)/100.0;
    }

    //calculate actual Price with Taxes
    public Double calcActualPriceWithTaxes(){

        tax = Double.parseDouble(driver.findElement(taxValue).getText().replaceAll("[^\\d.]", ""));
        System.out.println("tax value:" + tax);

        subTotalValue =  getExpectedSubTotalPrice();
        System.out.println("subTotal value:" + tax);

        Summation = tax + subTotalValue;
        roundedSummation = Math.round(Summation * 100.0) / 100.0;
        System.out.println("roundedSummation:" + roundedSummation);

        return roundedSummation;
    }

    public void clickCancelButton(){
        driver.findElement(cancelButton).click();
    }

    public void clickFinishButton(){
        driver.findElement(finishButton).click();
    }
}
