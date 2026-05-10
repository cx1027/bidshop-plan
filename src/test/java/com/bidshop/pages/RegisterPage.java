package com.bidshop.pages;

import com.bidshop.base.BasePage;
import com.bidshop.utils.ConfigReader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class RegisterPage extends BasePage {

    private final ConfigReader config = new ConfigReader();

    @FindBy(xpath = "//input[@data-testid='register-name']")
    WebElement fullNameField;

    @FindBy(xpath = "//input[@data-testid='register-email']")
    WebElement emailField;

    @FindBy(xpath = "//input[@data-testid='register-password']")
    WebElement passwordField;

    @FindBy(xpath = "//button[contains(text(),'Create account')]")
    WebElement createAccountButton;

    @FindBy(xpath = "//a[contains(text(),'Already have')] | //a[contains(@href,'/login')]")
    WebElement alreadyHaveAccountLink;

    @FindBy(xpath = "//p[contains(text(),'Invalid') or contains(text(),'required') or contains(text(),'already') or contains(text(),'error') or contains(text(),'taken')]")
    WebElement errorMessage;

    @FindBy(xpath = "//p[contains(text(),'success') or contains(text(),'created') or contains(text(),'Account') or contains(text(),'Welcome')]")
    WebElement successMessage;

    public RegisterPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public void navigateToRegister() {
        driver.get(config.getProperty("base.url") + "/register");
    }

    public boolean isFullNameFieldVisible() {
        return isDisplayed(fullNameField);
    }

    public boolean isEmailFieldVisible() {
        return isDisplayed(emailField);
    }

    public boolean isPasswordFieldVisible() {
        return isDisplayed(passwordField);
    }

    public boolean isCreateAccountButtonVisible() {
        return isDisplayed(createAccountButton);
    }

    public void enterFullName(String name) {
        sendKeys(fullNameField, name);
    }

    public void enterEmail(String email) {
        sendKeys(emailField, email);
    }

    public void enterPassword(String password) {
        sendKeys(passwordField, password);
    }

    public void clickCreateAccount() {
        clickWithJs(createAccountButton);
    }

    public void register(String fullName, String email, String password) {
        enterFullName(fullName);
        enterEmail(email);
        enterPassword(password);
        clickCreateAccount();
    }

    public LoginPage clickAlreadyHaveAccountLink() {
        click(alreadyHaveAccountLink);
        return new LoginPage(driver);
    }

    public boolean isErrorMessageVisible() {
        return isDisplayed(errorMessage);
    }

    public String getErrorMessageText() {
        return getText(errorMessage);
    }

    public boolean isSuccessMessageVisible() {
        return isDisplayed(successMessage);
    }
}
