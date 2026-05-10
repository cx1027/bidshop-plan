package com.bidshop.pages;

import com.bidshop.base.BasePage;
import com.bidshop.utils.ConfigReader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CheckoutPage extends BasePage {

    private final ConfigReader config = new ConfigReader();

    @FindBy(xpath = "//input[@data-testid='shipping-name']")
    WebElement shippingName;

    @FindBy(xpath = "//input[@data-testid='shipping-address']")
    WebElement shippingAddress;

    @FindBy(xpath = "//input[@data-testid='shipping-city']")
    WebElement shippingCity;

    @FindBy(xpath = "//input[@data-testid='payment-card']")
    WebElement paymentCard;

    @FindBy(xpath = "//input[@data-testid='payment-expiry']")
    WebElement paymentExpiry;

    @FindBy(xpath = "//input[@data-testid='payment-cvv']")
    WebElement paymentCvv;

    @FindBy(xpath = "//button[contains(text(),'Place Order')]")
    WebElement placeOrderButton;

    @FindBy(xpath = "//div[contains(@class,'order-summary') or contains(@class,'summary')]")
    WebElement orderSummary;

    @FindBy(xpath = "//span[contains(@class,'order-total')]")
    WebElement orderTotal;

    @FindBy(xpath = "//div[contains(@class,'error') or contains(@class,'alert')]")
    WebElement errorMessage;

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

    public void enterPaymentCard(String card) {
        sendKeys(paymentCard, card);
    }

    public void enterPaymentExpiry(String expiry) {
        sendKeys(paymentExpiry, expiry);
    }

    public void enterPaymentCvv(String cvv) {
        sendKeys(paymentCvv, cvv);
    }

    public void clickPlaceOrder() {
        click(placeOrderButton);
    }

    public boolean isErrorMessageVisible() {
        return isDisplayed(errorMessage);
    }

    public String getOrderTotalText() {
        return getText(orderTotal);
    }
}
