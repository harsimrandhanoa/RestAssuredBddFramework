package node.base;

import org.testng.asserts.SoftAssert;

public class Utils {

	public SoftAssert softAssert;

	public Utils() {
		softAssert = new SoftAssert();
	}

	public SoftAssert getSoftAssert() {
		return softAssert;
	}

	public void setSoftAssert(SoftAssert softAssert) {
		this.softAssert = softAssert;
	}

	public void isEqual(String expectedValue, String actualValue, boolean hardAssert) {
		softAssert.assertEquals(actualValue, expectedValue);
		if (hardAssert) {
			softAssert.assertAll();
		}
	}

	public void isEquals(int expectedValue, int actualValue, boolean hardAssert) {
		softAssert.assertEquals(actualValue, expectedValue);
		if (hardAssert) {
			softAssert.assertAll();
		}
	}

	public void failTest(String message) {
		softAssert.fail(message);
		softAssert.assertAll();
	}

}
