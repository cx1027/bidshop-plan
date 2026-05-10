package com.bidshop.tests;

import com.bidshop.base.BaseTest;
import com.bidshop.pages.SearchAndFilterPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class SearchAndFilterPageTest extends BaseTest {

    private SearchAndFilterPage searchAndFilterPage;

    @BeforeMethod
    public void setUpSearchAndFilterPage() {
        searchAndFilterPage = new SearchAndFilterPage(driver);
    }

    @Test(priority = 1, description = "TC_SF_01: Search product by name 'beef' and verify ProductNameAndDescriptionContain has beef")
    public void verifySearchByNameBeef() {
        searchAndFilterPage.search("beef");
        Assert.assertTrue(
                searchAndFilterPage.ProductNameAndDescriptionContain("beef"),
                "Search results for 'beef' should contain 'beef' in product name or description"
        );
    }

    @Test(priority = 2, description = "TC_SF_02: Search product by description 'mince' and verify ProductNameAndDescriptionContain has mince")
    public void verifySearchByDescriptionMince() {
        searchAndFilterPage.search("mince");
        Assert.assertTrue(
                searchAndFilterPage.ProductNameAndDescriptionContain("mince"),
                "Search results for 'mince' should contain 'mince' in product name or description"
        );
    }

    @Test(priority = 3, description = "TC_SF_03: Filter products by category 'Bakery' and verify AllProductCategoryContain are Bakery")
    public void verifyFilterByCategoryBakery() {
        searchAndFilterPage.selectCategory("Bakery");
        Assert.assertTrue(
                searchAndFilterPage.AllProductCategoryContain("Bakery"),
                "All filtered products should belong to the 'Bakery' category"
        );
    }

    @Test(priority = 4, description = "TC_SF_04: Filter products by category 'All categories' and verify there are 18 products")
    public void verifyFilterByAllCategories18Products() {
        searchAndFilterPage.selectCategory("All categories");
        Assert.assertEquals(
                searchAndFilterPage.getDisplayedProductCount(),
                18,
                "There should be 18 products when 'All categories' is selected"
        );
    }

    @Test(priority = 5, description = "TC_SF_05: Filter by Dairy and search 'Milk' - there should be one product: Anchor Full Cream Milk")
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
}
