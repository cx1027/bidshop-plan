package com.bidshop.pages;

import com.bidshop.base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class HomePage extends BasePage {

    @FindBy(xpath = "//input[@data-testid='search-bar']")
    WebElement searchBar;

    @FindBy(xpath = "//a[contains(text(),'Meat')]")
    WebElement meatCategory;

    @FindBy(xpath = "//a[contains(text(),'Seafood')]")
    WebElement seafoodCategory;

    @FindBy(xpath = "//a[contains(text(),'Bakery')]")
    WebElement bakeryCategory;

    @FindBy(xpath = "//a[@data-testid='nav-login']")
    WebElement loginToBuyButton;

    @FindBy(xpath = "//div[contains(@class,'product-card') or contains(@class,'product')]")
    WebElement productCard;

    @FindBy(xpath = "//button[contains(text(),'Log out')]")
    WebElement logoutButton;

    @FindBy(xpath = "//a[@data-testid='nav-login']")
    WebElement loginLink;

    @FindBy(xpath = "//button[contains(text(),'Add to cart')]")
    List<WebElement> addToCartButtons;

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
        if (!addToCartButtons.isEmpty()) {
            clickWithJs(addToCartButtons.get(0));
        }
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

    public boolean isLoginLinkVisible() {
        return isDisplayed(loginLink);
    }
}
