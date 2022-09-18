package node.base;

import node.reporter.ReportsLogger;

public class JsonValidations {
	ReportsLogger logger;
	protected Utils utils;

	public void setManager(ReportsLogger logger) {
		this.logger = logger;
	}

	public JsonValidations(ReportsLogger logger, Utils utils) {
		this.logger = logger;
		this.utils = utils;
	}

	public void validateResults(String expectedValue, String actualValue, boolean hardAssert) {
		utils.isEqual(expectedValue, actualValue, hardAssert);
	}

}
