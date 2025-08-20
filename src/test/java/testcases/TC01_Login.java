package testcases;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.P01_loginPage;

import static drivers.DriverHolder.getDriver;

public class TC01_Login extends TestBase {

    String username = "standard_user";
    String password = "secret_sauce";
    String actualHomePageTitle = "Products";

    @Test(priority = 1, description = "User could log in with valid username and password")
    public void loginWithValidData_P() throws InterruptedException {


        //test steps
        new P01_loginPage(getDriver()).fillUsername(username).fillPassword(password).clickLoginButton();

        //assertion
        Assert.assertTrue(new P01_loginPage(getDriver()).isHomePageTitleShown(actualHomePageTitle), "Assertion Msg: Login failed");

    }

}
