package com.bidshop.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class TestDataReader {

    private Properties properties;

    public TestDataReader(String filename) {
        properties = new Properties();
        String path = System.getProperty("user.dir") + "/resources/testdata/" + filename;
        try (InputStream stream = new FileInputStream(path)) {
            properties.load(stream);
        } catch (IOException e) {
            throw new RuntimeException("testdata file not found: " + filename, e);
        }
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }
}
