package net.media.test;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import static org.testng.Assert.assertFalse;

public class CalcTester {

    WebDriver driver;
    SoftAssert softAssert;
    WebElement firstNumber;
    WebElement secondNumber;
    Select operationSelector;
    WebElement operationSelectorForDisplay;
    WebElement calculateButton;
    WebElement answerField;
    WebElement integersOnlyCheckbox;
    WebElement clearButton;
    WebDriverWait wait;
    WebElement errorMessage;
    String baseUrlOfPage;


    @BeforeTest
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
    @Parameters({"hostUrl"})
    public void accessUrl(String baseUrl){
        baseUrlOfPage = baseUrl;
        driver.get(baseUrl);
        //below code scrolls the page till the clear button is visible
        JavascriptExecutor js = (JavascriptExecutor) driver;
        clearButton = driver.findElement(By.cssSelector("input[id=clearButton]"));
        js.executeScript("arguments[0].scrollIntoView();", clearButton);
        wait = new WebDriverWait(driver, 5);

        firstNumber = driver.findElement(By.cssSelector("input[id=number1Field]"));
        secondNumber = driver.findElement(By.cssSelector("input[id=number2Field]"));
        operationSelector = new Select(driver.findElement(By.cssSelector("select[id=selectOperationDropdown]")));
        operationSelectorForDisplay = driver.findElement(By.cssSelector("select[id=selectOperationDropdown]"));
        calculateButton = driver.findElement(By.cssSelector("input[id=calculateButton]"));
        answerField = driver.findElement(By.cssSelector("input[id=numberAnswerField]"));
        integersOnlyCheckbox = driver.findElement(By.cssSelector("input[id=integerSelect]"));
    }

    @Test (priority = 2, dependsOnMethods = "accessUrl")
    public void elementDisplayValidator(){
        Assert.assertTrue(firstNumber.isDisplayed());
        Assert.assertTrue(secondNumber.isDisplayed());
        Assert.assertTrue(operationSelectorForDisplay.isDisplayed());
        Assert.assertTrue(calculateButton.isDisplayed());
        Assert.assertTrue(answerField.isDisplayed());
        Assert.assertTrue(integersOnlyCheckbox.isDisplayed());
        Assert.assertTrue(clearButton.isDisplayed());
        }



    @Test (priority = 3, dependsOnMethods = "accessUrl")
    public void fieldLabelChecker(){
        softAssert = new SoftAssert();
        softAssert.assertEquals(driver.findElement(By.xpath("//*[@id=\"calcForm\"]/div[1]/div[1]/label")).getText(), "First number");
        softAssert.assertEquals(driver.findElement(By.xpath("//*[@id=\"calcForm\"]/div[2]/div[1]/label")).getText(), "Second number");
        softAssert.assertEquals(driver.findElement(By.xpath("//*[@id=\"calcForm\"]/div[3]/div[1]/label")).getText(), "Operation");
        softAssert.assertEquals(driver.findElement(By.xpath("//*[@id=\"answerForm\"]/div[1]/div[1]/label")).getText(), "Answer");
        softAssert.assertEquals(driver.findElement(By.xpath("//*[@id=\"intSelectionLabel\"]/i")).getText(), "Integers only");
        softAssert.assertAll();
   }

    @Test(priority = 4, dependsOnMethods = "elementDisplayValidator", dataProvider = "dataProviderNegativeInputCases")
    public void inputNotAllowedChecker(String inputValueNegative, String expectedReadValueNegative, String userRefErrorMsgNegative) {
        softAssert = new SoftAssert();
        firstNumber.clear();
        firstNumber.sendKeys(inputValueNegative);
        softAssert.assertNotEquals(firstNumber.getAttribute("value"), expectedReadValueNegative, "First number "+userRefErrorMsgNegative);

        secondNumber.clear();
        secondNumber.sendKeys(inputValueNegative);
        softAssert.assertNotEquals(secondNumber.getAttribute("value"), expectedReadValueNegative, "Second number "+userRefErrorMsgNegative);

        softAssert.assertAll();
    }

    @DataProvider
    public Object[][] dataProviderNegativeInputCases(){
        return new Object[][]{
                {"qwerty", "qwerty", "Accepted alphabets, "},
                {" "," ","Accepted Blank, "},
                {"!@%$#$+)","!@%$#$+)", "Accepted special characters, "} //we can improve in future by only checking for exceptions like "-" (minus), "."
        };
    }

    @Test(priority = 5, dependsOnMethods = "elementDisplayValidator", dataProvider = "dataProviderPositiveInputCases")
    public void inputAllowedChecker(String inputValuePositive, String expectedReadValuePositive, String userRefErrorMsgPositive) {
        softAssert = new SoftAssert();
        firstNumber.clear();
        firstNumber.sendKeys(inputValuePositive);
        softAssert.assertEquals(firstNumber.getAttribute("value"), expectedReadValuePositive,"First number "+userRefErrorMsgPositive);

        secondNumber.clear();
        secondNumber.sendKeys(inputValuePositive);
        softAssert.assertEquals(secondNumber.getAttribute("value"), expectedReadValuePositive, "Second number "+userRefErrorMsgPositive);

        softAssert.assertAll();
    }

    @DataProvider
    public Object[][] dataProviderPositiveInputCases(){
        return new Object[][]{
                {"1234", "1234", "Integer not accepted"},
                {"12.34", "12.34", "Decimal not accepted"},
                {"-10", "-10", "Negative not accepted"},
                {"10,000", "10,000", "Comma separated not accepted"},
                {"12345678901", "1234567890", "More than 10 digits accepted"}
        };
    }

    @Test (priority = 6, dependsOnMethods = "elementDisplayValidator")
    public void checkClearButton(){

        operationSelector.selectByVisibleText("Add");

        firstNumber.clear();
        firstNumber.sendKeys("10");

        secondNumber.clear();
        secondNumber.sendKeys("10");

        calculateButton.click();

        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("input[id=clearButton]")));

        clearButton.click();
        Assert.assertEquals(answerField.getText(), "");

    }

    @Test (priority = 7, dependsOnMethods = "elementDisplayValidator")
    public void answerFieldNotInput(){
        accessUrl(baseUrlOfPage);
        answerField.sendKeys("Test");
        Assert.assertEquals(answerField.getAttribute("value"), "", "Answer not null on sending keys");
    }

    @Test(priority = 8, dependsOnMethods = "elementDisplayValidator", dataProvider = "dataProviderCalculationCases")
    public void calculationCases(String firstNumberCalcInput, String secondNumberCalcInput, String answerCalcInput, String operationSelection, String customErrorMessage) {
        softAssert = new SoftAssert();

        firstNumber.clear();
        secondNumber.clear();

        firstNumber.sendKeys(firstNumberCalcInput);
        secondNumber.sendKeys(secondNumberCalcInput);
        operationSelector.selectByVisibleText(operationSelection);
        calculateButton.click();

        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("input[id=clearButton]")));
        softAssert.assertEquals(answerField.getAttribute("value"), answerCalcInput, customErrorMessage);
        softAssert.assertAll();

    }

    @DataProvider
    public Object[][] dataProviderCalculationCases(){
        return new Object[][]{
                //All the calculation results are compared with microsoft calculator application, mathematically answers may be correct but the calculator app is considered source of truth in this test suite
                //Addition
                {"10", "5", "15", "Add", "Integer addition failed"},
                {"10.05", "5.45", "15.5", "Add",  "Decimal addition failed"},
                {"-10", "5", "-5", "Add", "Negative number addition failed"},
                {"9999999999", "9999999999", "19999999998", "Add", "BVA failure, addition of largest number"},
                {"0.00000005", "0.00000002", "0.00000007", "Add", "BVA failure, smallest positive number addition failure"},
                {"-999999999", "-999999999", "-1999999998", "Add", "BVA failure, addition of largest negative number"},
                //Subtraction
                {"10", "5", "5", "Subtract", "Integer subtraction failed"},
                {"9999999999", "9999999999", "0", "Subtract", "Large number subtraction failure"},
                {"-10", "10", "-20", "Subtract", "Incorrect answer found in -- = + case/ negative number not displayed in result"},
                {"0.00000005", "0.00000002", "0.00000003", "Subtract", "BVA failure, smallest positive number subtraction failure"},
                //Multiplication
                {"10", "5", "50", "Multiply", "Integer multiplication failed"},
                {"10.05", "5.45", "54.7725", "Multiply", "Decimal multiplication failed"},
                {"-10", "5", "-50", "Multiply", "Negative number multiplication failed"},
                {"9999999999", "9999999999", "9.999999998e+19", "Multiply", "BVA failure, largest number multiplication failure"},
                {"0.00000005", "0.00000002", "0.000000000000001", "Multiply", "BVA failure, smallest positive number multiplication failure"},
                {"-999999999", "-999999999", "9.99999998e+17", "Multiply", "BVA failure, largest negative number multiplication failure"},
                //Division
                {"10", "5", "2", "Divide", "Integer division failed"},
                {"10.05", "5.45", "1.844036697247706", "Divide", "Decimal division failed"},
                {"-10", "5", "-2", "Divide", "Negative number division failed"},
                {"9999999999", "9999999999", "1", "Divide", "BVA failure, largest number division failure"},
                {"0.00000005", "0.00000002", "2.5", "Divide", "BVA failure, smallest positive number division failure"},
                {"-999999999", "-999999999", "1", "Divide", "BVA failure, largest negative number division failure"},
                //Concatenate
                {"10", "5", "105", "Concatenate", "Integer Concatenate failed"},
                {"10.05", "5.45", "10.055.45", "Concatenate", "Decimal Concatenate failed"},
                {"9999999999", "9999999999", "99999999999999999999", "Concatenate", "BVA failure, largest number Concatenate failure"},
                {"-999999999", "-999999999", "-999999999-999999999", "Concatenate", "BVA failure, largest negative number Concatenate failure"},
        };
    }

    @Test(priority = 9, dependsOnMethods = "elementDisplayValidator", dataProvider = "dataProviderErrorMessageCases")
    public void validationMessageChecker(String firstNumberCalcInputErrMsg, String secondNumberCalcInputErrMsg, String operationSelectionErrMsg, String errorMessageExpected, String customErrorMessageForValidation){
        softAssert = new SoftAssert();

        firstNumber.clear();
        secondNumber.clear();

        firstNumber.sendKeys(firstNumberCalcInputErrMsg);
        secondNumber.sendKeys(secondNumberCalcInputErrMsg);
        operationSelector.selectByVisibleText(operationSelectionErrMsg);
        calculateButton.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("label[id=errorMsgField]")));
        errorMessage = driver.findElement(By.cssSelector("label[id=errorMsgField]"));
        softAssert.assertEquals(errorMessage.getText(), errorMessageExpected, customErrorMessageForValidation);
        softAssert.assertAll();
        accessUrl(baseUrlOfPage);
    }

    @DataProvider
    public Object[][] dataProviderErrorMessageCases(){
        return new Object[][]{
                {"1", "0", "Divide", "Divide by zero error!", "Error message validation not implemented for divide by zero case"},
                {" ", " ", "Add", "Number 1 is not a number", "Error message validation not implemented for blank"},
                {"abc", "10", "Add", "Number 1 is not a number", "Error message validation not implemented for alphabets at First number"},
                {"10", "abcv", "Add", "Number 2 is not a number", "Error message validation not implemented for alphabets at Second number"},

        };
    }

    @Test(priority = 10, dependsOnMethods = "elementDisplayValidator", dataProvider = "intOnlyCheckerCases")
    public void integersOnlyCheckboxChecker(String firstNumberIntCheck, String secondNumberIntCheck, String operationSelection, String expectedResultOfIntCheckOn, String customErrMsgIntCheck){
        accessUrl(baseUrlOfPage);
        softAssert = new SoftAssert();
        if(operationSelection == "Concatenate") {
            operationSelector.selectByVisibleText(operationSelection);
            assertFalse(integersOnlyCheckbox.isDisplayed(), "Integers only check is displayed on selecting concatenate");
        }
        else{
            firstNumber.clear();
            secondNumber.clear();
            firstNumber.sendKeys(firstNumberIntCheck);
            secondNumber.sendKeys(secondNumberIntCheck);
            operationSelector.selectByVisibleText(operationSelection);

            calculateButton.click();
            wait.until(ExpectedConditions.elementToBeClickable(clearButton));
            integersOnlyCheckbox.click();
            softAssert.assertEquals(answerField.getAttribute("value"), expectedResultOfIntCheckOn, customErrMsgIntCheck);
            softAssert.assertAll();
        }
    }


    @DataProvider
    public Object[][] intOnlyCheckerCases() {
        return new Object[][]{
                {"10.50", "5.20", "Add", "15", "Integers Only checkbox functionality failed on addition"},
                {"10.50", "5.20", "Subtract", "5", "Integers Only checkbox functionality failed on subtraction"},
                {"10.50", "5.20", "Multiply", "54", "Integers Only checkbox functionality failed on multiplication"},
                {"10.50", "5.20", "Divide", "2", "Integers Only checkbox functionality failed on division"}
        };
    }

   @AfterTest
   public void tearDown(){
    driver.quit();
    }
}