package net.media.assignment3;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;


public class ShoppingCartCases {

    ElementActions actionObject = new ElementActions();
    SoftAssert softAssert;
    HashMap<String, String> categoryToSelector;
    String hostUrl;

    @BeforeTest
    @Parameters("hostUrl")
    public void setUp(String hostUrl) throws IOException {
        this.hostUrl = hostUrl;
        actionObject.setUp(hostUrl);
        categoryToSelector = new HashMap<String, String>();
        categoryToSelector.put("Phones", "a[onclick=\"byCat('phone')\"]");
        categoryToSelector.put("Laptops", "a[onclick=\"byCat('notebook')\"]");
        categoryToSelector.put("Monitors", "a[onclick=\"byCat('monitor')\"]");
    }

    @Test(priority = 1, dataProvider = "itemAvailabilityCheck")
    public void checkItemsAvailability(String listItem, String itemType){
        softAssert = new SoftAssert();
        softAssert.assertNotEquals(actionObject.itemCheckerOnPage(listItem), 0);
        softAssert.assertAll();
    }

    @Test(priority = 2, dataProvider = "itemAvailabilityCheck")
    public void checkItemsByCategory(String listItem, String itemType){ //here 2 cases are checked, 1 whether item is present in resp category and also it is not present in other category
        actionObject.checkingItemByCategory = true;
        softAssert = new SoftAssert();
        for (HashMap.Entry<String,String> entry : categoryToSelector.entrySet()) {
            WebElement tempCard = actionObject.driver.findElement(By.cssSelector("div[class=\"col-lg-4 col-md-6 mb-4\"]"));
            actionObject.wait.until(ExpectedConditions.visibilityOf(tempCard));
            actionObject.driver.findElement(By.cssSelector(entry.getValue())).click();
            actionObject.wait.until(ExpectedConditions.invisibilityOf(tempCard));
            if (itemType == entry.getKey()) {
                softAssert.assertNotEquals(actionObject.itemCheckerOnPage(listItem), 0, "Item not found in it's category listing");
            } else {
                softAssert.assertEquals(actionObject.itemCheckerOnPage(listItem), 0, "Item found in some other category listing");
            }
            softAssert.assertAll();
        }
        actionObject.checkingItemByCategory = false;
    }

    @Test(priority = 3, dataProvider = "itemAvailabilityCheck")
    public void addToCartAndCheck(String listItem, String itemCategory){
        SoftAssert softAssert = new SoftAssert();
        actionObject.driver.get(this.hostUrl);
        actionObject.addItemToCart(listItem, itemCategory, categoryToSelector);
        softAssert.assertTrue(actionObject.checkCartItems(listItem), "Item not found in cart: " + listItem);
        softAssert.assertAll();
    }

    @Test(priority = 4)
    public void totalCalculateCase(){
        actionObject.driver.get(this.hostUrl);
        actionObject.driver.findElement(By.linkText("Cart")).click();
        actionObject.wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#totalp")));
        List<WebElement> itemPrices = actionObject.driver.findElements(By.xpath("//*[@id=\"tbodyid\"]/tr[*]/td[3]"));

        int listItemSum = 0;
        for (WebElement itemPrice : itemPrices) {
            listItemSum = listItemSum + Integer.parseInt(itemPrice.getText());
        }
        int numberShownInTotal = Integer.parseInt(actionObject.driver.findElement(By.cssSelector(".panel-title")).getText());
        Assert.assertEquals(listItemSum, numberShownInTotal, "The price shown in total is incorrect");
    }

    @Test (priority = 5, dataProvider = "formFieldValues")
    public void placeOrderReceiptCheck(String nameValue, String countryValue, String cityValue, String creditCardValue, String monthValue, String yearValue){
        SoftAssert softAssert = new SoftAssert();
        actionObject.driver.findElement(By.linkText("Cart")).click();
        actionObject.wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#totalp")));
        int numberShownInTotal = Integer.parseInt(actionObject.driver.findElement(By.cssSelector("#totalp")).getText());
        actionObject.driver.findElement(By.cssSelector("button[class=\"btn btn-success\"]")).click();
        actionObject.fillOrderDetails(nameValue, countryValue, cityValue, creditCardValue, monthValue, yearValue);
        actionObject.driver.findElement(By.cssSelector("button[onclick=\"purchaseOrder()\"]")).click();
        actionObject.wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button[class=\"confirm btn btn-lg btn-primary\"]")));
        String purchaseReceiptText = actionObject.driver.findElement(By.cssSelector("p[class=\"lead text-muted \"]")).getText();
        int amountInReceipt = Integer.parseInt(purchaseReceiptText.substring(purchaseReceiptText.indexOf("Amount") + 8, purchaseReceiptText.indexOf(" USD")));
        String cardNumberInReceipt = purchaseReceiptText.substring(purchaseReceiptText.indexOf("Card Number:") + 13, purchaseReceiptText.indexOf("Name")-1);
        String nameInReceipt = purchaseReceiptText.substring(purchaseReceiptText.indexOf("Name:") + 6, purchaseReceiptText.indexOf("Date")-1);

        softAssert.assertEquals(numberShownInTotal, amountInReceipt, "The amount is not matching in the receipt");
        softAssert.assertEquals(cardNumberInReceipt, creditCardValue, "The credit card number is not matching in the receipt");
        softAssert.assertEquals(nameInReceipt, nameValue, "The name is not matching in the receipt");
        softAssert.assertEquals(purchaseReceiptText.substring(purchaseReceiptText.indexOf("Date:") + 6), actionObject.getAndFormatCurrentDate());
        actionObject.driver.findElement(By.cssSelector("button[class=\"confirm btn btn-lg btn-primary\"]")).click();
        softAssert.assertAll();
    }

    @Test(priority = 6, dataProvider = "itemAvailabilityCheck")
    public void checkRemovalFromCart(String listItem, String itemCategory){
        softAssert = new SoftAssert();
        actionObject.driver.get(this.hostUrl);
        actionObject.addItemToCart(listItem, itemCategory, categoryToSelector);
        actionObject.removeFromCart(listItem);
        actionObject.wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//tbody[@id='tbodyid']//td[contains(text(),'"+listItem+"')]//following-sibling::td//child::a")));
        softAssert.assertFalse(actionObject.driver.getPageSource().contains(listItem), "Item not removed from cart");
        softAssert.assertAll();
    }

    @DataProvider
    public Object[][] itemAvailabilityCheck(){
        return new Object[][]{
                {"Samsung galaxy s6", "Phones"},
                {"Nokia lumia 1520", "Phones"},
                {"Nexus 6", "Phones"},
                {"Samsung galaxy s7", "Phones"},
                {"Iphone 6 32gb", "Phones"},
                {"Sony xperia z5", "Phones"},
                {"ASUS Full SD", "Monitors"},
                {"HTC One M9", "Phones"},
                {"Sony vaio i5", "Laptops"},
                {"Sony vaio i7", "Laptops"},
                {"ASUS Full HD", "Monitors"},
                {"MacBook air", "Laptops"},
                {"Dell i7 8gb", "Laptops"},
                {"2017 Dell 15.6 Inch", "Laptops"},
                {"MacBook Pro", "Laptops"},
                {"Apple monitor 24", "Monitors"},
        };
    }

    @DataProvider
    public Object[][] formFieldValues(){
        return new Object[][]{
                {"Dhruv Test", "India", "Mumbai", "12348907", "November", "2000"}};
        }

    @AfterTest
    public void tearDown(){
        actionObject.driver.quit();
    }

}

/*
notes:
- do not use .pageContains
- do not use xpath, same kaam cssSelectors se karo
* */