package com.jagan.api.base;

import com.jagan.api.utils.ReadProperties;
import com.jagan.api.utils.RequestManager;
import io.cucumber.java.After;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeSuite;

import java.io.IOException;
import java.util.Properties;

public class BaseTest extends AbstractTestNGCucumberTests {
    public static Properties envProps;
    public static String USER_LOCALE;

    @BeforeSuite(alwaysRun = true)
    public void beforeSuite() throws IOException {
        loadEnvProperties();
    }
    private void loadEnvProperties() throws IOException {
        envProps = ReadProperties.getEnvProperties(System.getProperty("env"));
        USER_LOCALE = ReadProperties.getProperty("locale");
    }

    @AfterMethod
    public void tearDown() throws Exception {
        if(RequestManager.getRequest() != null)
            RequestManager.deleteRequestFromThreadLocal();
    }

}
