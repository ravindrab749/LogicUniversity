package com.nus.logicuniversity.model;

public class RequisitionDetails {
    private int Quantity;
    private Requisition Requisition;
    private Inventory Item;


    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    public com.nus.logicuniversity.model.Requisition getRequisition() {
        return Requisition;
    }

    public void setRequisition(com.nus.logicuniversity.model.Requisition requisition) {
        Requisition = requisition;
    }

    public Inventory getItem() {
        return Item;
    }

    public void setItem(Inventory item) {
        Item = item;
    }
}
