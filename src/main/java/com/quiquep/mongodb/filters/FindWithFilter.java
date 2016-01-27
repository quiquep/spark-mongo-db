package com.quiquep.mongodb.filters;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

public class FindWithFilter {
	public static void main(String[] args) {
		MongoClient client = new MongoClient();
		MongoDatabase database = client.getDatabase("course");
		MongoCollection<Document> collection = database.getCollection("findWithFilterTest");
		
		collection.drop();
		
		for (int i = 0; i < 10; i++) {
			collection.insertOne(new Document()
								 .append("x", new Random().nextInt(2))
								 .append("y", new Random().nextInt(100)));
		}
		
		Bson filter1 = new Document("x" , 0);
		
		Bson filter2 = Filters.eq("x", 0);
		
		Bson projection = new Document("y", 1)
								.append("i", 1)
								.append("_id", 0);
		
		List<Document> all = collection.find(filter2)
				                       .projection(projection)
									   .into(new ArrayList<Document>());
		
		for(Document cur : all) {
			System.out.println(cur.toJson());
		}
	}
}
