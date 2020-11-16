package org.automation.driverlaunch;

import org.openqa.selenium.chrome.ChromeDriver;

    public class ChromeDriverManager extends DriverManager {
        public ChromeDriverManager() {
        }

        public void createDriver() {
            System.setProperty("webdriver.chrome.driver", "/Users/Chaudhary/Documents/Drivers/chromedriver");
            this.driver = new ChromeDriver();
        }
    }


