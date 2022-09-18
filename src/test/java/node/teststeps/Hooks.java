package node.teststeps;

import java.io.FileInputStream;
import java.util.Properties;

import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.restassured.RestAssured;
import node.reporter.ReportsLogger;
import node.reports.ExtentManager;

import node.context.TestContext;

public class Hooks {

	ExtentTest test;

	private SoftAssert softAssert;
	private TestContext testContext;
	private ReportsLogger logger;

	private static MongoDatabase database;
	public static ExtentReports report;
	public static MongoClient client;

	private static boolean firstRun = false;
	public static Properties testProp;

	@Before
	public void beforeSuite() {
		if (!firstRun) {
			report = ExtentManager.getInstance();
			try {
				testProp = new Properties();
				FileInputStream fs = new FileInputStream(
						System.getProperty("user.dir") + "//src//test//resources//project.properties");
				testProp.load(fs);

				client = MongoClients.create(testProp.getProperty("mongoDb"));
				System.out.println("connected to local mongo db");

				database = client.getDatabase("RestShop");

			} catch (Exception e) {
				e.printStackTrace();
			}

			RestAssured.baseURI = testProp.getProperty("baseUrl");
			RestAssured.port = Integer.parseInt(testProp.getProperty("portNumber"));
		}
		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				report.flush();
				database.getCollection("baskets").drop();
				database.getCollection("products").drop();
				client.close();
			}
		});
		firstRun = true;
	}

	@Before
	public void beforeAll(Scenario scenario) {
		testContext = new TestContext(scenario.getName(), report, database);
		softAssert = testContext.getAssertionsUtil().getSoftAssert();
		logger = testContext.getLogger();
		RestAssured.basePath = testProp.getProperty("Login");
	}

	@After
	public void afterAll(Scenario scenario) {
		Boolean unknownReason = true;

		try {
			getSoftAssert().assertAll();
		} catch (AssertionError error) {
			logger.testFail(error.getMessage());
			unknownReason = false;
		}

		if (scenario.isFailed() && unknownReason) {
			logger.testFail("Reason known. Please check above logs");
		}

		if (!scenario.isFailed() && unknownReason) {
			logger.testPass(scenario.getName() + " has passed");
		}

		getSoftAssert().assertAll();

	}

	public ReportsLogger getReportsLogger() {
		return this.logger;
	}

	public TestContext getTestContext() {
		return this.testContext;
	}

	public SoftAssert getSoftAssert() {
		return this.softAssert;
	}

}
