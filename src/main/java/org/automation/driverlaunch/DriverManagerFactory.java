package org.automation.driverlaunch;


public class DriverManagerFactory {

    public DriverManagerFactory() {
    }

    public static DriverManager getInstance(DriverType type) {
        DriverManager driverManager = null;
        switch(type) {
            case CHROME:
                driverManager = new ChromeDriverManager();
//            case FIREFOX:
//                driverManager = new FirefoxDriverManager();
//            case SAFARI:
//                driverManager = new SafariDriverManager();
            default:
                return driverManager;
        }
    }
}
