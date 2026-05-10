package com.bidshop.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {

    private Properties properties;

    public ConfigReader() {
        properties = new Properties();
        String envPath = System.getProperty("user.dir") + File.separator + ".env";
        try (InputStream envStream = new FileInputStream(envPath)) {
            properties.load(envStream);
        } catch (IOException e) {
        }
        try (InputStream propStream = new FileInputStream(
                System.getProperty("user.dir") + File.separator + "resources" + File.separator + "config.properties")) {
            properties.load(propStream);
        } catch (IOException e) {
            throw new RuntimeException("config.properties not found in resources folder", e);
        }
    }

    public String getProperty(String key) {
        String value = properties.getProperty(key);
        if (value == null) {
            throw new RuntimeException("Property '" + key + "' not found in configuration files");
        }
        return value;
    }

    public String getBaseUrl() {
        return getProperty("base.url");
    }

    public String getBrowser() {
        return getProperty("browser");
    }
}
