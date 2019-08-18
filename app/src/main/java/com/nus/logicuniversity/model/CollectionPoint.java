package com.nus.logicuniversity.model;

import com.google.gson.annotations.SerializedName;

public class CollectionPoint {

    @SerializedName("PlacedId")
    private long placedId;

    @SerializedName("Name")
    private String name;

    @SerializedName("Time")
    private Object time;

    public long getPlacedId() {
        return placedId;
    }

    public void setPlacedId(long placedId) {
        this.placedId = placedId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getTime() {
        return time;
    }

    public void setTime(Object time) {
        this.time = time;
    }
}
