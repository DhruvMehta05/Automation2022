package net.media.test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class PractiseFormActions {

    WebDriver driver;
    String baseUrlOfPage;
    WebElement firstNameField;
    WebElement lastNameField;
    WebElement emailField;
    WebElement genderMale;
    WebElement genderFemale;
    WebElement genderOther;
    WebElement mobileNumber;
    WebElement dateField;
    WebElement subjectsField;
    WebElement hobbySport;
    WebElement hobbyReading;
    WebElement hobbyMusic;
    WebElement currentAddressField;
    WebElement stateSelector;
    WebElement citySelector;
    Select monthSelector;
    Select yearSelector;
    WebElement daySelector;

    public PractiseFormActions() {
        firstNameField = driver.findElement(By.cssSelector("input[id=firstName]"));
    }

    @BeforeTest()
    public void setUp() throws IOException {
        Properties prop=new Properties();
        FileInputStream ip= new FileInputStream("C:\\Automation2022\\config.properties");
        prop.load(ip);
        if(prop.getProperty("browser").equals("firefox")){
            System.setProperty("webdriver.gecko.driver", "C:\\selenium files\\geckodriver.exe");
            driver = new FirefoxDriver();
        }
        else {
            System.setProperty("webdriver.chrome.driver", "C:\\selenium files\\chromedriver.exe");
            driver = new ChromeDriver();
        }
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @Test (priority = 1)
    public void accessHostUrl(String baseUrl){
        baseUrlOfPage = baseUrl;
        driver.get(baseUrl);
        Assert.assertEquals(driver.getCurrentUrl(), baseUrl);
    }

    @Test (priority = 2, dependsOnMethods = "accessHostUrl")
    public void pageElementsChecker(){
        Assert.assertNotNull(firstNameField = driver.findElement(By.cssSelector("input[id=firstName]")));
        Assert.assertNotNull(lastNameField = driver.findElement(By.cssSelector("input[id=lastName]")));
        Assert.assertNotNull(emailField = driver.findElement(By.cssSelector("input[id=userEmail]")));
        Assert.assertNotNull(genderMale = driver.findElement(By.cssSelector("input[id=gender-radio-1]")));
        Assert.assertNotNull(genderFemale = driver.findElement(By.cssSelector("input[id=gender-radio-2]")));
        Assert.assertNotNull(genderOther = driver.findElement(By.cssSelector("input[id=gender-radio-3]")));
        Assert.assertNotNull(mobileNumber = driver.findElement(By.cssSelector("input[id=userNumber]")));
        Assert.assertNotNull(dateField = driver.findElement(By.cssSelector("input[id=dateOfBirthInput]")));
        Assert.assertNotNull(subjectsField = driver.findElement(By.cssSelector("input[id=subjectsInput]")));
        Assert.assertNotNull(hobbySport = driver.findElement(By.cssSelector("input[id=\"hobbies-checkbox-1\"]")));
        Assert.assertNotNull(hobbyReading = driver.findElement(By.cssSelector("input[id=\"hobbies-checkbox-2\"]")));
        Assert.assertNotNull(hobbyMusic = driver.findElement(By.cssSelector("input[id=\"hobbies-checkbox-3\"]")));
        Assert.assertNotNull(currentAddressField = driver.findElement(By.cssSelector("textarea[id=currentAddress]")));
        Assert.assertNotNull(stateSelector = driver.findElement(By.cssSelector("input[id=\"react-select-3-input\"]")));
        Assert.assertNotNull(citySelector = driver.findElement(By.cssSelector("input[id=\"react-select-4-input\"]")));

    }

    public void dateSelector(String dateToBeSet){
        dateField.click();
        monthSelector = new Select(driver.findElement(By.cssSelector("select[class=\"react-datepicker__month-select\"]")));
        monthSelector.selectByVisibleText(dateToBeSet.substring(3, dateToBeSet.length()-5));
        yearSelector = new Select(driver.findElement(By.cssSelector("select[class=\"react-datepicker__year-select\"]")));
        yearSelector.selectByVisibleText(dateToBeSet.substring(dateToBeSet.length()-4, dateToBeSet.length()));
        daySelector = driver.findElement(By.cssSelector("div[class=\"react-datepicker__day react-datepicker__day--0"+dateToBeSet.substring(0,2)+"\"]"));
        daySelector.click();
    }

    public String checkRedColor(WebElement elementToCheck){
        return (elementToCheck.getCssValue("border-color"));
    }

}


