package node.base;

import static io.restassured.RestAssured.given;

import java.io.PrintStream;
import java.io.StringWriter;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.io.output.WriterOutputStream;
import org.testng.asserts.SoftAssert;

import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import node.context.TestContext;
import node.database.DbActions;
import node.database.DbValidations;
import node.reporter.ReportsLogger;
import node.teststeps.Hooks;

public class Base {
	
	
    protected static String token;
	
	protected  Hooks hooks;
	protected SoftAssert softAssert;
    protected JsonValidations jsonValidations;
	protected DbValidations dbValidations;
	protected DbActions dbActions;
   
	protected StringWriter requestWriter;
	protected PrintStream requestCapture;
	protected Response resp;
	
	protected String validStatusCode;
	protected String invalidStatusCode;
	
	protected Utils utils;
	protected TestContext testContext;
	
	protected ReportsLogger logger;
    protected  Properties testProp;

	public Base(Hooks hooks){

      testContext = hooks.getTestContext();
      testProp = Hooks.testProp;
	  utils = testContext.getAssertionsUtil();
	  softAssert = utils.getSoftAssert();
	  jsonValidations = testContext.getJsonValidations();
	  dbActions = testContext.getDbActions();
	  dbValidations = testContext.getDbValidations();
	  logger = testContext.getLogger();

     	
      requestWriter = new StringWriter();
	  requestCapture = new PrintStream(new WriterOutputStream(requestWriter), true);
	}
	
   protected void validateLoginResponse(String responseCode){
	 if(responseCode.equals("valid"))
		  jsonValidations.validateResults(validStatusCode,String.valueOf(resp.statusCode()),true);
      else
		  jsonValidations.validateResults(invalidStatusCode,String.valueOf(resp.statusCode()),true);
	}
	
	protected void logResponse(){
        logger.log("Response from server is :- "+resp.prettyPrint());
	}
	
	protected void log(String message){
        logger.log(message);
	}
	
	protected void sendLoginPostRequest(Object obj){
        resp = given().filter(new RequestLoggingFilter(requestCapture)).contentType(ContentType.JSON).log().body().body(obj).post();
        logResponse();
    }
	
	protected void sendPostRequest(Object obj){
	 logger.log("About to send a post request to server");
     resp = given().filter(new RequestLoggingFilter(requestCapture)).contentType(ContentType.JSON).headers("Authorization","Bearer "+token).log().body().body(obj).post();
     logResponse();
     }
	
	protected void sendPatchRequest(Product product){
		logger.log("About to send a patch request to server");
        resp = given().filter(new RequestLoggingFilter(requestCapture)).contentType(ContentType.JSON).headers("Authorization","Bearer "+token).log().body().body(product).patch();
	    logResponse();
	     }
	
	protected void sendGetRequest(){
		logger.log("About to send a get request to server");
		resp = given().filter(new RequestLoggingFilter(requestCapture)).contentType(ContentType.JSON).headers("Authorization","Bearer "+token).log().body().get();
	    logResponse();
    }
	
	protected void sendDeleteRequest(){
		logger.log("About to send a delete request to server");
        resp = given().filter(new RequestLoggingFilter(requestCapture)).contentType(ContentType.JSON).headers("Authorization","Bearer "+token).log().body().delete();
	     logResponse();
	}
	
	protected void setRelevantEndPoint(String endPoint){
		log("Setting relvant end point for "+endPoint);
	   if(endPoint.equals("products"))
			RestAssured.basePath = 	testProp.getProperty("Product");
			else if(endPoint.equals("basket"))
				RestAssured.basePath = 	testProp.getProperty("Basket");
			else
				log("Unknown endpoint "+endPoint);
		}
	
	protected void validateResponseCode(String action,String responseCode){
	    log("The response code we got is "+responseCode);
	    if(action.equals("get"))
			jsonValidations.validateResults("200",responseCode,true);
        else if(action.equals("add"))
		jsonValidations.validateResults("201",responseCode,true);
	    else if(action.equals("delete")||action.equals("update"))
			jsonValidations.validateResults("200",responseCode,true);
	    else {log("unknown action "+action);}
	}
	
	protected void validateProductJson(Product product,String action){
		
		JsonPath extractor = getJsonPath();
		String message = getJsonMessage(extractor);
	   
	    if(action.equals("add")){
	      jsonValidations.validateResults("Created product successfully",message,true);
          Map<?, ?> responseMap  = extractor.getJsonObject("createdProduct");
	      
	    String name = (String) responseMap.get("name");
	    int price = (Integer) responseMap.get("price");
	    int quantity = (Integer) responseMap.get("quantity");
	   
	    jsonValidations.validateResults(product.getName(),name,false);
	    jsonValidations.validateResults(product.getPrice(),String.valueOf(price),false);
	    jsonValidations.validateResults(product.getQuantity(),String.valueOf(quantity),false);
	    }
	    
	    else if (action.equals("update")){
		    jsonValidations.validateResults("Product updated successfully",message,true); }
	    
	    else if (action.equals("delete")){
		    jsonValidations.validateResults("Product deleted successfully",message,true); }
	}
	
	protected void validateBasketJson(Order order,String action){
		JsonPath extractor = getJsonPath();
		String message = getJsonMessage(extractor);
	    
	    if(action.equals("add")){
	      jsonValidations.validateResults("Product added to basket successfully",message,false);
          Map<?, ?> responseMap  = extractor.getJsonObject("createdBasket");
	      
	    String name = (String) responseMap.get("name");
	    String productId = (String) responseMap.get("product");
	    int quantity = (Integer) responseMap.get("quantity");
	   
	    jsonValidations.validateResults(order.getName(),name,false);
	    jsonValidations.validateResults(order.getProductId(),productId,false);
	    jsonValidations.validateResults(order.getQuantity(),String.valueOf(quantity),false);
	   }
	    else if (action.equals("delete")){
		    jsonValidations.validateResults("Basket emptied successfully",message,true); }
	}
	
	protected void validateGetBasketJson(Order order){
		JsonPath extractor = getJsonPath();
		List<?> responseList  =  extractor.getJsonObject("basket"); 
        Map<?, ?> obj = (Map<?, ?>) responseList.get(0);
	 
         String name = (String) obj.get("name");
	     int quantity =  (Integer) obj.get("quantity");
	
	    Map<?, ?> product = (Map<?, ?>) obj.get("product");
	    String actualProductId = product.get("_id").toString();
	    jsonValidations.validateResults(order.getName(),name,false);
	    jsonValidations.validateResults(order.getProductId(),actualProductId,false);
	    jsonValidations.validateResults(order.getQuantity(),String.valueOf(quantity),false);
	  	}
	
	
	private JsonPath getJsonPath(){
        JsonPath extractor = resp.jsonPath();
        return extractor;
	}

	private String getJsonMessage(JsonPath extractor){
		 return (String)extractor.get("message");
    }
	
	
	 protected void getSessionToken() {
		  JsonPath extractor = resp.jsonPath();
		  token = (String)extractor.get("token");
		  log("Session token value is "+token);
	}

}
