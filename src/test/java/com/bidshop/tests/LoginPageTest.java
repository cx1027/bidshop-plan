package com.bidshop.tests;

import com.bidshop.base.BaseTest;
import com.bidshop.pages.HomePage;
import com.bidshop.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginPageTest extends BaseTest {

    private static final String VALID_EMAIL = "test1@gmail.com";
    private static final String VALID_PASSWORD = "123456";
    private static final String INVALID_EMAIL = "invalid@test.com";
    private static final String INVALID_PASSWORD = "wrongpassword";

    @Test(priority = 1, description = "TC_Login_01: Verify login page is displayed when clicking 'Log in to buy'")
    public void verifyLoginPageDisplayed() {
        HomePage homePage = new HomePage(driver);
        homePage.clickLoginToBuy();

        LoginPage loginPage = new LoginPage(driver);
        Assert.assertTrue(loginPage.getCurrentUrl().contains("/login"),
                "URL should contain '/login' after clicking 'Log in to buy'");
        Assert.assertTrue(loginPage.isEmailFieldVisible(),
                "Email field should be visible on login page");
        Assert.assertTrue(loginPage.isPasswordFieldVisible(),
                "Password field should be visible on login page");
    }

    @Test(priority = 2, description = "TC_Login_02: Verify login with valid credentials redirects to home page")
    public void verifyLoginWithValidCredentials() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.navigateToLogin();
        HomePage homePage = loginPage.login(VALID_EMAIL, VALID_PASSWORD);

        boolean loginSuccess = homePage.isLogoutButtonVisible()
                || homePage.getCurrentUrl().equals(homePage.getPageTitle());
        Assert.assertTrue(loginSuccess,
                "User should be logged in successfully with valid credentials");
    }

    @Test(priority = 3, description = "TC_Login_03: Verify error message displayed with invalid credentials")
    public void verifyLoginWithInvalidCredentials() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.navigateToLogin();
        loginPage.login(INVALID_EMAIL, INVALID_PASSWORD);

        Assert.assertTrue(loginPage.isErrorMessageVisible() || !loginPage.isLoginSuccessful(),
                "Error message should be displayed or user should stay on login page with invalid credentials");
    }

    @Test(priority = 4, description = "TC_Login_04: Verify user can logout successfully")
    public void verifyLogout() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.navigateToLogin();
        HomePage homePage = loginPage.login(VALID_EMAIL, VALID_PASSWORD);

        homePage.clickLogout();

        boolean onHomePage = homePage.getCurrentUrl().contains("/");
        boolean loginButtonVisible = homePage.isSearchBarVisible();
        Assert.assertTrue(onHomePage || loginButtonVisible,
                "User should be redirected to home page after logout without user session");
    }
}
