package com.nus.logicuniversity.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class AdjustmentVoucher {

    @SerializedName("AdjId")
    private long adjId;
    @SerializedName("Date")
    private Date date;
    @SerializedName("AuthorisedBy")
    private String authorisedBy;
    @SerializedName("AdjQty")
    private int adjQty;
    @SerializedName("Reason")
    private String reason;
    @SerializedName("Item")
    private Inventory item;

    @SerializedName("ItemId")
    private long itemId;

    @SerializedName("ItemCode")
    private long itemCode;

    @SerializedName("TotalPrice")
    private double totalPrice;

    //0-pending 1-approved 2-rejected
    private int status;

    public long getAdjId() {
        return adjId;
    }

    public void setAdjId(long adjId) {
        this.adjId = adjId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getAuthorisedBy() {
        return authorisedBy;
    }

    public void setAuthorisedBy(String authorisedBy) {
        this.authorisedBy = authorisedBy;
    }

    public int getAdjQty() {
        return adjQty;
    }

    public void setAdjQty(int adjQty) {
        this.adjQty = adjQty;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Inventory getItem() {
        return item;
    }

    public void setItem(Inventory item) {
        this.item = item;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getItemId() {
        return itemId;
    }

    public void setItemId(long itemId) {
        this.itemId = itemId;
    }

    public long getItemCode() {
        return itemCode;
    }

    public void setItemCode(long itemCode) {
        this.itemCode = itemCode;
    }
}
