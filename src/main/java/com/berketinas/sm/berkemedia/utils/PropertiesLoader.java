package com.berketinas.sm.berkemedia.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

// ***
// to mock application.properties in spring boot
// ***
public class PropertiesLoader {
    public static Properties loadProperties() throws IOException {
        Properties configuration = new Properties();
        InputStream inputStream = PropertiesLoader.class
                .getClassLoader()
                .getResourceAsStream("application.properties");
        configuration.load(inputStream);
        inputStream.close();
        return configuration;
    }
}
