package com.bidshop.pages;

import com.bidshop.base.BasePage;
import com.bidshop.utils.ConfigReader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class CartPage extends BasePage {

    private final ConfigReader config = new ConfigReader();

    @FindBy(xpath = "//div[contains(@class,'cart-item') or contains(@class,'item-row')]")
    List<WebElement> cartItems;

    @FindBy(xpath = "//button[@data-testid='remove-item']")
    WebElement removeItemButton;

    @FindBy(xpath = "//button[contains(text(),'Remove')]")
    WebElement removeButton;

    @FindBy(xpath = "//input[@data-testid='quantity-input' or @data-testid='quantity']")
    WebElement quantityInput;

    @FindBy(xpath = "//span[contains(@class,'subtotal') or contains(text(),'Subtotal')]/following-sibling::span")
    WebElement subtotalElement;

    @FindBy(xpath = "//span[contains(@class,'gst') or contains(text(),'GST')]/following-sibling::span")
    WebElement gstElement;

    @FindBy(xpath = "//span[contains(@class,'total') or contains(text(),'Total')]/following-sibling::span")
    WebElement totalElement;

    @FindBy(xpath = "//button[contains(text(),'Checkout')]")
    WebElement checkoutButton;

    @FindBy(xpath = "//div[contains(@class,'empty-cart') or contains(text(),'empty')]")
    WebElement emptyCartMessage;

    @FindBy(xpath = "//p[contains(@class,'cart-empty') or contains(text(),'empty')]")
    WebElement emptyState;

    public CartPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public void navigateToCart() {
        driver.get(config.getProperty("base.url") + "/cart");
    }

    public boolean isCartPageDisplayed() {
        return isDisplayed(checkoutButton) || isDisplayed(removeButton) || isDisplayed(emptyState);
    }

    public boolean hasItems() {
        return !cartItems.isEmpty();
    }

    public int getCartItemCount() {
        return cartItems.size();
    }

    public void removeItem() {
        WebElement btn = removeItemButton.isDisplayed() ? removeItemButton : removeButton;
        click(btn);
    }

    public void updateQuantity(int quantity) {
        if (isDisplayed(quantityInput)) {
            sendKeys(quantityInput, String.valueOf(quantity));
        }
    }

    public double getSubtotal() {
        return extractPrice(getText(subtotalElement));
    }

    public double getGst() {
        return extractPrice(getText(gstElement));
    }

    public double getTotal() {
        return extractPrice(getText(totalElement));
    }

    public CheckoutPage clickCheckout() {
        click(checkoutButton);
        return new CheckoutPage(driver);
    }

    public boolean isEmptyStateVisible() {
        return isDisplayed(emptyCartMessage) || isDisplayed(emptyState);
    }

    private double extractPrice(String text) {
        String cleaned = text.replaceAll("[^0-9.]", "");
        return cleaned.isEmpty() ? 0.0 : Double.parseDouble(cleaned);
    }

    public boolean verifyGstCalculation(double subtotal) {
        double expectedGst = subtotal * 0.15;
        double actualGst = getGst();
        return Math.abs(expectedGst - actualGst) < 0.01;
    }

    public boolean verifyTotalCalculation(double subtotal) {
        double expectedTotal = subtotal * 1.15;
        double actualTotal = getTotal();
        return Math.abs(expectedTotal - actualTotal) < 0.01;
    }
}
