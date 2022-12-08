package net.media.assignment3;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class ElementActions {

    WebDriver driver;
    WebDriverWait wait;
    boolean checkingItemByCategory = false;
    WebElement addToCartButton;
    Date dateObject;
    DateTimeFormatter dtf;
    LocalDateTime now;
    String hostUrl;
    String windowHandle;

    public void setUp(String hostUrl) throws IOException {
        Properties prop = new Properties();
        FileInputStream ip = new FileInputStream("C:\\Automation2022\\config.properties");
        prop.load(ip);
        if (prop.getProperty("browser").equals("firefox")) {
            System.setProperty("webdriver.gecko.driver", "C:\\selenium files\\geckodriver.exe");
            driver = new FirefoxDriver();
        } else {
            System.setProperty("webdriver.chrome.driver", "C:\\selenium files\\chromedriver.exe");
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--headless");
            driver = new ChromeDriver(options);
        }
        driver.get(hostUrl);
        windowHandle = driver.getWindowHandle();
        this.hostUrl = hostUrl;
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
        wait = new WebDriverWait(driver, 5);

        dateObject = new Date();
        now = LocalDateTime.now();
    }

    public int itemCheckerOnPage(String listItem){
        List<WebElement> elementIsPresent;
        elementIsPresent = driver.findElements(By.linkText(listItem));
        if(elementIsPresent.size()  == 0){
            if(!checkingItemByCategory) {
                driver.findElement(By.cssSelector(".page-link#next2")).click();
                elementIsPresent = driver.findElements(By.linkText(listItem));
                driver.navigate().refresh();
            }
        }
        return elementIsPresent.size();
    }

    public void addItemToCart(String itemName, String itemCategory, HashMap<String, String> categoryToSelector){
        driver.get(this.hostUrl);
        if (itemCheckerOnPage(itemName) != 0) {
            driver.findElement(By.cssSelector(categoryToSelector.get(itemCategory))).click();
            driver.findElement(By.linkText(itemName)).click();
            addToCartButton = driver.findElement(By.cssSelector("a[class=\"btn btn-success btn-lg\"]"));
            addToCartButton.click();
            wait.until(ExpectedConditions.alertIsPresent());
            driver.switchTo().alert().accept();
        }
    }

    public boolean checkCartItems (String itemName){
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Cart")));
        WebElement cartButton = driver.findElement(By.linkText("Cart"));
        boolean elementIsPresent;
        cartButton.click();
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//*[@class='success']//td[contains(text(),'"+itemName+"')]")));
            List<WebElement> e = driver.findElements(By.cssSelector("#tbodyid .success td:nth-child(2)"));
//            List<String> values = new ArrayList<>();
            for(WebElement e1 : e){
//                values.add(e1.getText());
                e1.getText().equals(itemName);
            }
        }catch(TimeoutException timeoutException){
            System.out.println(timeoutException);
        }
        elementIsPresent = driver.getPageSource().contains(itemName);
        return  elementIsPresent;
    }

    public void removeFromCart (String itemName){
        if (checkCartItems(itemName) == true){
            driver.findElement(By.xpath("//tbody[@id='tbodyid']//td[contains(text(),'"+itemName+"')]//following-sibling::td//child::a")).click();
        }else System.out.println("unable to remove as "+itemName+" not present in cart");
    }

   public void fillOrderDetails(String nameValue, String countryValue, String cityValue, String creditCardValue, String monthValue, String yearValue) {
        WebElement nameField = driver.findElement(By.cssSelector("#name"));
        WebElement countryField = driver.findElement(By.cssSelector("#country"));
        WebElement cityField = driver.findElement(By.cssSelector("#city"));
        WebElement creditCardField = driver.findElement(By.cssSelector("#card"));
        WebElement monthField = driver.findElement(By.cssSelector("#month"));
        WebElement yearField = driver.findElement(By.cssSelector("#year"));
       nameField.sendKeys(nameValue);
       countryField.sendKeys(countryValue);
       cityField.sendKeys(cityValue);
       creditCardField.sendKeys(creditCardValue);
       monthField.sendKeys(monthValue);
       yearField.sendKeys(yearValue);
   }

    public String getAndFormatCurrentDate() {
        now = LocalDateTime.now();
        dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return dtf.format(now);
    }

}
