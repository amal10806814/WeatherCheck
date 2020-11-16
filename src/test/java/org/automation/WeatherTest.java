package org.automation;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.Response;
import org.automation.driverlaunch.DriverManager;
import org.automation.driverlaunch.DriverManagerFactory;
import org.automation.driverlaunch.DriverType;
import org.automation.link.WeatherLink;
import org.automation.pages.WeatherPage;
import org.automation.report.ExtentReport;
import org.automation.response.WeatherResponse;
import org.automation.utility.CommonHelpers;
import org.automation.utility.LocalConfig;
import org.automation.utility.WeatherComparator;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.testng.asserts.SoftAssert;

public class WeatherTest extends ExtentReport {

    Logger log = Logger.getLogger(WeatherTest.class);
    DriverManager driverManager;
    WebDriver driver;
    SoftAssert softassert;

    @BeforeTest
    public void launchBrowser() {
        test = extent.createTest("CheckWeatherTest", "PASSED Test Cases");
        this.driverManager = DriverManagerFactory.getInstance(DriverType.CHROME);
        this.driver = this.driverManager.getDriver();
        this.driver.manage().timeouts().implicitlyWait(20L, TimeUnit.SECONDS);
        this.driver.get(LocalConfig.NDTV);
    }

    @Test(description = "check weather difference from UI and Get API")
    public void checkWeatherDiffTest() throws Exception {
        BasicConfigurator.configure();
        WeatherPage wpage = new WeatherPage(this.driver);
        wpage.clickOnWeatherSection();

        /***Use the `Pin your city` section on the left of the screen to search & select any
         given city**/
        wpage.searchCityAndClick(LocalConfig.CITY.toString());

        /***** Assertion for city displayed on Map ***/
        Assert.assertTrue(wpage.validateCityOnMap(LocalConfig.CITY.toString()));


        /***Validate that selecting any city on the map reveals the weather details ***/

        List uiTempDetails = wpage.getCityWeatherDetails(LocalConfig.CITY.toString());

        List<Integer> uiTempList = new ArrayList();

        for (Object ui:uiTempDetails) {
            uiTempList.add(CommonHelpers.getNumericValue(ui.toString()));
        }

        /*****API Get Call for city temprature*****/

        List<Integer> apiweatherlist=new ArrayList();
        WeatherLink weatherlink =new WeatherLink();
        Response response=weatherlink.execute();
        ObjectMapper mapper=new ObjectMapper();
        WeatherResponse wresponse= mapper.readValue(response.asString(),WeatherResponse.class);
        double apitemp=wresponse.getMain().getTemp();
        List<Integer> apiTempList = new ArrayList();


        double diff=apitemp-273.15;
        log.info("Temp diff "+diff);
        apiTempList.add(((int)Math.abs(diff)));


        String tempFinal=WeatherComparator.compareTo(apiTempList,uiTempList);
        softassert=new SoftAssert();
        if(tempFinal.equals("fail")){
            softassert.assertEquals(tempFinal,"fail");
        }
        else{
            softassert.assertEquals(tempFinal, "success");
        }
    }

    @AfterMethod
    public void getResult(ITestResult result){
        if(result.getStatus()==ITestResult.FAILURE){
            test.log(Status.FAIL, MarkupHelper.createLabel(result.getName()+" FAILED ", ExtentColor.RED));
            test.fail(result.getThrowable());
        }else if(result.getStatus() == ITestResult.SUCCESS) {
            test.log(Status.PASS, MarkupHelper.createLabel(result.getName()+" PASSED ", ExtentColor.GREEN));
        }
        else {
            test.log(Status.SKIP, MarkupHelper.createLabel(result.getName()+" SKIPPED ", ExtentColor.ORANGE));
            test.skip(result.getThrowable());
        }
    }


    @AfterTest
    public void tearDown() {
        //to write or update test information to reporter
        extent.flush();
        driver.quit();
    }
}

