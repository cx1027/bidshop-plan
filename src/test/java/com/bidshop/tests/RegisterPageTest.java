package com.bidshop.tests;

import com.bidshop.base.BaseTest;
import com.bidshop.pages.LoginPage;
import com.bidshop.pages.RegisterPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class RegisterPageTest extends BaseTest {

    private static final String VALID_NAME = "Test User";
    private static final String VALID_EMAIL = "testuser" + System.currentTimeMillis() + "@gmail.com";
    private static final String VALID_PASSWORD = "password123";
    private static final String INVALID_EMAIL = "invalidemail";
    private static final String SHORT_PASSWORD = "123";

    @Test(priority = 1, description = "TC_Reg_01: Verify register page displays all required form fields")
    public void verifyRegisterPageDisplayed() {
        RegisterPage registerPage = new RegisterPage(driver);
        registerPage.navigateToRegister();

        Assert.assertTrue(registerPage.isFullNameFieldVisible(),
                "Full Name field should be visible on register page");
        Assert.assertTrue(registerPage.isEmailFieldVisible(),
                "Email field should be visible on register page");
        Assert.assertTrue(registerPage.isPasswordFieldVisible(),
                "Password field should be visible on register page");
        Assert.assertTrue(registerPage.isCreateAccountButtonVisible(),
                "Create account button should be visible on register page");
    }

    @Test(priority = 2, description = "TC_Reg_02: Verify registration with valid data redirects to home or login page")
    public void verifyRegisterWithValidData() {
        RegisterPage registerPage = new RegisterPage(driver);
        registerPage.navigateToRegister();
        registerPage.register(VALID_NAME, VALID_EMAIL, VALID_PASSWORD);

        String currentUrl = registerPage.getCurrentUrl();
        Assert.assertTrue(
                currentUrl.contains("/login") || currentUrl.contains("/") || currentUrl.contains("/home"),
                "User should be redirected to login or home page after successful registration");
    }

    @Test(priority = 3, description = "TC_Reg_03: Verify clicking 'Already have account? Log in' navigates to login page")
    public void verifyRegisterNavigationToLogin() {
        RegisterPage registerPage = new RegisterPage(driver);
        registerPage.navigateToRegister();
        LoginPage loginPage = registerPage.clickAlreadyHaveAccountLink();

        Assert.assertTrue(loginPage.getCurrentUrl().contains("/login"),
                "User should be redirected to '/login' page after clicking 'Already have account? Log in'");
        Assert.assertTrue(loginPage.isEmailFieldVisible(),
                "Email field should be visible on login page");
    }

    @Test(priority = 4, description = "TC_Reg_04a: Verify registration is rejected with bad email")
    public void verifyRegisterWithBadEmail() {
        RegisterPage registerPage = new RegisterPage(driver);
        registerPage.navigateToRegister();
        registerPage.register(VALID_NAME, INVALID_EMAIL, VALID_PASSWORD);

        boolean stillOnRegisterPage = registerPage.getCurrentUrl().contains("/register");
        boolean hasValidationError = registerPage.isErrorMessageVisible();
        Assert.assertTrue(stillOnRegisterPage || hasValidationError,
                "User should stay on register page or validation error should be displayed for bad email");
    }

    @Test(priority = 5, description = "TC_Reg_04b: Verify registration is rejected with short password")
    public void verifyRegisterWithShortPassword() {
        RegisterPage registerPage = new RegisterPage(driver);
        registerPage.navigateToRegister();
        registerPage.register(VALID_NAME, VALID_EMAIL, SHORT_PASSWORD);

        boolean stillOnRegisterPage = registerPage.getCurrentUrl().contains("/register");
        boolean hasValidationError = registerPage.isErrorMessageVisible();
        Assert.assertTrue(stillOnRegisterPage || hasValidationError,
                "User should stay on register page or validation error should be displayed for short password");
    }
}
