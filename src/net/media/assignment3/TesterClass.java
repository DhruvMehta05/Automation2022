package net.media.assignment3;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.devtools.v85.indexeddb.model.Key;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class TesterClass {

    WebDriver driver;
    WebDriverWait wait;
    JavascriptExecutor jse;


    @BeforeTest
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "C:\\selenium files\\chromedriver.exe");
        //chrome.exe --remote-debugging-port=9222 --user-data-dir="C:\selenium files\ChromeProfile"

        ChromeOptions opt = new ChromeOptions();
//        opt.addArguments("--headless");
        opt.setExperimentalOption("debuggerAddress", "localhost:9222 ");
        driver = new ChromeDriver(opt);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        SoftAssert softAssert = new SoftAssert();
        wait = new WebDriverWait(driver, 10);
//        Dimension dimension = new Dimension(3840, 2160);
//        driver.manage().window().setSize(dimension);
        jse = (JavascriptExecutor) driver;
    }

    @Test
    public void searchByName() {
        for(int i =0; i < 5; i++) {
            driver.navigate().refresh();
            driver.findElement(By.cssSelector("input[name=email]")).sendKeys("dhrtest");
            Actions action = new Actions(driver);
            Action seriesOfAction = action.sendKeys(Keys.TAB).sendKeys(Keys.ENTER).build();
            seriesOfAction.perform();
            driver.findElement(By.cssSelector("button[type=\"submit\"]")).click();
            String validationMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("span[class=\"StyledText-sc-1sadyjn-0 cFRZLY\"]"))).getText();
            System.out.println(validationMessage);
        }
    }
}
