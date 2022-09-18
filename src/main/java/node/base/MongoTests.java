package node.base;

import java.util.Iterator;

import org.bson.Document;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

public class MongoTests {

	public static void main(String[] args) {
		MongoClient client = MongoClients.create("mongodb://localhost:27017");
		
		System.out.println("connected to local mongo db");
		
		MongoDatabase database = client.getDatabase("RestShop");
		MongoCollection<Document> products = database.getCollection("products");
		
		 System.out.println(products.toString());
		 
		 

		
//		 Document product = products.find(new Document("name","Table")).first(); 
//		 System.out.println(product.toJson());
		 
//		 MongoCollection<Document> product = database
//					.getCollection("employee_records");
		 
		 Document product = products.find(new Document("name","Table30")).first(); 
		 System.out.println("count is "+product.get("name"));
		 System.out.println("count is "+product.get("quantity"));
		 System.out.println("count is "+product.get("price"));
		 
		 Document query = new Document("_id", new Document("$lt", 100));
		// long count = products.count(query);
		 
		 FindIterable findIterable = 
				 products.find(Filters.eq("name","Table3sd"));
				    Iterator iterator = findIterable.iterator();
				    int count = 0;
				    while (iterator.hasNext()) {
				        iterator.next();
				        count++;
				    }
				    System.out.println(">>>>> count = " + count);
		 
			System.out.println("Total Document Count is : "+product.size());

		 
		MongoCollection<Document> orders = database.getCollection("baskets");
		System.out.println(orders.toString());
          

		 
		 Document basket = orders.find(new Document("name","NewBasket1233")).first(); 
		 System.out.println("count is "+basket.get("name"));
		 System.out.println("count is "+basket.get("quantity"));
		 System.out.println("count is "+basket.get("product"));



		
		 
		 String productName = "Table30";
		 String price = "30";
		 String quantity = "20";

		 
		 Document newProduct = new Document("name",productName).append("price",price).append("quantity",quantity);
		 
		// ObjectId id = products.insertOne(newProduct).getInsertedId().asObjectId().getValue();
		 
		// System.out.println(id);


		// TODO Auto-generated method stub

	}

}
