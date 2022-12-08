package net.media.assignment2;

import com.beust.jcommander.Parameter;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Parameters;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class FormElementActions {

    WebDriver driver;
    String baseUrlOfPage;
    WebElement firstNameField;
    WebElement lastNameField;
    WebElement emailField;
    WebElement genderMaleLabel;
    WebElement genderFemaleLabel;
    WebElement genderOtherLabel;
    WebElement mobileNumber;
    WebElement dateField;
    WebElement subjectsField;
    WebElement hobbySportLabel;
    WebElement hobbyReadingLabel;
    WebElement hobbyMusicLabel;
    WebElement currentAddressField;
    WebElement stateSelector;
    WebElement citySelector;
    Select monthSelector;
    Select yearSelector;
    WebElement daySelector;
    WebElement submitButton;
    WebElement nameFieldOnModal;
    WebElement emailFieldOnModal;
    WebElement genderOnModal;
    WebElement mobileNumberOnModal;
    WebElement dateFieldOnModal;
    WebElement subjectsFieldOnModal;
    WebElement hobbiesOnModal;
    WebElement currentAddressFieldOnModal;
    WebElement stateAndCityOnModal;
    WebElement closeModalButton;
    WebElement genderMale;
    WebElement genderFemale;
    WebElement genderOther;
    WebElement hobbySport;
    WebElement hobbyReading;
    WebElement hobbyMusic;

    WebDriverWait wait;
    LocalDateTime now;
    LocalDateTime newDate;
    DateTimeFormatter dtf;
    JavascriptExecutor jse;


    public void instantiateElements() {
        firstNameField = driver.findElement(By.cssSelector("input[id=firstName]"));
        lastNameField = driver.findElement(By.cssSelector("input[id=lastName]"));
        emailField = driver.findElement(By.cssSelector("input[id=userEmail]"));
        mobileNumber = driver.findElement(By.cssSelector("input[id=userNumber]"));
        dateField = driver.findElement(By.cssSelector("input[id=dateOfBirthInput]"));
        subjectsField = driver.findElement(By.cssSelector("input[id=subjectsInput]"));
        currentAddressField = driver.findElement(By.cssSelector("textarea[id=currentAddress]"));
        stateSelector = driver.findElement(By.cssSelector("input[id=\"react-select-3-input\"]"));
        citySelector = driver.findElement(By.cssSelector("div[class=\" css-1fhf3k1-control\"]"));
        submitButton = driver.findElement(By.cssSelector("button[id=\"submit\"]"));
        genderMaleLabel = driver.findElement(By.cssSelector("label[for=\"gender-radio-1\"]"));
        genderFemaleLabel = driver.findElement(By.cssSelector("label[for=\"gender-radio-2\"]"));
        genderOtherLabel = driver.findElement(By.cssSelector("label[for=\"gender-radio-3\"]"));
        hobbySportLabel = driver.findElement(By.cssSelector("label[for=\"hobbies-checkbox-1\"]"));
        hobbyReadingLabel = driver.findElement(By.cssSelector("label[for=\"hobbies-checkbox-2\"]"));
        hobbyMusicLabel = driver.findElement(By.cssSelector("label[for=\"hobbies-checkbox-3\"]"));
        genderMale = driver.findElement(By.cssSelector("#gender-radio-1"));
        genderFemale = driver.findElement(By.cssSelector("#gender-radio-2"));
        genderOther = driver.findElement(By.cssSelector("#gender-radio-3"));
        hobbySport = driver.findElement(By.cssSelector("#hobbies-checkbox-1"));
        hobbyReading = driver.findElement(By.cssSelector("#hobbies-checkbox-2"));
        hobbyMusic = driver.findElement(By.cssSelector("#hobbies-checkbox-3"));
        wait = new WebDriverWait(driver, 1);
    }

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
        driver.manage().window().maximize();
        Dimension dimension = new Dimension(3840, 2160);
        driver.manage().window().setSize(dimension);

        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
        instantiateElements();
        now = LocalDateTime.now();
        jse = (JavascriptExecutor) driver;
    }


    public void dateSelector(String dateToBeSet) {
        dateField.sendKeys(Keys.ARROW_DOWN);
        monthSelector = new Select(driver.findElement(By.cssSelector("select[class=\"react-datepicker__month-select\"]")));
        monthSelector.selectByVisibleText(dateToBeSet.substring(3, dateToBeSet.length() - 5));
        yearSelector = new Select(driver.findElement(By.cssSelector("select[class=\"react-datepicker__year-select\"]")));
        yearSelector.selectByVisibleText(dateToBeSet.substring(dateToBeSet.length() - 4));
        daySelector =  driver.findElement(By.cssSelector("div.react-datepicker__day.react-datepicker__day--0"+ dateToBeSet.substring(0, 2) +":not(.react-datepicker__day--outside-month)"));
        jse.executeScript("arguments[0].click();", daySelector);
    }

    public void dropDownSelector(String stateToSelect) {
        HashMap<String, String> stateToSelectorIdMapping = new HashMap<String, String>();
        stateToSelectorIdMapping.put("NCR", "div[id=\"react-select-3-option-0\"]");
        stateToSelectorIdMapping.put("Uttar Pradesh", "div[id=\"react-select-3-option-1\"]");
        stateToSelectorIdMapping.put("Haryana", "div[id=\"react-select-3-option-2\"]");
        stateToSelectorIdMapping.put("Rajasthan", "div[id=\"react-select-3-option-3\"]");
        stateSelector.sendKeys(Keys.ARROW_DOWN);
        jse.executeScript("arguments[0].click();", stateToSelectorIdMapping.get(stateToSelect));

    }

    public String getAndFormatCurrentDate(int formatType) {
        if(formatType == 1) {
            dtf = DateTimeFormatter.ofPattern("dd MMMM yyyy");
        }
        if(formatType == 2){
            dtf = DateTimeFormatter.ofPattern("dd MMM yyyy");
        }
        if(formatType == 3){
            dtf = DateTimeFormatter.ofPattern("dd/mm/yyyy");
        }
        return dtf.format(now);
    }

    public String getFutureDate(int formatType) {
        newDate = now.plusHours(24);
        if(formatType == 1) {
            dtf = DateTimeFormatter.ofPattern("dd MMMM yyyy");
        }
        if(formatType == 2){
            dtf = DateTimeFormatter.ofPattern("dd MMM yyyy");
        }
        return dtf.format(newDate);
    }

    public String getCssValue(WebElement elementToCheck, String cssValueToGet){
        return (elementToCheck.getCssValue(cssValueToGet));
    }

    public void fillAndSubmitForm(String firstName, String lastName, String email, String gender, String mobileNumber, String hobbies, String currentAddress){
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        firstNameField.clear();
        firstNameField.sendKeys(firstName);
        lastNameField.clear();
        lastNameField.sendKeys(lastName);
        emailField.clear();
        emailField.sendKeys(email);
        this.mobileNumber.clear();
        this.mobileNumber.sendKeys(mobileNumber);
        currentAddressField.clear();
        currentAddressField.sendKeys(currentAddress);

        switch(gender){
           case "Male":
               genderMale.sendKeys(Keys.SPACE);
               break;
           case "Female":
               genderFemale.sendKeys(Keys.SPACE);
               break;
           case "Other":
               genderOther.sendKeys(Keys.SPACE);
               break;
       }

        if(hobbies.contains("Sports")){
           hobbySport.sendKeys(Keys.SPACE);
        }
        if(hobbies.contains("Reading")){
            hobbyReading.sendKeys(Keys.SPACE);
        }
        if(hobbies.contains("Music")){
            hobbyMusic.sendKeys(Keys.SPACE);
        }
        submitButton.sendKeys(Keys.ENTER);
    }

    public String[] getDetailsOnPopup(){
        instantiateModalElements();
        String detailsOnPopup[] ={nameFieldOnModal.getText(), emailFieldOnModal.getText(), genderOnModal.getText(), mobileNumberOnModal.getText(), dateFieldOnModal.getText(), subjectsFieldOnModal.getText(), hobbiesOnModal.getText(), currentAddressFieldOnModal.getText(), stateAndCityOnModal.getText()};
        return detailsOnPopup;
    }
    public void instantiateModalElements(){
        nameFieldOnModal= driver.findElement(By.xpath("/html/body/div[4]/div/div/div[2]/div/table/tbody/tr[1]/td[2]"));
        emailFieldOnModal = driver.findElement(By.xpath("/html/body/div[4]/div/div/div[2]/div/table/tbody/tr[2]/td[2]"));
        genderOnModal = driver.findElement(By.xpath("/html/body/div[4]/div/div/div[2]/div/table/tbody/tr[3]/td[2]"));
        mobileNumberOnModal = driver.findElement(By.xpath("/html/body/div[4]/div/div/div[2]/div/table/tbody/tr[4]/td[2]"));
        dateFieldOnModal = driver.findElement(By.xpath("/html/body/div[4]/div/div/div[2]/div/table/tbody/tr[5]/td[2]"));
        subjectsFieldOnModal = driver.findElement(By.xpath("/html/body/div[4]/div/div/div[2]/div/table/tbody/tr[6]/td[2]"));
        hobbiesOnModal = driver.findElement(By.xpath("/html/body/div[4]/div/div/div[2]/div/table/tbody/tr[7]/td[2]"));
        currentAddressFieldOnModal = driver.findElement(By.xpath("/html/body/div[4]/div/div/div[2]/div/table/tbody/tr[9]/td[2]"));
        stateAndCityOnModal = driver.findElement(By.xpath("/html/body/div[4]/div/div/div[2]/div/table/tbody/tr[10]/td[2]"));
        closeModalButton = driver.findElement(By.cssSelector("button[id=\"closeLargeModal\"]"));
    }

}

