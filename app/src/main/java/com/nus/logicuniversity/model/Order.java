package com.nus.logicuniversity.model;

import java.util.List;

public class Order {

    private int id;

    private String employeeName;

    private String representativeName;

    private String collectionPoint;

    private List<OrderedItem> orderedItems;

    private boolean approved;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<OrderedItem> getOrderedItems() {
        return orderedItems;
    }

    public void setOrderedItems(List<OrderedItem> orderedItems) {
        this.orderedItems = orderedItems;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getRepresentativeName() {
        return representativeName;
    }

    public void setRepresentativeName(String representativeName) {
        this.representativeName = representativeName;
    }

    public String getCollectionPoint() {
        return collectionPoint;
    }

    public void setCollectionPoint(String collectionPoint) {
        this.collectionPoint = collectionPoint;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }
}
