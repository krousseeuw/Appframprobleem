package com.example.mongodbwithexplicittomcat.services;


import com.example.mongodbwithexplicittomcat.models.Geo;
import com.example.mongodbwithexplicittomcat.models.LocationPoint;
import com.example.mongodbwithexplicittomcat.models.Park;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.geojson.Point;
import com.mongodb.client.model.geojson.Position;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.json.bind.JsonbBuilder;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

@Service
public class ParkDAO {
    @Autowired
    private MongoConnection mongoConnection;

    @Autowired
    private ParkConverterService parkConverter;

    private final String collectionName = "parken";

    public ArrayList<Park> findAll(){
        ArrayList<Park> parken = new ArrayList<>();
        mongoConnection.getCollection(collectionName).find()
                .iterator()
                .forEachRemaining(document -> {
                    Park p = new Park();
                    p = parkConverter.docToPark(document);
                    parken.add(p);
                });

        return parken;
    }

    public Park insertOne(Park park){
        Document d = parkConverter.parkToDoc(park);
        mongoConnection.getCollection(collectionName).insertOne(d);
        return parkConverter.docToPark(d);
    }

    public void insertFromJson(String json){
        mongoConnection.getCollection(collectionName).drop();

        ArrayList<Document> importedParken = new ArrayList<>();
        JsonbBuilder.create().fromJson(json, ArrayList.class)
                .stream()
                .forEach(h -> {
                    HashMap<String, BigDecimal> geoHash = (HashMap<String, BigDecimal>)((HashMap<String, Object>)h).get("geo");
                    Park p = new Park(
                            (String)((HashMap<String, Object>)h).get("description"),
                            new LocationPoint(geoHash.get("lat").doubleValue(), geoHash.get("lon").doubleValue())
                    );

                    importedParken.add(parkConverter.parkToDoc(p));
                });

        mongoConnection.getCollection(collectionName).insertMany(importedParken);
        Document d = new Document();
        d.append("location", "2dsphere");
        mongoConnection.getCollection(collectionName).createIndex(d);
    }

    public ArrayList<Park> findByName(String name){
        ArrayList<Park> parken = new ArrayList<>();
        mongoConnection.getCollection(collectionName)
                .find(Filters.regex("description", name, "i"))
                .iterator()
                .forEachRemaining(document -> {
                    parken.add(parkConverter.docToPark(document));
                });

        return parken;
    }

    public ArrayList<Park> findNearBy(Geo geo) {
        ArrayList<Park> parkenNearby = new ArrayList<>();
        Point p = new Point(new Position(geo.getLatitude(), geo.getLongitude()));
        mongoConnection.getCollection(collectionName)
                .find(Filters.nearSphere("location", p, 1000.0, 0.0))
                .iterator()
                .forEachRemaining(document -> {
                    parkenNearby.add(parkConverter.docToPark(document));
                });

        return parkenNearby;
    }
}
