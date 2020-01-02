package com.example.mongodbwithexplicittomcat.models;

import java.util.ArrayList;

public class LocationPoint {
    public final String type = "Point";
    public ArrayList<Double> coordinates = new ArrayList<>();

    public LocationPoint(){

    }

    public LocationPoint(Double latitude, Double longitude){
        coordinates.add(latitude);
        coordinates.add(longitude);
    }
}
