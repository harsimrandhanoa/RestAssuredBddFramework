package node.teststeps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.RestAssured;
import node.base.Base;
import node.base.Order;
import node.base.Product;

public class Basket extends Base {

	Order order;
	Product product;
	String productId;

	public Basket(Hooks hooks) {
		super(hooks);
	}

	@Given("Product {string} priced {string} of Quantity {string} is already added to database")
	public void addProductToDb(String name, String price, String quantity) {
		product = new Product(name, quantity, price);
		productId = dbActions.createProduct(product);
		log("The product added is " + product.toString());
	}

	@And("I send a post request to basket for  basket {string} and quantity {string}")
	public void postRequest(String basketName, String quantity) {
		this.order = new Order(productId, basketName, quantity);
		log("The order to be send in post request is " + order.toString());
		sendPostRequest(order);
	}

	@Then("I get valid response code for {string} orders")
	public void getResponseCode(String action) {
		String responseCode = String.valueOf(resp.statusCode());
		validateResponseCode(action, responseCode);
	}

	@And("I get a valid response as well for {string} orders")
	public void getResponse(String action) {
		validateBasketJson(order, action);
	}

	@Then("The order named {string} is saved in mongodb")
	public void isOrderCreated(String orderName) {
		dbValidations.isDocumentCreated(orderName, "orders", true);
		dbValidations.validateDocumentFields(order, true);
	}

	@Given("Order {string} for Quantity {string} with {string} ,{string} ,{string} is already added to database")
	public void order_for_quantity_with_is_already_added_to_database(String orderName, String orderQuantity,
			String productName, String productQuantity, String productPrice) {
		product = new Product(productName, productQuantity, productPrice);
		productId = dbActions.createProduct(product);
		log("The product is " + product.toString());
		order = new Order(productId, orderName, orderQuantity);
		log("The order to be added to db is " + order.toString());
		dbActions.createOrder(order);
	}

	@Given("I set relevant end point for {string} orders for {string}")
	public void setBasketEndPoint(String string, String string2) {
		RestAssured.basePath = RestAssured.basePath + string2;
		log("Rest endpoint for individual orders is " + RestAssured.basePath);
	}

	@And("I send a get request for orders")
	public void getRequest() {
		sendGetRequest();
	}

	@Then("I get valid response for get orders for {string} and {string} and {string}")
	public void validateGetOrder(String productName, String basketName, String basketQuantity) {
		String productId = dbActions.getProductId(productName);
		if (order == null)
			order = new Order(productId, basketName, basketQuantity);
		log("Validating the get response for order with fields - "+order.toString());
        validateGetBasketJson(order);
	}

	@And("I send a delete request for orders")
	public void deleteRequest() {
		sendDeleteRequest();
	}

	@Then("The order named {string} is deleted from mongodb")
	public void isOrderDeleted(String orderName) {
		dbValidations.isDocumentDeleted(orderName, "orders", true);
	}

	// @DataTableType
	// public Order entry(Map<String, String> entry) {
	// // return new
	// Order(entry.get("ProductName"),entry.get("Price"),entry.get("ProductQuantity"),entry.get("BasketName"),entry.get("BasketQuantity"));
	// }

}
