package com.nus.logicuniversity.model;

import com.google.gson.annotations.SerializedName;

public class DisbursementDetailItem {

    private long deptId;

    private long itemId;

    @SerializedName("Quantity")
    private int quantity;

    @SerializedName("DisbursementList")
    private DisbursementItem disbursementItem;

    @SerializedName("Item")
    private Inventory item;

    public long getDeptId() {
        return deptId;
    }

    public void setDeptId(long deptId) {
        this.deptId = deptId;
    }

    public long getItemId() {
        return itemId;
    }

    public void setItemId(long itemId) {
        this.itemId = itemId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public DisbursementItem getDisbursementItem() {
        return disbursementItem;
    }

    public void setDisbursementItem(DisbursementItem disbursementItem) {
        this.disbursementItem = disbursementItem;
    }

    public Inventory getItem() {
        return item;
    }

    public void setItem(Inventory item) {
        this.item = item;
    }
}
