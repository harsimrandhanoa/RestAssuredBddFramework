package node.database;

import org.bson.Document;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import node.reporter.ReportsLogger;

public class DbConnections {

	protected ReportsLogger logger;
	protected MongoDatabase database;
	protected MongoCollection<Document> products;
	protected MongoCollection<Document> baskets;

	public DbConnections(ReportsLogger logger, MongoDatabase database) {
		this.logger = logger;
		this.database = database;
		products = database.getCollection("products");
		baskets = database.getCollection("baskets");
	}

}
