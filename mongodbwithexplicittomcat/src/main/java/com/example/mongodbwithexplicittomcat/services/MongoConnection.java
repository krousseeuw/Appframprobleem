package com.example.mongodbwithexplicittomcat.services;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class MongoConnection {private MongoConnection entity;
    private MongoDatabase database;

    public MongoCollection<Document> getCollection(String collectionName){
        if (entity == null || entity.database == null){
            entity = new MongoConnection();

            String ip = "localhost", user = "plecotus", db = "geolocationexercise", password = "Fl4pflapDatBat";
            MongoCredential credentials = MongoCredential.createCredential(user,db,password.toCharArray());
            MongoClientOptions mongoClientOptions = MongoClientOptions.builder().build();
            MongoClient mongoClient = new MongoClient(new ServerAddress(ip, 27017), Collections.singletonList(credentials), mongoClientOptions);

            entity.database = mongoClient.getDatabase(db);
        }

        return entity.database.getCollection(collectionName);
    }
}
