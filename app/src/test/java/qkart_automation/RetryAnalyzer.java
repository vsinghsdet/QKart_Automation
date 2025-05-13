package qkart_automation;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

import com.aventstack.extentreports.Status;

public class RetryAnalyzer implements IRetryAnalyzer {

    private int retryCount = 0;
    private static final int maxRetryCount = 2; // Retry twice

    @Override
    public boolean retry(ITestResult result) {
        if (retryCount < maxRetryCount) {
            retryCount++;
            String retryDescription = "Retrying test '" + result.getName() + "' (" + retryCount + "/" + maxRetryCount + ")";
            Object testClass = result.getInstance();
            ExtentReportManager.log(Status.WARNING, retryDescription);

            return true;
        }
        return false;
    }
}
