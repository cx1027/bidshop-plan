package com.bidshop.pages;

import com.bidshop.base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage extends BasePage {

    @FindBy(xpath = "//input[@data-testid='search-bar']")
    WebElement searchBar;

    @FindBy(xpath = "//a[contains(text(),'Meat')]")
    WebElement meatCategory;

    @FindBy(xpath = "//a[contains(text(),'Seafood')]")
    WebElement seafoodCategory;

    @FindBy(xpath = "//a[contains(text(),'Bakery')]")
    WebElement bakeryCategory;

    @FindBy(xpath = "//button[contains(text(),'Log in to buy')]")
    WebElement loginToBuyButton;

    @FindBy(xpath = "//div[contains(@class,'product-card') or contains(@class,'product')]")
    WebElement productCard;

    @FindBy(xpath = "//button[contains(text(),'Logout')]")
    WebElement logoutButton;

    @FindBy(xpath = "//button[@data-testid='add-to-cart']")
    WebElement addToCartButton;

    @FindBy(xpath = "//a[@data-testid='cart-icon' or contains(@href,'cart')]")
    WebElement cartIcon;

    public HomePage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public String getPageTitle() {
        return driver.getTitle();
    }

    public boolean isSearchBarVisible() {
        return isDisplayed(searchBar);
    }

    public boolean areProductsLoaded() {
        return isDisplayed(productCard);
    }

    public void clickMeatCategory() {
        click(meatCategory);
    }

    public void clickSeafoodCategory() {
        click(seafoodCategory);
    }

    public void clickBakeryCategory() {
        click(bakeryCategory);
    }

    public void clickLoginToBuy() {
        click(loginToBuyButton);
    }

    public void clickLogout() {
        click(logoutButton);
    }

    public void search(String keyword) {
        sendKeys(searchBar, keyword);
    }

    public void clickAddToCart() {
        clickWithJs(addToCartButton);
    }

    public void clickCartIcon() {
        click(cartIcon);
    }

    public LoginPage navigateToLogin() {
        clickLoginToBuy();
        return new LoginPage(driver);
    }

    public boolean isLogoutButtonVisible() {
        return isDisplayed(logoutButton);
    }
}
