package com.example.mongodbwithexplicittomcat.models;

public class Park {
    private String _id;
    private String description;
    private LocationPoint location;

    public Park() {
    }

    public Park(String description, LocationPoint location) {
        this.description = description;
        this.location = location;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocationPoint getLocation() {
        return location;
    }

    public void setLocation(LocationPoint location) {
        this.location = location;
    }
}
