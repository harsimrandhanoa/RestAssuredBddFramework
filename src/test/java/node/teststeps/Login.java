package node.teststeps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import node.base.Base;
import node.base.LoginCredentials;

public class Login extends Base {

	LoginCredentials loginCredentials;

	public Login(Hooks hooks) {
		super(hooks);
		validStatusCode = testProp.getProperty("validStatusCode");
		invalidStatusCode = testProp.getProperty("invalidStatusCode");
	}

	@Given("I login into app using {string} and {string}")
	public void login(String email, String password) {
		
		this.loginCredentials = new LoginCredentials(email, password);
		log("About to login into the app using email "+ email + " and password "+password);
		sendLoginPostRequest(loginCredentials);
	}

	@And("I get correct response code for {string} login")
	public void getResponseCode(String responseCode) {
		validateLoginResponse(responseCode);
	}

	@And("I have the authorisation token available")
	public void auth() {
		getSessionToken();
	}

	@Given("I look for valid session or else login into api using email {string} and password {string}")
	public void lookForSession(String string, String string2) {
		if (token == null) {
			log("Sesion is null so we are going to login into the app");
			login(string, string2);
			getResponseCode("valid");
			auth();
		}
	}
}
