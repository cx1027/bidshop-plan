package com.bidshop.base;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.bidshop.utils.ConfigReader;

import java.time.Duration;

public class BasePage {

    protected WebDriver driver;
    protected WebDriverWait wait;
    protected JavascriptExecutor js;
    protected Actions actions;
    protected ConfigReader config;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(
                Integer.parseInt(new ConfigReader().getProperty("explicit.wait"))));
        this.js = (JavascriptExecutor) driver;
        this.actions = new Actions(driver);
        this.config = new ConfigReader();
    }

    protected void waitForElementVisible(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    protected void waitForElementClickable(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    protected void waitForUrlContains(String urlFragment) {
        wait.until(ExpectedConditions.urlContains(urlFragment));
    }

    protected void click(WebElement element) {
        waitForElementClickable(element);
        element.click();
    }

    protected void clickWithJs(WebElement element) {
        js.executeScript("arguments[0].click();", element);
    }

    protected void sendKeys(WebElement element, String text) {
        waitForElementVisible(element);
        element.clear();
        element.sendKeys(text);
    }

    protected String getText(WebElement element) {
        waitForElementVisible(element);
        return element.getText();
    }

    protected boolean isDisplayed(WebElement element) {
        try {
            waitForElementVisible(element);
            return element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    protected void scrollToElement(WebElement element) {
        js.executeScript("arguments[0].scrollIntoView(true);", element);
    }

    protected void scrollToTop() {
        js.executeScript("window.scrollTo(0, 0);");
    }

    protected void scrollToBottom() {
        js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
    }

    protected String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    protected String getPageTitle() {
        return driver.getTitle();
    }
}
