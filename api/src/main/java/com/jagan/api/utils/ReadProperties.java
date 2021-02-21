package com.jagan.api.utils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ReadProperties {
    public static Properties defaultProperties;
    private static Properties retrieveProperties(String filename) throws IOException {
        Properties properties = new Properties();
        properties.load(new FileReader(new File("config/"+filename+".properties")));
        return properties;
    }

    public static Properties getEnvProperties(String filename) throws IOException {
        if(filename == null) {
            if(defaultProperties == null)
                defaultProperties = retrieveProperties("app");
            return retrieveProperties(defaultProperties.getProperty("env"));
        }
        return retrieveProperties(filename);
    }

    public static String getDefaultProperty(String propertyname) throws IOException {
        if(defaultProperties == null) {
            if(defaultProperties == null)
                defaultProperties = retrieveProperties("app");
        }
        return defaultProperties.getProperty(propertyname);
    }

    public static String getProperty(String propertyName) throws IOException {
        String property = System.getProperty(propertyName);
        if(property == null){
            return getDefaultProperty(propertyName);
        }
        return property;
    }
}
