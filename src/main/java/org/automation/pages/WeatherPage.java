package org.automation.pages;


import org.automation.utility.DriverWait;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.List;

public class WeatherPage {

    WebDriver driver;

    public WeatherPage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver,this);
    }

    @FindBy(xpath = "//a[@id='h_sub_menu']")
    private WebElement menu;

    @FindBy(xpath="//a[contains(text(),'WEATHER')]")
    private WebElement weather;
    @FindBy(xpath="//input[@class='searchBox']")
    private WebElement searchTextBox;
    String cityCheckBox = "//label[@for = '%1s']";
    String citytemperature = "//div[@title= '%1s']//div//span[@class='tempRedText']";
    String validatecity = "//div[@title= '%1s']";
    String tempdetail="//div[@class='leaflet-popup-content']//span[@class='heading']";


    public void clickOnWeatherSection(){
        menu.click();
        weather.click();
    }
    public void searchCityAndClick(String city){
        searchTextBox.sendKeys(city);
        searchTextBox.click();
        DriverWait.expicitWait(driver, By.xpath(String.format(cityCheckBox, city)), 10);

        driver.findElement(By.xpath(String.format(cityCheckBox,city))).click();
    }


    public boolean validateCityOnMap(String city){
        DriverWait.expicitWait(driver,By.xpath(String.format(validatecity,city)),10);
        return driver.findElement(By.xpath(String.format(validatecity,city))).isDisplayed();
    }

    public List getCityWeatherDetails(String city){
        DriverWait.expicitWait(driver,By.xpath(String.format(citytemperature,city)),10);

        driver.findElement(By.xpath(String.format(citytemperature,city))).click();

        List<WebElement> list = driver.findElements(By.xpath(tempdetail));
        List<String> weatherlist=new ArrayList<>();
        for (WebElement citytemp:list) {
            if(citytemp.getText().contains("Degrees:")){
                weatherlist.add(citytemp.getText());
            }
        }
        return weatherlist;

    }
}
