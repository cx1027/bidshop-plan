package com.bidshop.pages;

import com.bidshop.base.BasePage;
import com.bidshop.utils.ConfigReader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage extends BasePage {

    private final ConfigReader config = new ConfigReader();

    @FindBy(xpath = "//input[@data-testid='login-email']")
    WebElement emailField;

    @FindBy(xpath = "//input[@data-testid='login-password']")
    WebElement passwordField;

    @FindBy(xpath = "//button[contains(text(),'Log in')]")
    WebElement loginSubmitButton;

    @FindBy(xpath = "//a[contains(text(),'Register here')]")
    WebElement registerLink;

    @FindBy(xpath = "//a[contains(text(),'Already have account? Log in')]")
    WebElement alreadyHaveAccountLink;

    @FindBy(xpath = "//p[contains(text(),'Invalid') or contains(text(),'required')]")
    WebElement errorMessage;

    @FindBy(xpath = "//button[contains(text(),'Log out')]")
    WebElement logoutButton;

    @FindBy(xpath = "//span[contains(@class,'user-email') or contains(text(),'Welcome')]")
    WebElement welcomeMessage;

    public LoginPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public void navigateToLogin() {
        driver.get(config.getProperty("base.url") + "/login");
    }

    public boolean isEmailFieldVisible() {
        return isDisplayed(emailField);
    }

    public boolean isPasswordFieldVisible() {
        return isDisplayed(passwordField);
    }

    public boolean isLoginSubmitButtonVisible() {
        return isDisplayed(loginSubmitButton);
    }

    public void enterEmail(String email) {
        sendKeys(emailField, email);
    }

    public void enterPassword(String password) {
        sendKeys(passwordField, password);
    }

    public HomePage clickLoginSubmit() {
        clickWithJs(loginSubmitButton);
        return new HomePage(driver);
    }

    public HomePage login(String email, String password) {
        enterEmail(email);
        enterPassword(password);
        return clickLoginSubmit();
    }

    public RegisterPage clickRegisterLink() {
        click(registerLink);
        return new RegisterPage(driver);
    }

    public boolean isErrorMessageVisible() {
        return isDisplayed(errorMessage);
    }

    public String getErrorMessageText() {
        return getText(errorMessage);
    }

    public boolean isLoginSuccessful() {
        return getCurrentUrl().equals(config.getProperty("base.url") + "/")
                || getCurrentUrl().equals(config.getProperty("base.url"));
    }

    public void clickLogout() {
        clickWithJs(logoutButton);
    }
}
