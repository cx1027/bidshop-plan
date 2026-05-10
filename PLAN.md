---
name: Selenium Java POM Test Framework
overview: Design a complete Selenium Java automation test framework using Page Object Model (POM) for the Bidshop e-commerce website. The framework will be built from scratch as a Maven project with TestNG, covering 4 pages and 5 modules with concise, critical test cases only. All tests use Firefox WebDriver via WebDriverManager.
todos:
  - id: step-1
    content: Create Maven pom.xml with Selenium Firefox, TestNG, WebDriverManager dependencies
    status: pending
  - id: step-2
    content: Create config.properties with base URL and Firefox browser config
    status: pending
  - id: step-3
    content: Create BaseTest.java with Firefox WebDriver init via WebDriverManager
    status: pending
  - id: step-4
    content: Create BasePage.java (common wait and interaction utilities)
    status: pending
  - id: step-5
    content: Implement all 5 Page Objects (HomePage, LoginPage, RegisterPage, CartPage, CheckoutPage)
    status: pending
  - id: step-6
    content: Implement WaitUtils and ScreenshotUtils helpers
    status: pending
  - id: step-7
    content: Implement TestListener for reporting
    status: pending
  - id: step-8
    content: Write 14 test cases across 5 test classes (HomePageTest: 2, LoginPageTest: 4, RegisterPageTest: 4, CartPageTest: 3, CheckoutPageTest: 1)
    status: pending
  - id: step-9
    content: Create testng.xml suite file
    status: pending
  - id: step-10
    content: Create README.md with run instructions
    status: pending
isProject: false
---

## Project Structure

```
bidshop-automation/
├── pom.xml
├── src/
│   └── test/
│       └── java/
│           └── com/bidshop/
│               ├── base/
│               │   ├── BaseTest.java          # Firefox WebDriver setup/teardown, screenshot capture
│               │   └── BasePage.java           # Common page utilities (wait, scroll, sendKeys)
│               ├── pages/
│               │   ├── HomePage.java          # Products listing, search, category, login button
│               │   ├── LoginPage.java          # Email/password login form, logout
│               │   ├── RegisterPage.java       # Full name/email/password registration form
│               │   ├── CartPage.java           # Cart items, quantity, prices, checkout
│               │   └── CheckoutPage.java        # Checkout flow
│               ├── tests/
│               │   ├── HomePageTest.java       # 2 critical tests
│               │   ├── LoginPageTest.java       # 4 critical tests
│               │   ├── RegisterPageTest.java   # 4 critical tests
│               │   ├── CartPageTest.java       # 3 critical tests
│               │   └── CheckoutPageTest.java   # 1 critical test
│               ├── utils/
│               │   ├── ConfigReader.java       # Read config.properties
│               │   ├── WaitUtils.java           # Explicit wait helpers (FluentWait)
│               │   └── ScreenshotUtils.java     # Capture screenshot on failure
│               └── listeners/
│                   └── TestListener.java        # ITestListener for screenshots and logging
├── resources/
│   ├── config.properties                       # Base URL, credentials, timeouts
│   └── testdata/
│       └── testdata.xlsx                       # Optional Excel test data
└── README.md
```

## Pages (Page Object Layer)

| Page Class | URL Fragment | Key WebElements |
|---|---|---|
| `HomePage` | `/` | Search bar, category links (Meat, Seafood, Bakery), "Log in to buy" buttons, product cards |
| `LoginPage` | `/login` | Email field, Password field, "Log in" button, "Create account" link, Logout button |
| `RegisterPage` | `/register` | Full Name field, Email field, Password field, "Create account" button, "Already have account? Log in" link |
| `CartPage` | `/cart` | Item rows (name, price), quantity inputs, Remove buttons, Subtotal, GST (15%), Total, "Checkout" button |
| `CheckoutPage` | `/checkout` | Shipping fields, Payment fields, "Place Order" button, Order summary |

## Test Cases (Test Layer — Critical Only)

### Module 1: Home Page
| Test Case ID | Name | Description | Expected Result |
|---|---|---|---|
| `TC_Home_01` | `verifyHomePageLoaded` | Open base URL, verify page title or main heading | Page loads without error |
| `TC_Home_02` | `verifyProductsLoaded` | Open base URL, verify product cards/items are visible | At least one product is displayed on the page |

### Module 2: Login
| Test Case ID | Name | Description | Expected Result |
|---|---|---|---|
| `TC_Login_01` | `verifyLoginPageDisplayed` | Click "Log in to buy" on home, verify redirected to `/login` | URL contains `/login`, login form visible |
| `TC_Login_02` | `verifyLoginWithValidCredentials` | Enter email `test1@gmail.com` and password `123456`, click login | Redirect to home page, user logged in |
| `TC_Login_03` | `verifyLoginWithInvalidCredentials` | Enter invalid email and password, click login | Error message displayed, stay on login page |
| `TC_Login_04` | `verifyLogout` | Login with valid credentials, click logout button | User logged out, redirected to home page without user session |

### Module 3: Register
| Test Case ID | Name | Description | Expected Result |
|---|---|---|---|
| `TC_Reg_01` | `verifyRegisterPageDisplayed` | Navigate to `/register`, verify all form fields | Name, Email, Password fields and "Create account" button visible |
| `TC_Reg_02` | `verifyRegisterWithValidData` | Fill all fields with valid data, click create account | Redirect to home or login page |
| `TC_Reg_03` | `verifyRegisterNavigationToLogin` | Click "Already have account? Log in" link | Redirected to `/login` page |
| `TC_Reg_04` | `verifyRegisterWithInvalidData` | Fill fields with invalid email, empty full name, and password `< 5` characters | Validation error or form submission rejected, stay on register page |

### Module 4: Cart
| Test Case ID | Name | Description | Expected Result |
|---|---|---|---|
| `TC_Cart_01` | `verifyCartPageDisplayed` | Navigate to `/cart` when empty or with items | Cart page loads, item list or empty state visible |
| `TC_Cart_02` | `verifyCartTotalCalculation` | Add items to cart, verify Subtotal + 15% GST = Total | Total = Subtotal × 1.15 |
| `TC_Cart_03` | `verifyRemoveItemFromCart` | Add item, click remove, verify item removed | Cart item count decreases or empty state shown |

### Module 5: Checkout
| Test Case ID | Name | Description | Expected Result |
|---|---|---|---|
| `TC_Checkout_01` | `verifyCheckoutPageDisplayed` | From cart with items, click "Checkout" | Navigate to `/checkout`, order summary visible |

## POM Design Pattern Per Page

```java
public class LoginPage {

    // Page Factory - Object Repository (OR):
    @FindBy(xpath = "//input[@data-testid='login-email']")
    WebElement email;

    @FindBy(xpath = "//input[@data-testid='login-password']")
    WebElement password;

    @FindBy(xpath = "//button[@data-testid='login-submit']")
    WebElement loginBtn;

    @FindBy(xpath = "//a[contains(text(),'Register here')]")
    WebElement registerLink;

    // Initializing the Page Objects:
    public LoginPage() {
        PageFactory.initElements(driver, this);
    }

    // Page Actions:
    public String validateLoginPageTitle() {
        return driver.getTitle();
    }

    public void navigateToLogin() {
        driver.get(prop.getProperty("url") + "login");
    }

    public boolean validateEmailField() {
        return email.isDisplayed();
    }

    public boolean validatePasswordField() {
        return password.isDisplayed();
    }

    public HomePage login(String emailId, String pwd) {
        email.sendKeys(emailId);
        password.sendKeys(pwd);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", loginBtn);
        return new HomePage();
    }
}
```

Each page object follows this exact pattern: `@FindBy` locators at the top, `PageFactory.initElements()` in the constructor, and action methods below. Pages return new page objects on navigation (e.g., `LoginPage.login()` returns `HomePage`).

## Test Credentials

When any test requires a logged-in user session, use the following credentials:

| Username | Password |
|---|---|
| `test1@gmail.com` | `123456` |

> Note: `TC_Login_02` uses the same credentials for its valid login verification. Other modules (Cart, Checkout) that require authentication call `LoginPage.login("test1@gmail.com", "123456")` to establish the session before proceeding.

## WebDriver — Firefox Only

All tests use **Firefox WebDriver (GeckoDriver)** via WebDriverManager. The browser is initialized in `BaseTest.java`.

```java
WebDriverManager.firefoxdriver().setup();
driver = new FirefoxDriver();
```

- Browser: **Mozilla Firefox** (latest stable)
- Driver manager: **WebDriverManager** (auto-downloads matching GeckoDriver version)
- Headless mode: configurable via `config.properties`

## pom.xml Dependencies

```xml
<dependencies>
  <!-- Selenium -->
  <dependency>
    <groupId>org.seleniumhq.selenium</groupId>
    <artifactId>selenium-java</artifactId>
    <version>4.20.0</version>
  </dependency>
  <!-- TestNG -->
  <dependency>
    <groupId>org.testng</groupId>
    <artifactId>testng</artifactId>
    <version>7.10.2</version>
  </dependency>
  <!-- WebDriverManager -->
  <dependency>
    <groupId>io.github.bonigarcia</groupId>
    <artifactId>webdrivermanager</artifactId>
    <version>5.9.0</version>
  </dependency>
</dependencies>
```

## Implementation Sequence

1. **Step 1** — Create Maven project with `pom.xml`, add Selenium (Firefox), TestNG, and WebDriverManager dependencies
2. **Step 2** — Create `config.properties` with `base.url=http://localhost:5173`, `browser=firefox`
3. **Step 3** — Build `BaseTest.java` with Firefox WebDriver init via WebDriverManager, `@BeforeMethod`/`@AfterMethod` teardown
4. **Step 4** — Build `BasePage.java` (common wait, sendKeys, click helpers)
5. **Step 5** — Implement all 5 Page Objects (HomePage, LoginPage, RegisterPage, CartPage, CheckoutPage) with `PageFactory` using `data-testid` attributes
6. **Step 6** — Implement `WaitUtils.java` and `ScreenshotUtils.java` utilities
7. **Step 7** — Implement `TestListener.java` for TestNG reporting
8. **Step 8** — Write all 14 test cases across 5 test classes
9. **Step 9** — Create `testng.xml` suite file
10. **Step 10** — Create `README.md` with run instructions

## Test Execution

```bash
# Run all tests
mvn test

# Run specific test class
mvn test -Dtest=LoginPageTest

# Run with Maven Surefire report
mvn clean test surefire-report:report
```
