package node.database;

import java.util.Iterator;
import org.bson.Document;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

import node.base.Utils;
import node.base.Order;
import node.base.Product;
import node.reporter.ReportsLogger;

public class DbValidations extends DbConnections {

	protected Utils utils;

	public DbValidations(ReportsLogger logger, Utils utils, MongoDatabase database) {
		super(logger, database);
		this.utils = utils;
	}

	public void isDocumentCreated(String documentName, String collectionName, Boolean hardAssert) {
		logger.log("Checking if " + collectionName + " is present in db");
		int count = 0;
		if (collectionName.equals("products"))
			count = getCollectionCount(documentName, products);
		else if (collectionName.equals("orders"))
			count = getCollectionCount(documentName, baskets);
		logger.log("The count for ducument named "+documentName +" in collection named " + collectionName + " is " + count);
		utils.isEquals(1, count, hardAssert);
	}

	public void validateDocumentFields(Object obj, Boolean hardAssert) {
         if(obj==null){
				utils.failTest("Test failed as obj passed to be checked is null");
		 }
	    else if (obj instanceof Product) {
			Product productObj = (Product) obj;
			Document product = products.find(new Document("name", productObj.getName())).first();
			if(product==null)
				utils.failTest("Test failed as cannot find product in db");
			validateResults(productObj.getName(), product.get("name").toString(), true);
			validateResults(productObj.getPrice(), product.get("price").toString(), false);
			validateResults(productObj.getQuantity(), product.get("quantity").toString(), false);
		}
		else if (obj instanceof Order) {
			Order orderObj = (Order) obj;
			Document order = baskets.find(new Document("name", orderObj.getName())).first();
			if(order==null)
				utils.failTest("Test failed as cannot find order in db");
			validateResults(orderObj.getName(), order.get("name").toString(), true);
			validateResults(orderObj.getQuantity(), order.get("quantity").toString(), false);
			validateResults(orderObj.getProductId(), order.get("product").toString(), false);
		}
		else 
			utils.failTest("Validating product or order in db failed due to unknown reasons");
       }

	public void validateResults(String expectedValue, String actualValue, boolean hardAssert) {
		utils.isEqual(expectedValue, actualValue, hardAssert);
	}

	public void isDocumentDeleted(String documentName, String collectionName, Boolean hardAssert) {
		logger.log("Checking if " + collectionName + " is deleted");
        int count = 0;
		if (collectionName.equals("products"))
			count = getCollectionCount(documentName, products);
		else if (collectionName.equals("orders"))
			count = getCollectionCount(documentName, baskets);
		logger.log("The count for ducument named "+documentName +" in collection named " + collectionName + " is " + count);
        utils.isEquals(0, count, hardAssert);
	}

	private int getCollectionCount(String name, MongoCollection<Document> collection) {
		FindIterable<Document> findIterable = collection.find(Filters.eq("name", name));
		Iterator<Document> iterator = findIterable.iterator();
		int count = 0;
		while (iterator.hasNext()) {
			iterator.next();
			count++;
		}
		return count;
	}

}
