package net.media.test;

import net.media.assignment2.FormElementActions;
import org.openqa.selenium.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class FirstClass {

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
    String subjectSearchFirstResult;
    WebDriverWait wait;
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
    WebElement submitButton;
    JavascriptExecutor jse;
    SoftAssert softAssert;
    DateTimeFormatter dtf;



    @BeforeTest()
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
    public void elementVisibilityCheck(){
        softAssert = new SoftAssert();
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
        Assert.assertNotNull(stateSelector = driver.findElement(By.cssSelector("div[class=\" css-yk16xz-control\"]")));
        Assert.assertNotNull(citySelector = driver.findElement(By.cssSelector("div[class=\" css-1fhf3k1-control\"]")));
        Assert.assertNotNull(submitButton = driver.findElement(By.cssSelector("button[id=\"submit\"]")));
        }



    public void selectSubjectFromDropDown(String subjectSearchTerm){
        subjectsField.clear();
        subjectsField.sendKeys(subjectSearchTerm);
        driver.findElement(By.cssSelector("div[class=\"subjects-auto-complete__menu css-26l3qy-menu\"]")).isDisplayed();
        subjectSearchFirstResult = driver.findElement(By.cssSelector("div[id=\"react-select-2-option-0\"]")).getText();

    }



    @Test (dataProvider = "dataProviderDateCases")
    public void selectDate(String dateToBeSetForCases){
        dateSelector(dateToBeSetForCases);
        Assert.assertEquals(dateField.getAttribute("value"), dateToBeSetForCases);
    }


    public String checkRedColor(WebElement elementToCheck){
        return (elementToCheck.getCssValue("border-color"));
    }

    @Test
    public void subjectFields(){

        dropDownSelector("Rajasthan");
        dropDownSelector("NCR");
        dropDownSelector("Uttar Pradesh");
        dropDownSelector("Haryana");
        driver.findElement(By.cssSelector("button[id=\"submit\"]")).click();
    }


    public void dropDownSelector(String stateToSelect){
        HashMap<String, String> stateToSelectorIdMapping = new HashMap<String, String>();
        stateToSelectorIdMapping.put("NCR","div[id=\"react-select-3-option-0\"]");
        stateToSelectorIdMapping.put("Uttar Pradesh","div[id=\"react-select-3-option-1\"]");
        stateToSelectorIdMapping.put("Haryana","div[id=\"react-select-3-option-2\"]");
        stateToSelectorIdMapping.put("Rajasthan","div[id=\"react-select-3-option-3\"]");

        stateSelector.sendKeys(Keys.ARROW_DOWN);
        driver.findElement(By.cssSelector(stateToSelectorIdMapping.get(stateToSelect))).click();

    }



    @Test
    public void elementInterceptedTest(){
//        fillAndSubmitForm("dhruv", "Mehta", "dhr@fhr.com", "Other", "1234567890", "Sports Reading Music", "mumbai");
//        String value[] =getDetailsOnPopup();
//        for(int i=0; i< getDetailsOnPopup().length; i++) {
//            System.out.println(value[i]+" ");
//        }
        FormElementActions testObject = new FormElementActions();
        String testDate = "30 February 2022";
        System.out.println(testDate.substring(0, 2));
    }

    public void fillAndSubmitForm(String firstName, String lastName, String email, String gender, String mobileNumber, String hobbies, String currentAddress){
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
        nameFieldOnModal= driver.findElement(By.xpath("/html/body/div[4]/div/div/div[2]/div/table/tbody/tr[1]/td[2]"));
        emailFieldOnModal = driver.findElement(By.xpath("/html/body/div[4]/div/div/div[2]/div/table/tbody/tr[2]/td[2]"));
        genderOnModal = driver.findElement(By.xpath("/html/body/div[4]/div/div/div[2]/div/table/tbody/tr[3]/td[2]"));
        mobileNumberOnModal = driver.findElement(By.xpath("/html/body/div[4]/div/div/div[2]/div/table/tbody/tr[4]/td[2]"));
        dateFieldOnModal = driver.findElement(By.xpath("/html/body/div[4]/div/div/div[2]/div/table/tbody/tr[5]/td[2]"));
        subjectsFieldOnModal = driver.findElement(By.xpath("/html/body/div[4]/div/div/div[2]/div/table/tbody/tr[6]/td[2]"));
        hobbiesOnModal = driver.findElement(By.xpath("/html/body/div[4]/div/div/div[2]/div/table/tbody/tr[7]/td[2]"));
        currentAddressFieldOnModal = driver.findElement(By.xpath("/html/body/div[4]/div/div/div[2]/div/table/tbody/tr[8]/td[2]"));
        stateAndCityOnModal = driver.findElement(By.xpath("/html/body/div[4]/div/div/div[2]/div/table/tbody/tr[9]/td[2]"));
        closeModalButton = driver.findElement(By.cssSelector("button[id=\"closeLargeModal\"]"));
        String detailsOnPopup[] ={nameFieldOnModal.getText(), emailFieldOnModal.getText(), genderOnModal.getText(), mobileNumberOnModal.getText(), dateFieldOnModal.getText(), subjectsFieldOnModal.getText(), hobbiesOnModal.getText(), currentAddressFieldOnModal.getText(), stateAndCityOnModal.getText()};
        return detailsOnPopup;

    }

    @DataProvider
    public Object[][] dataProviderDateCases(){
        return new Object[][]{
                {"30 April 2022"},
                {"20 March 2022"},
                {"29 February 2024"},
                {"05 November 1997"}
        };
    }

    public String getCssValue(WebElement elementToCheck, String cssValueToGet){
        return (elementToCheck.getCssValue(cssValueToGet));
    }


    @Test(priority = 2, dependsOnMethods = "elementVisibilityCheck", dataProvider = "inputFieldDataTest")
    public void testMethod(String input, boolean isValid, String messageToShow, WebElement elementToAction){
        softAssert = new SoftAssert();
        elementToAction.clear();
        elementToAction.sendKeys(input);
        submitButton.sendKeys(Keys.ENTER);
        if(isValid) {
            softAssert.assertEquals(elementToAction.getAttribute("value"), input, messageToShow);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            softAssert.assertEquals(checkRedColor(elementToAction), "rgb(40, 167, 69)", "border color not as expected on submit");
        }
        if(!isValid) {
            softAssert.assertNotEquals(elementToAction.getAttribute("value"), input, messageToShow);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            softAssert.assertEquals(checkRedColor(elementToAction), "rgb(220, 53, 69)", "border color not as expected on submit");
        }
        elementToAction.clear();
        softAssert.assertAll();
    }

    @DataProvider
    public Object[][] inputFieldDataTest() {
        return new Object[][]{
                {"1", false, "Number accepted", firstNameField},
                {"@", false, "Special Char accepted", firstNameField},
                {"ABCabc", true, "Alphabets not accepted", firstNameField},
                {" ", false, "Blank Accepted", firstNameField},

                {"1", false, "Number accepted", lastNameField},
                {"@", false, "Special Char accepted", lastNameField},
                {"ABCabc", true, "Alphabets not accepted", lastNameField},
                {" ", false, "Blank Accepted", lastNameField},

                {"abc123", false, "Invalid Email accepted", emailField},
                {"dhrtest@dhr.com", true, "Valid email not accepted", emailField},

                {"abc", false, "Alphabets accepted in mobile number field", mobileNumber},
                {"@", false, "Special Char accepted", mobileNumber},
                {"12.34", false,"Decimal accepted", mobileNumber},
                {"-10", false,"Negative accepted", mobileNumber},
                {"12345678901", false, "More than 10 digits accepted", mobileNumber},
                {"12345", false, "Less than 10 digits accepted", mobileNumber},
                {"1234567890", true, "10 digits not accepted", mobileNumber},

                {"1", true, "Number not accepted", currentAddressField},
                {"@_()-+#&", true, "Special Char not accepted", currentAddressField},
                {"ABCabc", true, "Alphabets not accepted", currentAddressField},
                {" ", true, "Blank not accepted", currentAddressField},
                {"<>test</>", false, "Script accepted", currentAddressField},
                {"a-4534, test building, test city, country, pin12334", true, "Use case address not accepted", currentAddressField},
        };
    }

    public String getAndFormatCurrentDate(int formatType) {
        LocalDateTime now = LocalDateTime.now();
        if (formatType == 1) {
            dtf = DateTimeFormatter.ofPattern("dd MMMM yyyy");
        }
        if (formatType == 2) {
            dtf = DateTimeFormatter.ofPattern("dd MMM yyyy");
        }
        return dtf.format(now);
    }

    @Test
    public void testMethod2() {
        try {
            if (driver.findElement(By.cssSelector("#example-modal-sizes-title-lg")).isDisplayed()) {
                System.out.println("Performed");
            }
        }catch (NoSuchElementException noSuchElementException){
            System.out.println("exception");
        }
    }
//    @Test(dataProvider = "dataProviderDateCases")
    public void dateSelector(String dateToBeSet){
//        dateField.sendKeys(Keys.ARROW_DOWN);
//        monthSelector = new Select(driver.findElement(By.cssSelector("select[class=\"react-datepicker__month-select\"]")));
//        monthSelector.selectByVisibleText(dateToBeSet.substring(3, dateToBeSet.length() - 5));
//        yearSelector = new Select(driver.findElement(By.cssSelector("select[class=\"react-datepicker__year-select\"]")));
//        yearSelector.selectByVisibleText(dateToBeSet.substring(dateToBeSet.length() - 4));
//        if (driver.findElements(By.cssSelector("div[class=\"react-datepicker__day react-datepicker__day--0" + dateToBeSet.substring(0, 2) + " react-datepicker__day--selected react-datepicker__day--today react-datepicker__day--weekend\"]")).size() > 0) {
//            daySelector = driver.findElement(By.cssSelector("div[class=\"react-datepicker__day react-datepicker__day--0" + dateToBeSet.substring(0, 2) + " react-datepicker__day--selected react-datepicker__day--today react-datepicker__day--weekend\""));
//        } else if (driver.findElements(By.cssSelector("div[class=\"react-datepicker__day react-datepicker__day--0" + dateToBeSet.substring(0, 2) + "\"]")).size() > 0) {
//            daySelector = driver.findElement(By.cssSelector("div[class=\"react-datepicker__day react-datepicker__day--0" + dateToBeSet.substring(0, 2) + "\"]"));
//        } else if (driver.findElements(By.cssSelector("div[class=\"react-datepicker__day react-datepicker__day--0" + dateToBeSet.substring(0, 2) + " react-datepicker__day--weekend\"]")).size() > 0) {
//            daySelector = driver.findElement(By.cssSelector("div[class=\"react-datepicker__day react-datepicker__day--0" + dateToBeSet.substring(0, 2) + " react-datepicker__day--weekend\"]"));
//        }
//        if(daySelector.isDisplayed()){
//            jse.executeScript("arguments[0].click();", daySelector);
//        }else dateField.sendKeys(Keys.ESCAPE);

        dateField.sendKeys(Keys.ARROW_DOWN);
        monthSelector = new Select(driver.findElement(By.cssSelector("select[class=\"react-datepicker__month-select\"]")));
        monthSelector.selectByVisibleText(dateToBeSet.substring(3, dateToBeSet.length() - 5));
        yearSelector = new Select(driver.findElement(By.cssSelector("select[class=\"react-datepicker__year-select\"]")));
        yearSelector.selectByVisibleText(dateToBeSet.substring(dateToBeSet.length() - 4));
//        if (driver.findElements(By.cssSelector("div[class=\"react-datepicker__day react-datepicker__day--0" + dateToBeSet.substring(0, 2) + "\"]")).size() > 0) {
//            daySelector = driver.findElement(By.cssSelector("div[class=\"react-datepicker__day react-datepicker__day--0" + dateToBeSet.substring(0, 2) + "\"]"));
//        } else if (driver.findElements(By.cssSelector("div[class=\"react-datepicker__day react-datepicker__day--0" + dateToBeSet.substring(0, 2) + " react-datepicker__day--weekend\"]")).size() > 0) {
//            daySelector = driver.findElement(By.cssSelector("div[class=\"react-datepicker__day react-datepicker__day--0" + dateToBeSet.substring(0, 2) + " react-datepicker__day--weekend\"]"));
//        } else if (driver.findElements(By.cssSelector("div[class=\"react-datepicker__day react-datepicker__day--0" + dateToBeSet.substring(0, 2) + " react-datepicker__day--selected react-datepicker__day--today react-datepicker__day--weekend\"]")).size() > 0) {
//            daySelector = driver.findElement(By.cssSelector("div[class=\"react-datepicker__day react-datepicker__day--0" + dateToBeSet.substring(0, 2) + " react-datepicker__day--selected react-datepicker__day--today react-datepicker__day--weekend\""));
//        }
        daySelector =  driver.findElement(By.cssSelector("div.react-datepicker__day.react-datepicker__day--0"+ dateToBeSet.substring(0, 2) +":not(.react-datepicker__day--outside-month)"));

        jse.executeScript("arguments[0].click();", daySelector);
    }

    public void stateSelectingMethod(String stateToSelect) {
        HashMap<String, String> stateToSelectorIdMapping = new HashMap<String, String>();
        stateToSelectorIdMapping.put("NCR", "div[id=\"react-select-3-option-0\"]");
        stateToSelectorIdMapping.put("Uttar Pradesh", "div[id=\"react-select-3-option-1\"]");
        stateToSelectorIdMapping.put("Haryana", "div[id=\"react-select-3-option-2\"]");
        stateToSelectorIdMapping.put("Rajasthan", "div[id=\"react-select-3-option-3\"]");

        stateSelector.sendKeys(Keys.ENTER);
//        jse.executeScript("arguments[0].click();", stateSelector);
        jse.executeScript("arguments[0].click();", stateToSelectorIdMapping.get(stateToSelect));

    }


}

