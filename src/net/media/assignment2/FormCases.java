package net.media.assignment2;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;

import java.io.IOException;
import org.openqa.selenium.NoSuchElementException;

public class FormCases {

    FormElementActions actionObject = new FormElementActions();
    String baseUrlOfPage;
    SoftAssert softAssert;
    JavascriptExecutor jse;

    @BeforeTest
    @Parameters({"hostUrl"})
    public void setUpAndHost(String hostUrl) throws IOException {
        baseUrlOfPage = hostUrl;
        actionObject.setUp(hostUrl);
    }

    @Test (priority = 1)
    public void elementChecking() {
        softAssert = new SoftAssert();
        softAssert.assertNotNull(actionObject.firstNameField.isDisplayed());
        softAssert.assertNotNull(actionObject.lastNameField.isDisplayed());
        softAssert.assertNotNull(actionObject.emailField.isDisplayed());
        softAssert.assertNotNull(actionObject.genderMale.isDisplayed());
        softAssert.assertNotNull(actionObject.genderFemale.isDisplayed());
        softAssert.assertNotNull(actionObject.genderOther.isDisplayed());
        softAssert.assertNotNull(actionObject.mobileNumber.isDisplayed());
        softAssert.assertNotNull(actionObject.dateField.isDisplayed());
        softAssert.assertNotNull(actionObject.subjectsField.isDisplayed());
        softAssert.assertNotNull(actionObject.hobbySport.isDisplayed());
        softAssert.assertNotNull(actionObject.hobbyReading.isDisplayed());
        softAssert.assertNotNull(actionObject.hobbyMusic.isDisplayed());
        softAssert.assertNotNull(actionObject.currentAddressField.isDisplayed());
        softAssert.assertNotNull(actionObject.stateSelector.isDisplayed());
        softAssert.assertNotNull(actionObject.citySelector.isDisplayed());
        softAssert.assertNotNull(actionObject.submitButton.isDisplayed());
        softAssert.assertAll();
    }

    //Add label checker test cases here; yet to be done
    @Test(priority = 2)
    public void labelsChecker(){
        softAssert = new SoftAssert();
        softAssert.assertEquals(actionObject.driver.findElement(By.cssSelector("#userName-label")).getText(), "Name", "Incorrect label found for Name field");
        softAssert.assertEquals(actionObject.driver.findElement(By.cssSelector("#userEmail-label")).getText(), "Email", "Incorrect label found for Email field");
        softAssert.assertEquals(actionObject.driver.findElement(By.xpath("//*[@id=\"genterWrapper\"]/div[1]")).getText(), "Gender", "Incorrect label found for gender field"); //gender label
        softAssert.assertEquals(actionObject.driver.findElement(By.cssSelector("#userNumber-label")).getText(), "Mobile(10 Digits)", "Incorrect label found for Mobile number field");
        softAssert.assertEquals(actionObject.driver.findElement(By.cssSelector("#dateOfBirth-label")).getText(), "Date of Birth", "Incorrect label found for DOB field");
        softAssert.assertEquals(actionObject.driver.findElement(By.cssSelector("#subjectsWrapper #subjects-label")).getText(), "Subjects", "Incorrect label found for Subjects field"); //subjects label
        softAssert.assertEquals(actionObject.driver.findElement(By.cssSelector("#hobbiesWrapper #subjects-label")).getText(), "Hobbies", "Incorrect label found for Hobbies field"); //o
        softAssert.assertEquals(actionObject.driver.findElement(By.xpath("/html/body/div[2]/div/div/div[2]/div[2]/div[2]/form/div[8]/div[1]/label")).getText(), "Picture", "Incorrect label found for Picture field");
        softAssert.assertEquals(actionObject.driver.findElement(By.cssSelector("#currentAddress-label")).getText(), "Current Address", "Incorrect label found for Address field");
        softAssert.assertEquals(actionObject.driver.findElement(By.cssSelector("#stateCity-label")).getText(), "State and City", "Incorrect label found for State and City field");
    }


    @Test (priority = 2, dependsOnMethods = "elementChecking", dataProvider = "inputFieldData")
    public void inputFieldsTestCases(String input, boolean isValid, String messageToShow, WebElement elementToAction){
        softAssert = new SoftAssert();
        elementToAction.clear();
        elementToAction.sendKeys(input);
        actionObject.submitButton.sendKeys(Keys.ENTER);
        if(isValid) {
            softAssert.assertEquals(elementToAction.getAttribute("value"), input, messageToShow);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            softAssert.assertEquals(actionObject.getCssValue(elementToAction,"border-color"), "rgb(40, 167, 69)", "border color not as expected on submit");
        }
        if(!isValid) {
            softAssert.assertNotEquals(elementToAction.getAttribute("value"), input, messageToShow);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            softAssert.assertEquals(actionObject.getCssValue(elementToAction, "border-color"), "rgb(220, 53, 69)", "border color not as expected on submit");
        }
        elementToAction.clear();
        softAssert.assertAll();
    }

    @DataProvider
    public Object[][] inputFieldData() {
        return new Object[][]{
                {"1", false, "Number accepted", actionObject.firstNameField},
                {"@", false, "Special Char accepted", actionObject.firstNameField},
                {"ABCabc", true, "Alphabets not accepted", actionObject.firstNameField},
                {" ", false, "Blank Accepted", actionObject.firstNameField},
                {"rqwerqweqweefqwefwefwqefqwefqwefwefwefwefwefwqefwqfew", false, "Name too large Accepted", actionObject.firstNameField},
                {"<>test</>", false, "Script accepted", actionObject.firstNameField},

                {"1", false, "Number accepted", actionObject.lastNameField},
                {"@", false, "Special Char accepted", actionObject.lastNameField},
                {"ABCabc", true, "Alphabets not accepted", actionObject.lastNameField},
                {" ", false, "Blank Accepted", actionObject.lastNameField},
                {"rqwerqweqweefqwefwefwqefqwefqwefwefwefwefwefwqefwqfew", false, "Name too large Accepted", actionObject.lastNameField},
                {"<>test</>", false, "Script accepted", actionObject.lastNameField},

                {"abc123", false, "Invalid Email accepted", actionObject.emailField},
                {"dhrtest@dhr.com", true, "Valid email not accepted", actionObject.emailField},

                {"abc", false, "Alphabets accepted in mobile number field", actionObject.mobileNumber},
                {"@", false, "Special Char accepted", actionObject.mobileNumber},
                {"12.34", false,"Decimal accepted", actionObject.mobileNumber},
                {"-10", false,"Negative accepted", actionObject.mobileNumber},
                {"12345678901", false, "More than 10 digits accepted", actionObject.mobileNumber},
                {"12345", false, "Less than 10 digits accepted", actionObject.mobileNumber},
                {"1234567890", true, "10 digits not accepted", actionObject.mobileNumber},

                {"1", true, "Number not accepted", actionObject.currentAddressField},
                {"@_()-+#&", true, "Special Char not accepted", actionObject.currentAddressField},
                {"ABCabc", true, "Alphabets not accepted", actionObject.currentAddressField},
                {" ", true, "Blank not accepted", actionObject.currentAddressField},
                {"<>test</>", false, "Script accepted", actionObject.currentAddressField},
                {"a-4534, test building, test city, country, pin12334", true, "Use case address not accepted", actionObject.currentAddressField},
        };
    }


    @Test(priority = 3, dependsOnMethods = "elementChecking", dataProvider = "radioAndCheckboxData")
    public void radioAndCheckboxChecker(String optionType, WebElement firstOption, WebElement secondOption, WebElement thirdOption) throws IOException {
        softAssert = new SoftAssert();
        jse = (JavascriptExecutor)actionObject.driver;

        //Border color case for none selected
        actionObject.submitButton.sendKeys(Keys.ENTER);
        if(optionType == "Radio") {
            softAssert.assertEquals(actionObject.getCssValue(actionObject.genderMaleLabel, "color"), "rgba(220, 53, 69, 1)");
            softAssert.assertEquals(actionObject.getCssValue(actionObject.genderFemaleLabel, "color"), "rgba(220, 53, 69, 1)");
            softAssert.assertEquals(actionObject.getCssValue(actionObject.genderOtherLabel, "color"), "rgba(220, 53, 69, 1)");
        }
        if(optionType == "Checkbox") {
            softAssert.assertEquals(actionObject.getCssValue(actionObject.hobbySportLabel, "color"), "rgba(40, 167, 69, 1)");
            softAssert.assertEquals(actionObject.getCssValue(actionObject.hobbyReadingLabel, "color"), "rgba(40, 167, 69, 1)");
            softAssert.assertEquals(actionObject.getCssValue(actionObject.hobbyMusicLabel, "color"), "rgba(40, 167, 69, 1)");
        }
        //Cases on selection
        jse.executeScript("arguments[0].click();", firstOption);
        softAssert.assertTrue(firstOption.isSelected(), "Option1 not selected on click");
        jse.executeScript("arguments[0].click();", secondOption);
        softAssert.assertTrue(secondOption.isSelected(), "Option2 not selected on click");
        jse.executeScript("arguments[0].click();", thirdOption);
        softAssert.assertTrue(thirdOption.isSelected(), "Option3 not selected on click");
        if(optionType == "Radio") {
            softAssert.assertEquals(actionObject.getCssValue(actionObject.genderMaleLabel, "color"), "rgba(40, 167, 69, 1)");
            softAssert.assertEquals(actionObject.getCssValue(actionObject.genderFemaleLabel, "color"), "rgba(40, 167, 69, 1)");
            softAssert.assertEquals(actionObject.getCssValue(actionObject.genderOtherLabel, "color"), "rgba(40, 167, 69, 1)");
            softAssert.assertFalse(firstOption.isSelected(), "Multiple options selected");
            softAssert.assertFalse(secondOption.isSelected(), "Multiple options selected");
            softAssert.assertAll();
        }
        if(optionType == "Checkbox") {
            softAssert.assertEquals(actionObject.getCssValue(actionObject.hobbySportLabel, "color"), "rgba(40, 167, 69, 1)");
            softAssert.assertEquals(actionObject.getCssValue(actionObject.hobbyReadingLabel, "color"), "rgba(40, 167, 69, 1)");
            softAssert.assertEquals(actionObject.getCssValue(actionObject.hobbyMusicLabel, "color"), "rgba(40, 167, 69, 1)");
            softAssert.assertTrue(firstOption.isSelected(), "Multiple options not selected");
            softAssert.assertTrue(secondOption.isSelected(), "Multiple options not selected");
            softAssert.assertAll();
        }
    }

    @DataProvider
    public Object[][] radioAndCheckboxData(){
        return new Object[][]{
                {"Radio", actionObject.genderMale, actionObject.genderFemale, actionObject.genderOther},
                {"Checkbox", actionObject.hobbySport, actionObject.hobbyReading, actionObject.hobbyMusic}
        };
    }

    @Test(priority = 4, dependsOnMethods = "elementChecking", dataProvider = "dateFieldCases")
    public void datePickerCases(String dateToBeSelected, String dateToBeExpected, boolean isValid, String messageToShow){

        int year = Integer.parseInt(dateToBeSelected.substring(dateToBeSelected.length() - 4));
        int date = Integer.parseInt(dateToBeSelected.substring(0, 2));

            try {
                actionObject.dateSelector(dateToBeSelected);
            }catch(NoSuchElementException noSuchElementException){
                if (year < 1900 || date < 1 || date > 31 || year >2100){
                    Assert.assertTrue(true, "Out of bound date inputted is selected");
                }else Assert.assertTrue(false, noSuchElementException.getMessage());
            }
        if(isValid) {
            Assert.assertEquals(actionObject.dateField.getAttribute("value"), dateToBeExpected, messageToShow);
        }
        if(!isValid){
                Assert.assertNotEquals(actionObject.dateField.getAttribute("value"), dateToBeExpected, messageToShow);
        }

    }

    @DataProvider
    public Object[][] dateFieldCases(){
        return new Object[][]{
                {actionObject.getAndFormatCurrentDate(1), actionObject.getAndFormatCurrentDate(2), true, "Current date not accepted"},
                {"31 December 1899", "31 Dec 1899", false, "Date before 1990 accepted"},
                {"29 February 2004", "29 Feb 2004", true, "Leap year date not selected"},
                {actionObject.getFutureDate(1), actionObject.getFutureDate(2), false, "Future date accepted"}
        };
    }


    @Test(dependsOnMethods = "elementChecking", priority = 5, dataProvider = "submitWithoutMandatoryCheck")
    public void submitWithoutMandatory(String firstName, String lastName, String email, String gender, String mobileNumber, String hobbies, String currentAddress, Boolean isValid){
        softAssert = new SoftAssert();
        actionObject.driver.navigate().refresh();
        actionObject.instantiateElements();
//        actionObject.wait.until(ExpectedConditions.visibilityOf(actionObject.submitButton));
        actionObject.fillAndSubmitForm(firstName, lastName, email, gender, mobileNumber, hobbies, currentAddress);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        try {
            if (actionObject.driver.findElement(By.cssSelector("#example-modal-sizes-title-lg")).isDisplayed()) {
                if(isValid){
                    softAssert.assertTrue(true);
                }
                if(!isValid){
                    softAssert.assertFalse(true, "Modal opens on submitting without mandatory field(s)");
                }
            }
        }catch (NoSuchElementException noSuchElementException){
            if(isValid){
                softAssert.assertTrue(false, "Modal doesn't open on submitting all mandatory fields");
            }
            if(!isValid){
                softAssert.assertTrue(true);
            }
        }
        softAssert.assertAll();
    }

    @DataProvider
    public Object[][] submitWithoutMandatoryCheck(){
        return new Object[][]{
                {"dhruv", "Mehta", "dhr@fhr.com", "Female", "1234567890", "Sports, Reading", "mumbai andheri test", true},
                {"dhruv", "", "dhr@fhr.com", "Female", "1234567890", "Sports, Reading", "mumbai andheri test", false},
                {"", "Mehta", "dhr@fhr.com", "Female", "1234567890", "Sports, Reading", "mumbai andheri test", false},
                {"dhruv", "Mehta", "", "Female", "1234567890", "Sports, Reading", "mumbai andheri test", false},
                {"dhruv", "Mehta", "dhr@fhr.com", "", "1234567890", "Sports, Reading", "mumbai andheri test", false},
                {"dhruv", "Mehta", "dhr@fhr.com", "Female", "", "Sports, Reading", "mumbai andheri test", false},
                {"dhruv", "Mehta", "dhr@fhr.com", "Female", "1234567890", "", "mumbai andheri test", true},
                {"dhruv", "Mehta", "dhr@fhr.com", "Female", "1234567890", "Sports, Reading", "", true}
        };
    }

    @Test(dependsOnMethods = "elementChecking", priority = 6, dataProvider = "modalDataCheck")
    public void checkTheFormInputOnModal(String firstName, String lastName, String email, String gender, String mobileNumber, String hobbies, String currentAddress, Boolean isValid){
            softAssert = new SoftAssert();
            actionObject.driver.navigate().refresh();
            actionObject.instantiateElements();
            actionObject.fillAndSubmitForm(firstName, lastName, email, gender, mobileNumber, hobbies, currentAddress);
            String[] dataOnModal = actionObject.getDetailsOnPopup();
            String fullNameText = dataOnModal[0];
            softAssert.assertEquals(firstName, fullNameText.substring(0, firstName.length()));
            softAssert.assertEquals(lastName, fullNameText.substring(firstName.length() + 1));
            softAssert.assertEquals(mobileNumber, dataOnModal[3]);
            softAssert.assertEquals(email, dataOnModal[1]);
            softAssert.assertEquals(currentAddress, dataOnModal[7]);
            actionObject.closeModalButton.sendKeys(Keys.ENTER);
            softAssert.assertAll();
    }

    @DataProvider
    public Object[][] modalDataCheck(){
        return new Object[][]{
                {"Dhruv", "Mehta", "dhr@fhr.com", "Female", "1234567890", "Sports, Reading", "mumbai andheri test", true},
                {"Test", "Word", "dhr@fhr.com", "Female", "1234567890", "Sports, Reading", "mumbai andheri test", true},
                {"dhruv", "Mehta", "dhr@fhr.com", "Female", "1234567890", "", "mumbai andheri test", true},
                {"dhruv", "Mehta", "dhr@fhr.com", "Female", "1234567890", "Sports, Reading", "", true}
        };
    }

    @AfterTest
    public void tearDown(){
        actionObject.driver.quit();
    }

}
