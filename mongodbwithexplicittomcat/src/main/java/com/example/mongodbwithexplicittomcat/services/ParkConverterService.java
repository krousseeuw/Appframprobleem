package com.example.mongodbwithexplicittomcat.services;

import com.example.mongodbwithexplicittomcat.models.Park;
import org.bson.Document;
import org.springframework.stereotype.Service;

import javax.json.bind.JsonbBuilder;

@Service
public class ParkConverterService {
    public Park docToPark(Document document){
        Park newPark = JsonbBuilder.create().fromJson(document.toJson(), Park.class);
        newPark.set_id(document.getObjectId("_id").toString());
        return newPark;
    }

    public Document parkToDoc(Park park){
        return Document.parse(JsonbBuilder.create().toJson(park));
    }
}
