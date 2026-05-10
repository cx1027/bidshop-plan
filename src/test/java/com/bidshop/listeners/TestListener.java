package com.bidshop.listeners;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import com.bidshop.utils.ScreenshotUtils;

import java.util.Set;

public class TestListener implements ITestListener {

    private ScreenshotUtils screenshotUtils = new ScreenshotUtils();

    @Override
    public void onTestStart(ITestResult result) {
        System.out.println(">>> TEST STARTED: " + result.getMethod().getMethodName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        System.out.println(">>> TEST PASSED: " + result.getMethod().getMethodName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        System.out.println(">>> TEST FAILED: " + result.getMethod().getMethodName());
        Object webDriverAttribute = result.getTestContext().getAttribute("driver");
        if (webDriverAttribute instanceof WebDriver) {
            WebDriver driver = (WebDriver) webDriverAttribute;
            screenshotUtils = new ScreenshotUtils(driver);
            String screenshotPath = screenshotUtils.captureScreenshot(result.getMethod().getMethodName());
            System.out.println(">>> Screenshot saved: " + screenshotPath);
        }
        if (result.getThrowable() != null) {
            System.err.println(">>> Exception: " + result.getThrowable().getMessage());
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        System.out.println(">>> TEST SKIPPED: " + result.getMethod().getMethodName());
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        System.out.println(">>> TEST FAILED BUT WITHIN SUCCESS PERCENTAGE: " + result.getMethod().getMethodName());
    }

    @Override
    public void onStart(ITestContext context) {
        System.out.println("========================================");
        System.out.println(">>> SUITE STARTED: " + context.getName());
        System.out.println("========================================");
    }

    @Override
    public void onFinish(ITestContext context) {
        System.out.println("========================================");
        System.out.println(">>> SUITE FINISHED: " + context.getName());
        int passed = context.getPassedTests().size();
        int failed = context.getFailedTests().size();
        int skipped = context.getSkippedTests().size();
        System.out.println("Results - Passed: " + passed + ", Failed: " + failed + ", Skipped: " + skipped);
        System.out.println("========================================");
    }

    @Override
    public void onConfigurationFailure(ITestResult result) {
        System.out.println(">>> CONFIGURATION FAILED: " + result.getMethod().getMethodName());
        Object webDriverAttribute = result.getTestContext().getAttribute("driver");
        if (webDriverAttribute instanceof WebDriver) {
            WebDriver driver = (WebDriver) webDriverAttribute;
            screenshotUtils = new ScreenshotUtils(driver);
            screenshotUtils.captureScreenshot("CONFIG_FAILURE_" + result.getMethod().getMethodName());
        }
    }
}
