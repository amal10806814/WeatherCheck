package org.automation.driverlaunch;

import org.openqa.selenium.WebDriver;

public abstract class DriverManager {

    public WebDriver driver;

    public DriverManager() {
    }

    public abstract void createDriver();

    public void quitDriver() {
        if (this.driver != null) {
            this.driver.quit();
            this.driver = null;
        }

    }

    public WebDriver getDriver() {
        if (this.driver == null) {
            this.createDriver();
        }

        return this.driver;
    }

}
