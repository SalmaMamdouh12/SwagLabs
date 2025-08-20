package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class P02_inventoryPage {

    WebDriver driver;

    //constructor
    public P02_inventoryPage (WebDriver driver){
        this.driver= driver;
    }

    //Locator
    private final By cartButton = By.className("fa-shopping-cart");
    private final By inventoryItem = By.className("inventory_item");
    private final By addedItemPrice =By.className("inventory_item_price");
    private final By button = By.tagName("button");

    //export locators
    private final By allProducts = By.className("inventory_item");
    private final By allProductNames = By.className("inventory_item_name");
    private final By allProductDesc = By.className("inventory_item_desc");
    private final By allProductPrices =  By.className("inventory_item_price");
    private final By allImages = By.className("inventory_item_img");

    List<WebElement> products;
    double totalCalculatedPrice = 0;
    int numberOfProductsToAdd;
    Random random;

    //to add random products to cart with calc their total prices
    public double addRandomProductsToCart(WebDriver driver){

        // Get all product containers
        products = driver.findElements(inventoryItem);
        Collections.shuffle(products); //sort them randomly


        random = new Random();
        numberOfProductsToAdd = new Random().nextInt(Math.min(products.size(), 6)) + 1;

        for (int productIndex = 0; productIndex < numberOfProductsToAdd; productIndex++) {
            WebElement product = products.get(productIndex);
            System.out.println(product);

            String priceText = product.findElement(addedItemPrice).getText().replace("$", "");
            System.out.println(priceText);

            totalCalculatedPrice = Double.parseDouble(priceText) + totalCalculatedPrice;

            product.findElement(button).click(); //to add product to cart
        }
        System.out.println("Total Price=" + totalCalculatedPrice );
        return totalCalculatedPrice;
    }

    public void clickCartButton(){
        this.driver.findElement(cartButton).click();
    }

    //remove random products
    public double removeRandomProductsFromCart(WebDriver driver) {
        int addedCount = 0;

        // Count how many products currently have the "Remove" button (i.e., are in the cart)
        for (int i = 0; i < products.size(); i++) {
            // Re-fetch the product element to avoid stale element reference
            WebElement product = driver.findElements(inventoryItem).get(i);
            String buttonText = product.findElement(button).getText();
            if (buttonText.equalsIgnoreCase("Remove")) {
                addedCount++;
            }
        }

        // If no products are added, return the current total price as is
        if (addedCount == 0) return totalCalculatedPrice;

        // Randomly decide how many products to remove (at least 1, but not all)
        int numberToRemove = random.nextInt(Math.max(1, addedCount)) + 1;
        if (numberToRemove >= addedCount) {
            numberToRemove = addedCount - 1; // Ensure at least one product remains
        }

        int removed = 0;

        // Loop through products and remove the selected number
        for (int i = 0; i < products.size(); i++) {
            if (removed >= numberToRemove) break;

            // Re-fetch the product element each time to avoid stale element issues
            WebElement product = driver.findElements(inventoryItem).get(i);
            WebElement btn = product.findElement(button);

            // Only remove if the button text is "Remove"
            if (btn.getText().equalsIgnoreCase("Remove")) {
                String priceText = product.findElement(addedItemPrice).getText().replace("$", "");
                double price = Double.parseDouble(priceText);

                totalCalculatedPrice -= price;  // Subtract the product price from total
                btn.click();                    // Click the "Remove" button
                removed++;
            }
        }

        // Return the updated total price after removals
        return totalCalculatedPrice;
    }

    //methods for locators
    public List<WebElement> getAllProducts() {
        return driver.findElements(allProducts);
    }

    public String getProductName(WebElement product) {
        return product.findElement(allProductNames).getText();
    }

    public String getProductDesc(WebElement product) {
        return product.findElement(allProductDesc).getText();
    }

    public String getProductPrice(WebElement product) {
        return product.findElement(allProductPrices).getText();
    }

    public String getProductImageURL(WebElement product) {
        return product.findElement(allImages).getAttribute("src");
    }

}
