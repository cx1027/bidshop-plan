package com.bidshop.tests;

import com.bidshop.base.BaseTest;
import com.bidshop.pages.CartPage;
import com.bidshop.pages.CheckoutPage;
import com.bidshop.pages.HomePage;
import com.bidshop.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CartPageTest extends BaseTest {

    private static final String VALID_EMAIL = "test1@gmail.com";
    private static final String VALID_PASSWORD = "123456";

    private void loginToAddItemToCart() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.navigateToLogin();
        HomePage homePage = loginPage.login(VALID_EMAIL, VALID_PASSWORD);
        homePage.clickAddToCart();
    }

    @Test(priority = 1, description = "TC_Cart_01: Verify cart page displays with items or empty state")
    public void verifyCartPageDisplayed() {
        CartPage cartPage = new CartPage(driver);
        cartPage.navigateToCart();

        boolean hasItems = cartPage.hasItems();
        boolean emptyState = cartPage.isEmptyStateVisible();
        Assert.assertTrue(hasItems || emptyState,
                "Cart page should display items or show empty state");
    }

    @Test(priority = 2, description = "TC_Cart_02: Verify subtotal, GST (15%), and total calculation")
    public void verifyCartTotalCalculation() {
        loginToAddItemToCart();

        CartPage cartPage = new CartPage(driver);
        cartPage.navigateToCart();

        if (!cartPage.hasItems()) {
            Assert.fail("Cart should have items to verify total calculation");
        }

        double subtotal = cartPage.getSubtotal();
        Assert.assertTrue(subtotal > 0, "Subtotal should be greater than zero");

        boolean gstCorrect = cartPage.verifyGstCalculation(subtotal);
        Assert.assertTrue(gstCorrect,
                "GST should be 15% of subtotal. Subtotal: " + subtotal
                + ", GST: " + cartPage.getGst() + ", Expected GST: " + (subtotal * 0.15));

        boolean totalCorrect = cartPage.verifyTotalCalculation(subtotal);
        Assert.assertTrue(totalCorrect,
                "Total should be Subtotal x 1.15 (including 15% GST). Subtotal: " + subtotal
                + ", Total: " + cartPage.getTotal() + ", Expected Total: " + (subtotal * 1.15));
    }

    @Test(priority = 3, description = "TC_Cart_03: Verify item can be removed from cart")
    public void verifyRemoveItemFromCart() {
        loginToAddItemToCart();

        CartPage cartPage = new CartPage(driver);
        cartPage.navigateToCart();

        int itemCountBefore = cartPage.getCartItemCount();
        Assert.assertTrue(itemCountBefore > 0, "Cart should have at least one item to remove");

        cartPage.removeItem();

        int itemCountAfter = cartPage.getCartItemCount();
        boolean emptyOrReduced = cartPage.isEmptyStateVisible() || itemCountAfter < itemCountBefore;
        Assert.assertTrue(emptyOrReduced,
                "Cart item count should decrease or empty state should be shown after removing an item");
    }
}
