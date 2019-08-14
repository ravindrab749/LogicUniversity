package com.nus.logicuniversity.model;

import com.google.gson.annotations.SerializedName;

public class PriceList {

    @SerializedName("Item")
    private Inventory item;
    @SerializedName("Supplier1Id")
    private long supplier1Id;
    @SerializedName("Supplier2Id")
    private long supplier2Id;
    @SerializedName("Supplier3Id")
    private long supplier3Id;

    @SerializedName("Supplier1Name")
    private String supplier1Name;

    @SerializedName("Supplier2Name")
    private String supplier2Name;

    @SerializedName("Supplier3Name")
    private String supplier3Name;

    @SerializedName("Supplier1UnitPrice")
    private double supplier1UnitPrice;

    @SerializedName("Supplier2UnitPrice")
    private double supplier2UnitPrice;

    @SerializedName("Supplier3UnitPrice")
    private double supplier3UnitPrice;

    @SerializedName("Supplier1Code")
    private String supplier1Code;
    @SerializedName("Supplier2Code")
    private String supplier2Code;
    @SerializedName("Supplier3Code")
    private String supplier3Code;

    public Inventory getItem() {
        return item;
    }

    public void setItem(Inventory item) {
        this.item = item;
    }

    public long getSupplier1Id() {
        return supplier1Id;
    }

    public void setSupplier1Id(long supplier1Id) {
        this.supplier1Id = supplier1Id;
    }

    public long getSupplier2Id() {
        return supplier2Id;
    }

    public void setSupplier2Id(long supplier2Id) {
        this.supplier2Id = supplier2Id;
    }

    public long getSupplier3Id() {
        return supplier3Id;
    }

    public void setSupplier3Id(long supplier3Id) {
        this.supplier3Id = supplier3Id;
    }

    public String getSupplier1Name() {
        return supplier1Name;
    }

    public void setSupplier1Name(String supplier1Name) {
        this.supplier1Name = supplier1Name;
    }

    public String getSupplier2Name() {
        return supplier2Name;
    }

    public void setSupplier2Name(String supplier2Name) {
        this.supplier2Name = supplier2Name;
    }

    public String getSupplier3Name() {
        return supplier3Name;
    }

    public void setSupplier3Name(String supplier3Name) {
        this.supplier3Name = supplier3Name;
    }

    public double getSupplier1UnitPrice() {
        return supplier1UnitPrice;
    }

    public void setSupplier1UnitPrice(double supplier1UnitPrice) {
        this.supplier1UnitPrice = supplier1UnitPrice;
    }

    public double getSupplier2UnitPrice() {
        return supplier2UnitPrice;
    }

    public void setSupplier2UnitPrice(double supplier2UnitPrice) {
        this.supplier2UnitPrice = supplier2UnitPrice;
    }

    public double getSupplier3UnitPrice() {
        return supplier3UnitPrice;
    }

    public void setSupplier3UnitPrice(double supplier3UnitPrice) {
        this.supplier3UnitPrice = supplier3UnitPrice;
    }

    public String getSupplier1Code() {
        return supplier1Code;
    }

    public void setSupplier1Code(String supplier1Code) {
        this.supplier1Code = supplier1Code;
    }

    public String getSupplier2Code() {
        return supplier2Code;
    }

    public void setSupplier2Code(String supplier2Code) {
        this.supplier2Code = supplier2Code;
    }

    public String getSupplier3Code() {
        return supplier3Code;
    }

    public void setSupplier3Code(String supplier3Code) {
        this.supplier3Code = supplier3Code;
    }
}
