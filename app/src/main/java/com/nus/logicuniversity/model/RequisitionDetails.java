package com.nus.logicuniversity.model;

import com.google.gson.annotations.SerializedName;

public class RequisitionDetails {

    @SerializedName("Quantity")
    private int quantity;

    @SerializedName("Requisition")
    private Requisition requisition;

    @SerializedName("Item")
    private Inventory item;

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Requisition getRequisition() {
        return requisition;
    }

    public void setRequisition(Requisition requisition) {
        this.requisition = requisition;
    }

    public Inventory getItem() {
        return item;
    }

    public void setItem(Inventory item) {
        this.item = item;
    }
}
