package com.bidshop.tests;

import com.bidshop.base.BaseTest;
import com.bidshop.pages.HomePage;
import com.bidshop.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class HomePageTest extends BaseTest {

    @Test(priority = 1, description = "TC_Home_01: Verify home page loads without error")
    public void verifyHomePageLoaded() {
        HomePage homePage = new HomePage(driver);
        String title = homePage.getPageTitle();
        Assert.assertNotNull(title, "Page title should not be null");
        Assert.assertFalse(title.isEmpty(), "Page title should not be empty");
    }

    @Test(priority = 2, description = "TC_Home_02: Verify product cards are visible on home page")
    public void verifyProductsLoaded() {
        HomePage homePage = new HomePage(driver);
        boolean productsVisible = homePage.areProductsLoaded();
        Assert.assertTrue(productsVisible, "At least one product should be displayed on the home page");
    }
}
