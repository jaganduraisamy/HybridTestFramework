package com.jagan.api.utils;

import io.restassured.RestAssured;
import io.restassured.config.EncoderConfig;
import io.restassured.config.HeaderConfig;
import io.restassured.specification.RequestSpecification;

import java.util.UUID;

import static com.jagan.api.base.BaseTest.envProps;

public class RequestManager {
    public static RequestSpecification getRequest() throws Exception {
        if(request.get() == null) {
            setRequest(initializeRestAssuredConfig());
        }
        return request.get();
    }
    public static void deleteRequestFromThreadLocal() {
       if(request.get() != null)
           request.remove();
    }

    public static void setRequest(RequestSpecification _request) {
        request.set(_request);
    }

    private static ThreadLocal<RequestSpecification> request = new ThreadLocal<>();

    private static RequestSpecification initializeRestAssuredConfig() throws Exception {
        RestAssured.reset();
        RestAssured.baseURI = envProps.getProperty("baseURI");
        RestAssured.basePath = envProps.getProperty("basePath");
        RequestSpecification _request = RestAssured.given()
                .config(RestAssured.config().encoderConfig(EncoderConfig.encoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false)))
                .config(RestAssured.config().headerConfig(HeaderConfig.headerConfig().
                        overwriteHeadersWithName("X-AUTH-APIKEY","Content-Type","Authorization")))
                .header("accept", "application/json")
                .header("Content-Type","application/json")
                .header("X-Request-Id", UUID.randomUUID().toString().replace("-", ""))
                .header("Authorization", "Bearer " + envProps.getProperty("apiKey"));
        return _request;

    }

    public static RequestSpecification setRequest() throws Exception {
        setRequest(initializeRestAssuredConfig());
        return getRequest();
    }
}