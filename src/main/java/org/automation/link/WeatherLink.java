package org.automation.link;




import org.automation.baseclass.BaseApi;
import org.automation.baseclass.BasePath;
import org.automation.utility.LocalConfig;
import io.restassured.http.ContentType;

public class WeatherLink  extends BaseApi{

    public WeatherLink(){

        generateRequest();
    }

    public void generateRequest(){

        setBaseUri(LocalConfig.WEATHER_HOST);
        setContentType(ContentType.JSON);
        setBasePath(BasePath.Path.WEATHER_MAP);
        setMethod(BaseApi.MethodType.GET);
        addQueryParam("q",LocalConfig.CITY);
        addQueryParam("apiKey",LocalConfig.APP_KEY);
    }
}
