package com.bidshop.tests;

import com.bidshop.base.BaseTest;
import com.bidshop.pages.HomePage;
import com.bidshop.pages.LoginPage;
import com.bidshop.pages.SearchAndFilterPage;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class SearchAndFilterPageTest extends BaseTest {

    private SearchAndFilterPage searchAndFilterPage;
    private HomePage homePage;

    @BeforeMethod
    public void setUpSearchAndFilterPage() {
        searchAndFilterPage = new SearchAndFilterPage(driver);
    }

    @AfterMethod
    public void logoutIfLoggedIn() {
        try {
            homePage = new HomePage(driver);
            if (homePage.isLogoutButtonVisible()) {
                homePage.clickLogout();
            }
        } catch (Exception ignored) {
        }
    }

    // ============================================================
    // GUEST USER TESTS (no login required)
    // ============================================================

    @Test(priority = 1, groups = "guest",
            description = "TC_SF_01: Search product by name 'beef' and verify ProductNameAndDescriptionContain has beef")
    public void verifySearchByNameBeef() {
        searchAndFilterPage.search("beef");
        Assert.assertTrue(
                searchAndFilterPage.ProductNameAndDescriptionContain("beef"),
                "Search results for 'beef' should contain 'beef' in product name or description"
        );
    }

    @Test(priority = 2, groups = "guest",
            description = "TC_SF_02: Search product by description 'mince' and verify ProductNameAndDescriptionContain has mince")
    public void verifySearchByDescriptionMince() {
        searchAndFilterPage.search("mince");
        Assert.assertTrue(
                searchAndFilterPage.ProductNameAndDescriptionContain("mince"),
                "Search results for 'mince' should contain 'mince' in product name or description"
        );
    }

    @Test(priority = 3, groups = "guest",
            description = "TC_SF_03: Filter products by category 'Bakery' and verify AllProductCategoryContain are Bakery")
    public void verifyFilterByCategoryBakery() {
        searchAndFilterPage.selectCategory("Bakery");
        Assert.assertTrue(
                searchAndFilterPage.AllProductCategoryContain("Bakery"),
                "All filtered products should belong to the 'Bakery' category"
        );
    }

    @Test(priority = 4, groups = "guest",
            description = "TC_SF_04: Filter products by category 'All categories' and verify there are 18 products")
    public void verifyFilterByAllCategories18Products() {
        searchAndFilterPage.selectCategory("All categories");
        Assert.assertEquals(
                searchAndFilterPage.getDisplayedProductCount(),
                18,
                "There should be 18 products when 'All categories' is selected"
        );
    }

    @Test(priority = 5, groups = "guest",
            description = "TC_SF_05: Filter by Dairy and search 'Milk' - there should be one product: Anchor Full Cream Milk")
    public void verifyFilterDairyAndSearchMilk() {
        searchAndFilterPage.selectCategory("Dairy");
        searchAndFilterPage.search("Milk");
        Assert.assertEquals(
                searchAndFilterPage.getDisplayedProductCount(),
                1,
                "Filtering by 'Dairy' and searching 'Milk' should return exactly 1 product"
        );
        Assert.assertEquals(
                searchAndFilterPage.getFirstProductName(),
                "Anchor Full Cream Milk",
                "The single product should be 'Anchor Full Cream Milk'"
        );
    }

    // ============================================================
    // LOGGED-IN USER TESTS (login required)
    // ============================================================

    private void loginAsTestUser() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.navigateToLogin();
        HomePage homePage = loginPage.login("test1@gmail.com", "123456");
        searchAndFilterPage = new SearchAndFilterPage(driver);
    }

    @Test(priority = 6, groups = "loggedin",
            description = "TC_SF_06: [LoggedIn] Search product by name 'beef' and verify ProductNameAndDescriptionContain has beef")
    public void verifySearchByNameBeef_LoggedIn() {
        loginAsTestUser();
        searchAndFilterPage.search("beef");
        Assert.assertTrue(
                searchAndFilterPage.ProductNameAndDescriptionContain("beef"),
                "[LoggedIn] Search results for 'beef' should contain 'beef' in product name or description"
        );
    }

    @Test(priority = 7, groups = "loggedin",
            description = "TC_SF_07: [LoggedIn] Search product by description 'mince' and verify ProductNameAndDescriptionContain has mince")
    public void verifySearchByDescriptionMince_LoggedIn() {
        loginAsTestUser();
        searchAndFilterPage.search("mince");
        Assert.assertTrue(
                searchAndFilterPage.ProductNameAndDescriptionContain("mince"),
                "[LoggedIn] Search results for 'mince' should contain 'mince' in product name or description"
        );
    }

    @Test(priority = 8, groups = "loggedin",
            description = "TC_SF_08: [LoggedIn] Filter products by category 'Bakery' and verify AllProductCategoryContain are Bakery")
    public void verifyFilterByCategoryBakery_LoggedIn() {
        loginAsTestUser();
        searchAndFilterPage.selectCategory("Bakery");
        Assert.assertTrue(
                searchAndFilterPage.AllProductCategoryContain("Bakery"),
                "[LoggedIn] All filtered products should belong to the 'Bakery' category"
        );
    }

    @Test(priority = 9, groups = "loggedin",
            description = "TC_SF_10: [LoggedIn] Filter products by category 'All categories' and verify there are 18 products")
    public void verifyFilterByAllCategories18Products_LoggedIn() {
        loginAsTestUser();
        searchAndFilterPage.selectCategory("All categories");
        Assert.assertEquals(
                searchAndFilterPage.getDisplayedProductCount(),
                18,
                "[LoggedIn] There should be 18 products when 'All categories' is selected"
        );
    }

    @Test(priority = 10, groups = "loggedin",
            description = "TC_SF_10: [LoggedIn] Filter by Dairy and search 'Milk' - there should be one product: Anchor Full Cream Milk")
    public void verifyFilterDairyAndSearchMilk_LoggedIn() {
        loginAsTestUser();
        searchAndFilterPage.selectCategory("Dairy");
        searchAndFilterPage.search("Milk");
        Assert.assertEquals(
                searchAndFilterPage.getDisplayedProductCount(),
                1,
                "[LoggedIn] Filtering by 'Dairy' and searching 'Milk' should return exactly 1 product"
        );
        Assert.assertEquals(
                searchAndFilterPage.getFirstProductName(),
                "Anchor Full Cream Milk",
                "[LoggedIn] The single product should be 'Anchor Full Cream Milk'"
        );
    }
}
