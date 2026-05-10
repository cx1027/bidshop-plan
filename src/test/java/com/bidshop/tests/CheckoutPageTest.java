package com.bidshop.tests;

import com.bidshop.base.BaseTest;
import com.bidshop.pages.CartPage;
import com.bidshop.pages.CheckoutPage;
import com.bidshop.pages.HomePage;
import com.bidshop.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CheckoutPageTest extends BaseTest {

    private static final String VALID_EMAIL = "test1@gmail.com";
    private static final String VALID_PASSWORD = "123456";

    private void addItemToCartAndProceedToCheckout() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.navigateToLogin();
        HomePage homePage = loginPage.login(VALID_EMAIL, VALID_PASSWORD);
        homePage.clickAddToCart();
        CartPage cartPage = new CartPage(driver);
        cartPage.navigateToCart();
        if (cartPage.hasItems()) {
            cartPage.clickCheckout();
        }
    }

    @Test(priority = 1, description = "TC_Checkout_01: Verify checkout page displays with order summary")
    public void verifyCheckoutPageDisplayed() {
        addItemToCartAndProceedToCheckout();

        CheckoutPage checkoutPage = new CheckoutPage(driver);

        Assert.assertTrue(checkoutPage.getCurrentUrl().contains("/checkout"),
                "URL should contain '/checkout' after clicking Checkout from cart");
        Assert.assertTrue(checkoutPage.isShippingFieldsVisible()
                        || checkoutPage.isOrderSummaryVisible(),
                "Shipping fields or order summary should be visible on checkout page");
    }
}
