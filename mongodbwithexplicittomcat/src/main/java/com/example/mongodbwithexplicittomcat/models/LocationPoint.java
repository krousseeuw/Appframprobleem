package com.example.mongodbwithexplicittomcat.models;

import java.util.ArrayList;

public class LocationPoint {
    public final String type = "Point";
    public ArrayList<Double> coordinates = new ArrayList<>();

    public LocationPoint(){

    }

    public LocationPoint(double latitude, double longitude){
        coordinates.add(latitude);
        coordinates.add(longitude);
    }
}
