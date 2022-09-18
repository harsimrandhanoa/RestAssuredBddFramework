package node.reporter;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

public class ReportsLogger {

	ExtentTest test;
	ExtentReports report;

	public ReportsLogger(ExtentReports report) {
		this.report = report;
	}

	public void createTest(String scenarioName) {
		this.test = report.createTest(scenarioName);
	}

	public void log(String message) {
		this.test.log(Status.INFO, message);
	}

	public void testPass(String passMessage) {
		this.test.log(Status.PASS, passMessage);
	}

	public void testFail(String reason) {
		this.test.log(Status.FAIL, "Test failed due to reason :- " + reason);
	}

}
