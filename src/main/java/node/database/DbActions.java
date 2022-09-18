package node.database;

import org.bson.Document;
import org.bson.types.ObjectId;
import com.mongodb.client.MongoDatabase;

import node.base.Order;
import node.base.Product;
import node.base.Utils;
import node.reporter.ReportsLogger;

public class DbActions extends DbConnections {
	
	protected Utils utils;
	

	public DbActions(ReportsLogger logger,Utils utils, MongoDatabase database) {
		super(logger, database);
		this.utils = utils;
     }

	public String createProduct(Product productObj) {
		logger.log("About to create a new product in db with fields - " + productObj.toString());
		Document newProduct = new Document("name", productObj.getName()).append("price", productObj.getPrice()).append("quantity",productObj.getQuantity());
		ObjectId id = products.insertOne(newProduct).getInsertedId().asObjectId().getValue();
		if(id==null)
		utils.failTest("Failed to create new product "+productObj.toString()+ " in db");
		logger.log("The product is created in db and id is " + id);
		return id.toString();
	}

	public String createOrder(Order orderObj) {
		logger.log("About to create a new order in db with fields - " + orderObj.toString());
        Document newOrder = new Document("name", orderObj.getName()).append("product", new ObjectId(orderObj.getProductId())).append("quantity", orderObj.getQuantity());
		ObjectId id = baskets.insertOne(newOrder).getInsertedId().asObjectId().getValue();
		if(id==null)
        utils.failTest("Failed to create new order "+orderObj.toString()+ " in db");
		logger.log("The order is created in db and id is " + id);
		return id.toString();
   }

	public String getProductId(String productName) {
		Document product = products.find(new Document("name", productName)).first();
		if(product==null)
			  return null ;
		else
			return product.get("_id").toString();
   }
}
