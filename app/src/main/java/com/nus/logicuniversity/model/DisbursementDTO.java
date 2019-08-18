package com.nus.logicuniversity.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class DisbursementDTO {

    @SerializedName("ListId")
    private long listId;

    @SerializedName("CollectionPoint")
    private long collectionPoint;

    @SerializedName("Items")
    private ArrayList<PerItem> items;

    public long getListId() {
        return listId;
    }

    public void setListId(long listId) {
        this.listId = listId;
    }

    public long getCollectionPoint() {
        return collectionPoint;
    }

    public void setCollectionPoint(long collectionPoint) {
        this.collectionPoint = collectionPoint;
    }

    public ArrayList<PerItem> getItems() {
        return items;
    }

    public void setItems(ArrayList<PerItem> items) {
        this.items = items;
    }
}
