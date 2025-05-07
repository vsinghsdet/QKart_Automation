package qkart_automation;


import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

public class ExtentReportManager {

    static ExtentReports extentReports = ReportSingleton.getExtentReport();
    static ThreadLocal<ExtentTest> extentTestMap = new ThreadLocal<>();

    public static ExtentTest startTest(String testName){
        ExtentTest extentTest = extentReports.createTest(testName);
        extentTestMap.set(extentTest);
        return extentTest;
    }

    public static ExtentTest getExtentTest(){
        return extentTestMap.get();
    }

    public static void logInfo(String info){
        getExtentTest().log(Status.INFO, info);
    }

    public static void logPass(String passDescription){
        getExtentTest().log(Status.PASS, passDescription);
    }

    public static void logFail(String failDescription){
        getExtentTest().log(Status.FAIL, failDescription);
    }

    public static void logSkip(String skipDescription){
        getExtentTest().log(Status.SKIP, skipDescription);
    }

    public static void endTest(){
        extentTestMap.remove();
    }
}
