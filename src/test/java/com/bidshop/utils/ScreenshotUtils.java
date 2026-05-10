package com.bidshop.utils;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenshotUtils {

    private WebDriver driver;

    public ScreenshotUtils() {
    }

    public ScreenshotUtils(WebDriver driver) {
        this.driver = driver;
    }

    public String captureScreenshot(WebDriver driver, String testName) {
        TakesScreenshot ts = (TakesScreenshot) driver;
        File source = ts.getScreenshotAs(OutputType.FILE);
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String screenshotDir = System.getProperty("user.dir") + File.separator + "screenshots";
        new File(screenshotDir).mkdirs();
        String destPath = screenshotDir + File.separator + testName + "_" + timestamp + ".png";
        try {
            FileUtils.copyFile(source, new File(destPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return destPath;
    }

    public String captureScreenshot(String testName) {
        if (driver == null) {
            throw new IllegalStateException("WebDriver is not set. Use ScreenshotUtils(WebDriver driver) constructor.");
        }
        return captureScreenshot(driver, testName);
    }
}
