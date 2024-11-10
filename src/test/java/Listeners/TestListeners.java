package Listeners;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class TestListeners implements ITestListener {

	/* Creation of ExtentReports object */
	public static ExtentReports report;

	/* Creation of ExtentReporter object */
	public static ExtentSparkReporter reporter;

	/* Creation of ExtentTest object */
	public static ExtentTest test;

	public static String reportName;

	@Override
	public void onTestSuccess(ITestResult result) {
		// TODO Auto-generated method stub

		test = report.createTest(result.getMethod().getDescription());
		test.assignAuthor("Author 1");
		test.assignCategory(result.getMethod().getGroups());
		test.assignDevice("Laptop");
		test.log(Status.PASS, "TEST PASSED");
	}

	@Override
	public void onTestFailure(ITestResult result) {
		// TODO Auto-generated method stub
		test = report.createTest(result.getMethod().getDescription());
		test.assignAuthor("Author 2");
		test.assignCategory(result.getMethod().getGroups());
		test.assignDevice("Mac Book");
		test.log(Status.FAIL, "TEST FAILED");
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		// TODO Auto-generated method stub
		test = report.createTest(result.getMethod().getDescription());
		test.assignAuthor("Author 2");
		test.assignCategory(result.getMethod().getGroups());
		test.assignDevice("Mac Book");
		test.log(Status.SKIP, "TEST SKIPPED");
	}

	@Override
	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub

		/*
		 * This contains part that will be executed once as soon as execution started
		 * but before execution of Test Suite
		 */

		reportName = new SimpleDateFormat("dd-MM-yyy_hh.mm.ss").format(new Date()); // retuns date with time i.e.
																					// timestamp

		// ExtentReporter class's implementation class's like SparkReporter class object
		reporter = new ExtentSparkReporter(".\\Reports\\" + reportName + ".html"); // This is the path of reportname
																					// with extension.

		// Define configuration for reporter object.
		reporter.config().setDocumentTitle("RestAPITestAutomationPROJECT1");
		reporter.config().setReportName("REST-ASSURED API REPORT1");
		reporter.config().setTheme(Theme.DARK);

		// ExtentReport object creation
		report = new ExtentReports();

		// Define config for reports object

		report.attachReporter(reporter); // Linking report object with Reporter object .
		report.setSystemInfo("Application Module", "Users Module");
		report.setSystemInfo("OS", System.getProperty("os.name"));
		report.setSystemInfo("User's Name", System.getProperty("user.name"));
		report.setSystemInfo("Environment", "QA");
		report.setSystemInfo("User", "Amit");
	}

	@Override
	public void onFinish(ITestContext context) {
		report.flush();
	}

}
