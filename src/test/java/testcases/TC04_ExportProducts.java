package testcases;

import drivers.DriverHolder;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.P01_loginPage;
import pages.P02_inventoryPage;
import util.Utility;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import static drivers.DriverHolder.getDriver;

public class TC04_ExportProducts extends TestBase{


    P02_inventoryPage p02_inventoryPage;
    String username = "standard_user";
    String password = "secret_sauce";
    String siteURL;

    @Test(priority = 1, description = "Product Details Scrapping")
    public void extractProductData_P() throws IOException, InterruptedException {

        p02_inventoryPage = new P02_inventoryPage(getDriver());

        new P01_loginPage(getDriver()).fillUsername(username).fillPassword(password).clickLoginButton();
        // Now get the actual page URL after login
        siteURL = DriverHolder.getDriver().getCurrentUrl();
        System.out.println("Captured site URL: " + siteURL);

        List<WebElement> products = p02_inventoryPage.getAllProducts();

        Utility.initCSV("src/test/resources/products.csv");
        Utility.writeCSVHeader(new String[]{"SiteURL","Name", "Description", "Price", "ImageURL"});

        for (WebElement product : products) {
            List<String> row = new ArrayList<>();
            row.add(siteURL);
            row.add(p02_inventoryPage.getProductName(product));
            row.add(p02_inventoryPage.getProductDesc(product));
            row.add(p02_inventoryPage.getProductPrice(product));
            row.add(p02_inventoryPage.getProductImageURL(product));

            Utility.writeCSVRow(row);
        }
        Utility.closeCSV();

        List<String> lines = Files.readAllLines(Paths.get("src/test/resources/products.csv"));
        Assert.assertEquals(lines.size(), products.size() + 1); // +1 for header

    }

  }