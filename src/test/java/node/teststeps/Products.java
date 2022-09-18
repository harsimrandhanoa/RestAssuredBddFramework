package node.teststeps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.RestAssured;
import java.util.List;
import java.util.Map;

import node.base.Base;
import node.base.Product;
import io.cucumber.java.DataTableType;

public class Products extends Base {

	Product product;

	public Products(Hooks hooks) {
		super(hooks);
	}

	@And("I want to add a new {string}")
	public void wantToAdd(String thingToAdd) {
		log("I want to add a new " + thingToAdd);
	}

	@Given("I set relevant end point for {string}")
	public void setEndpoint(String endpoint) {
		setRelevantEndPoint(endpoint);
	}

	@And("I send a post request")
	public void postRequest(List<Product> productList) {
		product = new Product(productList.get(0).getName(), productList.get(0).getQuantity(),
				productList.get(0).getPrice());
		log("About to send a post request for product with fields - " + product.toString());
        sendPostRequest(product);
    }

	@Then("I get valid response code for {string} product")
	public void validateResponseCode(String action) {
		String responseCode = String.valueOf(resp.statusCode());
		validateResponseCode(action, responseCode);
	}

	@And("I get a valid response as well for {string} product")
	public void getResponse(String action) {
		validateProductJson(product, action);
	}

	@Then("The product is saved in mongodb with name {string} quantity {string} and price {string}")
	public void isProductAdded(String name, String quantity, String price) {
		if (product == null)
			log("The product is null so have to recreate it before checking db");

		if (product == null || !product.getName().equals(name) || !product.getPrice().equals(price)|| !product.getQuantity().equals(quantity)) // in case of an update product request
		this.product = new Product(name, quantity, price);
		log("The product to check in db is has fields - " + product.toString());
		dbValidations.isDocumentCreated(name, "products", true);
		dbValidations.validateDocumentFields(product, true);
	}

	@Given("We create Product {string} priced {string} of Quantity {string} in db to update or delete it")
	public void addProductToDb(String name, String price, String quantity) {
		product = new Product(name, quantity, price);
		log("Product to be added to db is " + product.toString());
		dbActions.createProduct(product);
	}

	@Given("{string} is available in db to be modified")
	public void findProduct(String productName) {
		log("Looking for product named " + productName + " in db");
		String productId = dbActions.getProductId(productName);
		if (productId == null)
			utils.failTest("Test failed because product to be modified in not avalaible in db");
		log("Theh product is present in db and id is " + productId);
	}

	@Given("I set relevant end point for {string} products for {string}")
	public void setProductEndPoint(String action, String productName) {
		log("Setting endpoint for " + productName);
		RestAssured.basePath = RestAssured.basePath + productName;
	}

	@Given("I send a update request for {string}  to change price to {string} and quantity to {string}")
	public void updateRequest(String name, String price, String quantity) {
		log("Path here in updates is " + RestAssured.basePath);
		product = new Product(name, quantity, price);
		log("Product sent in update request is " + product.toString());
		sendPatchRequest(product);
	}

	@And("I send a delete request for products")
	public void deleteRequest() {
		sendDeleteRequest();
	}

	@Then("The product {string} is deleted from mongodb")
	public void isProductDeleted(String name) {
		dbValidations.isDocumentDeleted(name, "products", true);
	}

	@DataTableType
	public Product entry(Map<String, String> entry) {
		return new Product(entry.get("ProductName"), entry.get("Quantity"), entry.get("Price"));
	}

}
