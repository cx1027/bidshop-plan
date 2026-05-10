package com.bidshop.pages;

import com.bidshop.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public class SearchAndFilterPage extends BasePage {

    @FindBy(xpath = "//input[@placeholder='Search products\u2026'] | //input[@role='searchbox']")
    WebElement searchBar;

    @FindBy(xpath = "//select | //input[@role='combobox']")
    WebElement categoryDropdown;

    @FindBy(xpath = "//article")
    List<WebElement> productCards;

    @FindBy(xpath = "//article//h3")
    List<WebElement> productNames;

    @FindBy(xpath = "//article//p")
    List<WebElement> productDescriptions;

    public SearchAndFilterPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    private By articleLocator() {
        return By.xpath("//article");
    }

    private By loadingTextLocator() {
        return By.xpath("//p[contains(text(),'Loading')]");
    }

    public void waitForProductsToLoad() {
        try {
            wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(articleLocator(), 0));
        } catch (Exception ignored) {
        }
    }

    public void waitForLoadingToDisappear() {
        try {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(loadingTextLocator()));
        } catch (Exception ignored) {
        }
    }

    public void search(String keyword) {
        waitForLoadingToDisappear();
        waitForElementVisible(searchBar);
        searchBar.clear();
        searchBar.sendKeys(keyword);
        waitForProductsToLoad();
    }

    public void selectCategory(String category) {
        waitForLoadingToDisappear();
        WebElement dropdown = driver.findElement(By.xpath("//select | //input[@role='combobox']"));
        waitForElementVisible(dropdown);

        try {
            Select select = new Select(dropdown);
            select.selectByVisibleText(category);
        } catch (Exception e) {
            dropdown.sendKeys(category);
        }
        waitForProductsToLoad();
    }

    public int getDisplayedProductCount() {
        waitForLoadingToDisappear();
        waitForProductsToLoad();
        return productCards.size();
    }

    public boolean ProductNameAndDescriptionContain(String keyword) {
        waitForLoadingToDisappear();
        waitForProductsToLoad();
        String lowerKeyword = keyword.toLowerCase();

        for (WebElement card : productCards) {
            String cardText = card.getText().toLowerCase();
            if (!cardText.contains(lowerKeyword)) {
                return false;
            }
        }
        return true;
    }

    public boolean AllProductCategoryContain(String category) {
        waitForLoadingToDisappear();
        waitForProductsToLoad();
        String lowerCategory = category.toLowerCase();

        for (WebElement card : productCards) {
            String cardText = card.getText();
            if (!cardText.toLowerCase().contains(lowerCategory)) {
                return false;
            }
        }
        return true;
    }

    public boolean ProductCategoryContain(String category) {
        return AllProductCategoryContain(category);
    }

    public String getFirstProductName() {
        waitForLoadingToDisappear();
        waitForProductsToLoad();
        if (!productNames.isEmpty()) {
            return productNames.get(0).getText();
        }
        return "";
    }

    public List<String> getAllProductNames() {
        waitForLoadingToDisappear();
        waitForProductsToLoad();
        return productNames.stream()
                .map(WebElement::getText)
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .toList();
    }
}
