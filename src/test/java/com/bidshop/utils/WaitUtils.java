package com.bidshop.utils;

import com.google.common.base.Function;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import java.time.Duration;

public class WaitUtils {

    private WebDriver driver;

    public WaitUtils(WebDriver driver) {
        this.driver = driver;
    }

    public WebElement fluentWaitForElement(By locator, int timeoutInSeconds, int pollingInSeconds) {
        Wait<WebDriver> wait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(timeoutInSeconds))
                .pollingEvery(Duration.ofSeconds(pollingInSeconds))
                .ignoring(Exception.class);

        return wait.until(new Function<WebDriver, WebElement>() {
            @Override
            public WebElement apply(WebDriver driver) {
                return driver.findElement(locator);
            }
        });
    }

    public boolean fluentWaitForVisibility(WebElement element, int timeoutInSeconds, int pollingInSeconds) {
        Wait<WebDriver> wait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(timeoutInSeconds))
                .pollingEvery(Duration.ofSeconds(pollingInSeconds))
                .ignoring(Exception.class);

        return wait.until(new Function<WebDriver, Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                try {
                    return element.isDisplayed();
                } catch (Exception e) {
                    return false;
                }
            }
        });
    }

    public boolean waitForUrlContains(String urlFragment, int timeoutInSeconds) {
        Wait<WebDriver> wait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(timeoutInSeconds))
                .pollingEvery(Duration.ofSeconds(1))
                .ignoring(Exception.class);

        return wait.until(new Function<WebDriver, Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                return driver.getCurrentUrl().contains(urlFragment);
            }
        });
    }

    public boolean waitForTextInElement(By locator, String expectedText, int timeoutInSeconds) {
        Wait<WebDriver> wait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(timeoutInSeconds))
                .pollingEvery(Duration.ofSeconds(1))
                .ignoring(Exception.class);

        return wait.until(new Function<WebDriver, Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                WebElement element = driver.findElement(locator);
                return element.getText().contains(expectedText);
            }
        });
    }
}
