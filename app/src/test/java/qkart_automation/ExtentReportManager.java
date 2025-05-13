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

    public static void log(Status status, String description){
        getExtentTest().log(status, description);
    }

    public static void endTest(){
        extentTestMap.remove();
    }
}
