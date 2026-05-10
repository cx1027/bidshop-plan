package com.bidshop.base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import com.bidshop.listeners.TestListener;
import com.bidshop.utils.ConfigReader;
import com.bidshop.utils.ScreenshotUtils;

import java.time.Duration;

@Listeners(TestListener.class)
public class BaseTest {

    protected WebDriver driver;
    protected ConfigReader config;
    protected ScreenshotUtils screenshotUtils;

    @BeforeMethod
    public void setUp() {
        config = new ConfigReader();
        screenshotUtils = new ScreenshotUtils();

        WebDriverManager.firefoxdriver().setup();

        FirefoxOptions options = new FirefoxOptions();
        if (Boolean.parseBoolean(config.getProperty("headless"))) {
            options.addArguments("--headless");
        }
        options.addArguments("--width=1920", "--height=1080");

        driver = new FirefoxDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(
                Integer.parseInt(config.getProperty("implicit.wait"))));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(
                Integer.parseInt(config.getProperty("page.load.timeout"))));
        driver.manage().window().maximize();
        driver.get(config.getProperty("base.url"));
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
