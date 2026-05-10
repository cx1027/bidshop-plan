# Bidshop Automation

Selenium Java test automation framework using the Page Object Model (POM) design pattern for the Bidshop e-commerce website.

## Tech Stack

- **Java 11**
- **Selenium WebDriver 4.20.0** (Firefox via GeckoDriver)
- **TestNG 7.10.2**
- **WebDriverManager 5.9.0** (auto-manages driver binaries)
- **Maven 3.x**

## Project Structure

```
bidshop-automation/
├── pom.xml
├── testng.xml
├── .env
├── README.md
├── src/test/java/com/bidshop/
│   ├── base/
│   │   ├── BaseTest.java          # Firefox WebDriver setup/teardown
│   │   └── BasePage.java           # Common page utilities (wait, scroll, sendKeys)
│   ├── pages/
│   │   ├── HomePage.java
│   │   ├── LoginPage.java
│   │   ├── RegisterPage.java
│   │   ├── CartPage.java
│   │   ├── SearchAndFilterPage.java
│   │   └── CheckoutPage.java
│   ├── tests/
│   │   ├── HomePageTest.java       # 2 tests
│   │   ├── LoginPageTest.java      # 4 tests
│   │   ├── RegisterPageTest.java    # 5 tests
│   │   ├── CartPageTest.java       # 3 tests
│   │   ├── SearchAndFilterPageTest.java  # 10 tests
│   │   └── CheckoutPageTest.java   # 1 test
│   ├── utils/
│   │   ├── ConfigReader.java
│   │   ├── TestDataReader.java
│   │   ├── WaitUtils.java
│   │   └── ScreenshotUtils.java
│   └── listeners/
│       └── TestListener.java        # ITestListener for reporting
└── resources/
    ├── config.properties            # Base URL, browser, timeouts
    └── testdata/
        └── login-credentials.properties  # Test account credentials
```

## Test Coverage (25 Tests)

| Module | Test Class | # Tests | Test Cases |
|---|---|---|---|
| Home Page | `HomePageTest` | 2 | TC_Home_01: verifyHomePageLoaded, TC_Home_02: verifyProductsLoaded |
| Login | `LoginPageTest` | 4 | TC_Login_01: verifyLoginPageDisplayed, TC_Login_02: verifyLoginWithValidCredentials, TC_Login_03: verifyLoginWithInvalidCredentials, TC_Login_04: verifyLogout |
| Register | `RegisterPageTest` | 5 | TC_Reg_01: verifyRegisterPageDisplayed, TC_Reg_02: verifyRegisterWithValidData, TC_Reg_03: verifyRegisterNavigationToLogin, TC_Reg_04a: verifyRegisterWithBadEmail, TC_Reg_04b: verifyRegisterWithShortPassword |
| Cart | `CartPageTest` | 3 | TC_Cart_01: verifyCartPageDisplayed, TC_Cart_02: verifyCartTotalCalculation, TC_Cart_03: verifyRemoveItemFromCart |
| Search & Filter | `SearchAndFilterPageTest` | 10 | TC_SF_01: verifySearchByNameBeef, TC_SF_02: verifySearchByDescriptionMince, TC_SF_03: verifyFilterByCategoryBakery, TC_SF_04: verifyFilterByAllCategories18Products, TC_SF_05: verifyFilterDairyAndSearchMilk, TC_SF_06: verifySearchByNameBeef_LoggedIn, TC_SF_07: verifySearchByDescriptionMince_LoggedIn, TC_SF_08: verifyFilterByCategoryBakery_LoggedIn, TC_SF_09: verifyFilterByAllCategories18Products_LoggedIn, TC_SF_10: verifyFilterDairyAndSearchMilk_LoggedIn |
| Checkout | `CheckoutPageTest` | 1 | TC_Checkout_01: verifyCheckoutPageDisplayed |

## Prerequisites

1. **Install Java 11+**
   ```bash
   java -version
   ```

2. **Install Maven**
   ```bash
   mvn -version
   ```

3. **Install Firefox**
   - Download from [mozilla.org/firefox](https://www.mozilla.org/firefox/)
   - WebDriverManager will auto-download the matching GeckoDriver

4. **Create a test account manually**
   The tests use the credentials stored in `resources/testdata/login-credentials.properties`. Create an account on the app with:
   - Email: `testlogin@example.com`
   - Password: `password123`

## Configuration

Edit `resources/config.properties` to customize:

```properties
base.url=http://localhost:5173/
browser=firefox
headless=false
implicit.wait=10
explicit.wait=15
page.load.timeout=30
screenshot.on.failure=true
```

Or set in `.env`:

```env
BASE_URL=http://localhost:5173/
```

## Test Credentials

Credentials are stored in `resources/testdata/login-credentials.properties`. The default credentials are:

| Username | Password |
|---|---|
| `testlogin@example.com` | `password123` |

## Running Tests

### Run all tests
```bash
mvn test
```

### Run a specific test class
```bash
mvn test -Dtest=LoginPageTest
```

### Run with Maven Surefire HTML report
```bash
mvn clean test surefire-report:report
```

### Run in headless mode
Edit `resources/config.properties`:
```properties
headless=true
```

## Page Object Model

Each page class follows this pattern:

```java
public class LoginPage extends BasePage {

    @FindBy(xpath = "//input[@data-testid='login-email']")
    WebElement emailField;

    public LoginPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public HomePage login(String email, String password) {
        sendKeys(emailField, email);
        clickWithJs(loginSubmitButton);
        return new HomePage(driver);
    }
}
```

## Screenshots

On test failure, screenshots are captured automatically and saved to:

```
screenshots/<TestName>_<timestamp>.png
```

## License

MIT
