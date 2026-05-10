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

    @FindBy(xpath = "//tbody/tr[starts-with(@data-testid,'cart-row-')]")
    List<WebElement> cartItems;

    @FindBy(xpath = "//button[starts-with(@data-testid,'cart-remove-')]")
    WebElement removeItemButton;

    @FindBy(xpath = "//button[contains(text(),'Remove')]")
    WebElement removeButton;

    @FindBy(xpath = "//input[starts-with(@data-testid,'quantity-')]")
    WebElement quantityInput;

    @FindBy(xpath = "//div[@class='cart-summary']/div[1]")
    WebElement subtotalElement;

    @FindBy(xpath = "//div[@class='cart-summary']/div[2]")
    WebElement gstElement;

    @FindBy(xpath = "//div[@class='cart-summary']/div[3]")
    WebElement totalElement;

    @FindBy(xpath = "//button[@data-testid='cart-checkout']")
    WebElement checkoutButton;

    @FindBy(xpath = "//button[@data-testid='cart-clear']")
    WebElement clearCartButton;

    @FindBy(xpath = "//p[contains(text(),'Your cart is empty')]")
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
        sendKeys(quantityInput, String.valueOf(quantity));
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
        return !hasItems() || isDisplayed(emptyState);
    }

    private double extractPrice(String text) {
        java.util.regex.Matcher matcher = java.util.regex.Pattern.compile("\\$?(\\d+\\.\\d{2})$").matcher(text.trim());
        if (matcher.find()) {
            return Double.parseDouble(matcher.group(1));
        }
        String cleaned = text.replaceAll("[^0-9.]", "");
        return cleaned.isEmpty() ? 0.0 : Double.parseDouble(cleaned);
    }

    public boolean verifyGstCalculation(double subtotal) {
        double actualGst = getGst();
        double actualTotal = getTotal();
        return Math.abs(actualTotal - (subtotal + actualGst)) < 0.02;
    }

    public boolean verifyTotalCalculation(double subtotal) {
        double actualGst = getGst();
        double actualTotal = getTotal();
        double expectedTotal = subtotal + actualGst;
        return Math.abs(expectedTotal - actualTotal) < 0.02;
    }
}
