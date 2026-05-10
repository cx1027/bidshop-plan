package com.bidshop.pages;

import com.bidshop.base.BasePage;
import com.bidshop.utils.ConfigReader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CheckoutPage extends BasePage {

    private final ConfigReader config = new ConfigReader();

    @FindBy(xpath = "//input[@data-testid='checkout-name']")
    WebElement shippingName;

    @FindBy(xpath = "//input[@data-testid='checkout-address']")
    WebElement shippingAddress;

    @FindBy(xpath = "//input[@data-testid='checkout-city']")
    WebElement shippingCity;

    @FindBy(xpath = "//input[@data-testid='checkout-postcode']")
    WebElement shippingPostcode;

    @FindBy(xpath = "//button[@data-testid='checkout-submit']")
    WebElement placeOrderButton;

    @FindBy(xpath = "//aside[contains(@class,'summary')] | //aside[contains(text(),'Order summary')] | //h2[contains(text(),'Order summary')]/..")
    WebElement orderSummary;

    @FindBy(xpath = "//div[contains(@class,'error') or contains(@class,'alert')]")
    WebElement errorMessage;

    @FindBy(xpath = "//div[contains(@class,'confirmation')] | //div[contains(@class,'success')] | //h1[contains(text(),'Order')] | //h2[contains(text(),'Thank')]")
    WebElement orderConfirmation;

    @FindBy(xpath = "//a[contains(text(),'Continue shopping')]")
    WebElement continueShoppingButton;

    public CheckoutPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public void navigateToCheckout() {
        driver.get(config.getProperty("base.url") + "/checkout");
    }

    public boolean isShippingFieldsVisible() {
        return isDisplayed(shippingName);
    }

    public boolean isOrderSummaryVisible() {
        return isDisplayed(orderSummary);
    }

    public void enterShippingName(String name) {
        sendKeys(shippingName, name);
    }

    public void enterShippingAddress(String address) {
        sendKeys(shippingAddress, address);
    }

    public void enterShippingCity(String city) {
        sendKeys(shippingCity, city);
    }

    public void enterShippingPostcode(String postcode) {
        sendKeys(shippingPostcode, postcode);
    }

    public void clickPlaceOrder() {
        click(placeOrderButton);
    }

    public boolean isErrorMessageVisible() {
        return isDisplayed(errorMessage);
    }

    public String getOrderTotalText() {
        WebElement orderTotal = driver.findElement(org.openqa.selenium.By.xpath(
                "//div[@class='cart-summary__total']"));
        return getText(orderTotal);
    }

    public boolean isOrderConfirmationVisible() {
        return isDisplayed(orderConfirmation);
    }

    public void clickContinueShopping() {
        click(continueShoppingButton);
    }
}
