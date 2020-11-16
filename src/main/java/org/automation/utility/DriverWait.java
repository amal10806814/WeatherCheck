package org.automation.utility;



import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class DriverWait {
    static  WebDriverWait wait;
    static public boolean expicitWait(WebDriver driver, By locator, int timeout){
        try {
            wait = new WebDriverWait(driver,timeout);
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public int compare(int one, int two){
        return one-two;
    }
}