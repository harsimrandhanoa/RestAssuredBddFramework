package node.context;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.mongodb.client.MongoDatabase;

import node.base.Utils;
import node.base.JsonValidations;
import node.database.DbActions;
import node.database.DbValidations;
import node.reporter.ReportsLogger;

public class TestContext {

	ExtentTest test;
	private ReportsLogger logger;
	private Utils utils; //
	private JsonValidations jsonValidations;
	private DbActions dbActions;
	public MongoDatabase database;
	private DbValidations dbValidations;

	public TestContext(String scenario, ExtentReports report, MongoDatabase database) {

		logger = new ReportsLogger(report);
		logger.createTest(scenario);
		logger.log("Starting Test named " + scenario);

		this.database = database;
		utils = new Utils();
		jsonValidations = new JsonValidations(logger, utils);
		dbActions = new DbActions(logger,utils, database);
		dbValidations = new DbValidations(logger,utils, database);
	}

	public Utils getAssertionsUtil() {
		return utils;
	}

	public JsonValidations getJsonValidations() {
		return jsonValidations;
	}

	public DbActions getDbActions() {
		return this.dbActions;
	}

	public DbValidations getDbValidations() {
		return this.dbValidations;
	}

	public ReportsLogger getLogger() {
		return this.logger;
	}

}
