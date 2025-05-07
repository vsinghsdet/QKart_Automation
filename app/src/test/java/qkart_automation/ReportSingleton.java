package qkart_automation;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ReportSingleton {

    static ExtentReports extentReports;
    
    static LocalDateTime now = LocalDateTime.now();
    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
    static long formattedTimeStamp = Long.parseLong(now.format(formatter));

    static final String reportFilePath = System.getProperty("user.dir")+File.separator+"reports"+File.separator+"learning"+formattedTimeStamp+".html";

    public static ExtentReports getExtentReport(){
        if(extentReports==null){
           ExtentSparkReporter spark = new ExtentSparkReporter(reportFilePath);
           extentReports = new ExtentReports();
           extentReports.attachReporter(spark);
        }  
        return extentReports;
    }
}
