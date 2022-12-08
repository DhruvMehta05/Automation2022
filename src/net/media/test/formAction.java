package net.media.test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class formAction {

    public void testForm(String testData){
        FirstClass firstClassObj = new FirstClass();
        WebElement firstName = firstClassObj.driver.findElement(By.cssSelector("input[id=\"firstName\"]"));
        firstName.sendKeys(testData);
    }

}
