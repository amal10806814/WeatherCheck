package org.automation.baseclass;


import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.EncoderConfig;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import java.util.*;

import java.util.HashMap;

public class BaseApi {

    private RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
    private String baseUri;
    private ContentType contentType;
    private BaseApi.MethodType method;
    private String basePath;
    private Response response;
    private Map<String, Object> headers = new HashMap();

    private Map<String, Object> queryParams = new HashMap();

    public void setQueryParams(Map<String, Object> queryParams) {
        this.queryParams = queryParams;
    }

    public void addQueryParam(String paramKey, Object paramValue) {
        this.queryParams.put(paramKey, paramValue);
        this.requestSpecBuilder.addQueryParam(paramKey, new Object[]{paramValue});
    }

    public void addQueryParams(Map<String, String> queryParams) {
        this.queryParams.putAll(queryParams);
        this.requestSpecBuilder.addQueryParams(queryParams);
    }

    public Map<String, Object> getQueryParams() {
        return this.queryParams;
    }

    public ContentType getContentType() {
        return contentType;
    }

    public void setContentType(ContentType contentType) {
        this.contentType = contentType;
        this.requestSpecBuilder.setContentType(contentType);
    }


    public void addHeaders(Map<String, String> headers) {
        this.headers.putAll(headers);
        this.requestSpecBuilder.addHeaders(headers);
    }

    public void addHeader(String headerKey, String headerValue) {
        this.headers.put(headerKey, headerValue);
        this.requestSpecBuilder.addHeader(headerKey, headerValue);
    }

    public Map<String, Object> getHeaders() {
        return this.headers;
    }


    public String getBaseUri() {
        return baseUri;
    }

    public void setBaseUri(String baseUri) {
        this.baseUri = baseUri;
        this.requestSpecBuilder.setBaseUri(baseUri);
    }


    public BaseApi.MethodType getMethod() {
        return this.method;
    }

    public void setMethod(BaseApi.MethodType method) {
        this.method = method;

    }

    public String getBasePath() {
        return basePath;
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath;
        this.requestSpecBuilder.setBasePath(basePath);
    }

    public Response execute() {
        RequestSpecification requestSpecification = this.requestSpecBuilder.addFilter(new RequestLoggingFilter()).addFilter(new ResponseLoggingFilter()).build();
//        if (this.authName != null && this.authPassword != null) {
//            PreemptiveBasicAuthScheme basicAuth = new PreemptiveBasicAuthScheme();
//            basicAuth.setUserName(this.authName);
//            basicAuth.setPassword(this.authPassword);
//            this.requestSpecBuilder.setAuth(basicAuth);
//        }

        EncoderConfig ec = new EncoderConfig();
        ec.appendDefaultContentCharsetToContentTypeIfUndefined(false);
        RestAssured.defaultParser = Parser.JSON;
        //  RestAssuredConfig config = (new CurlLoggingRestAssuredConfigBuilder()).build().httpClient(HttpClientConfig.httpClientConfig().setParam("http.socket.timeout", 50000)).encoderConfig(ec.appendDefaultContentCharsetToContentTypeIfUndefined(false));
        Response response;
        switch(this.method) {
            case GET:
                response = (Response)RestAssured.given().spec(requestSpecification).when().get();
                break;
            case POST:
                response = (Response)RestAssured.given().spec(requestSpecification).when().post();
                break;
            case PUT:
                response = (Response)RestAssured.given().spec(requestSpecification).when().put();
                break;
            case DELETE:
                response = (Response)RestAssured.given().spec(requestSpecification).when().delete();
                break;
            case PATCH:
                response = (Response)RestAssured.given().spec(requestSpecification).when().patch();
                break;
            default:
                throw new RuntimeException("API method not specified");
        }

        this.response = response;
        this.printResponse(response);
        return response;
    }

    public void printResponse(Response response){
        System.out.println(response.getBody().prettyPrint());
    }

    public static enum MethodType {
        POST,
        GET,
        PUT,
        DELETE,
        PATCH;

        private MethodType() {
        }
    }
}