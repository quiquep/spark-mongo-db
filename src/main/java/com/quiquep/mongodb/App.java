package com.quiquep.mongodb;

import static spark.Spark.get;

import java.io.StringWriter;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import freemarker.template.Configuration;
import freemarker.template.Template;


public class App 
{
    public static void main( String[] args )
    {
    	final Configuration configuration = new Configuration();
    	configuration.setClassForTemplateLoading(App.class, "/");
    	
		MongoClient client = new MongoClient();
		MongoDatabase database = client.getDatabase("course");
		final MongoCollection<Document> collection = database.getCollection("hello");
		
		collection.drop();
		
		collection.insertOne(new Document("name","MongoDB"));
    	
    	get("/hello", (req, res) -> {
    		StringWriter writer = new StringWriter();
    		Template tpl = configuration.getTemplate("hello.ftl");
    		
    		Document document = collection.find().first();
    		
    		tpl.process(document, writer);
    		
    		return writer;
    	});
    }
}
