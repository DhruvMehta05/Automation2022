package net.media.test;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class SecondClass {

    private WebDriver driver;

    @BeforeClass
    public void setUp(){
        System.setProperty("webdriver.chrome.driver", "C:\\selenium files\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @Test
    public void verifyTitle(){
        driver.get("https://pubconsole.media.net");
        String actualTitle = driver.getTitle();
        Assert.assertEquals(actualTitle, "Login | PubConsole");
    }

    @Test
    public void checkLogo(){
        boolean logoDisplay = driver.findElement(By.cssSelector("div.logo")).isDisplayed();
        Assert.assertTrue(logoDisplay);
    }

    @Test
    public void test3(){
        driver.get("https://google.com");
    }
}
