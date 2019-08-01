package com.nus.logicuniversity.model;

public class Inventory {
    private long ItemId;
    private String ItemCode;
    private String BinNo;
    private int StockLevel;
    private int ReorderLevel;
    private int ReorderQty;
    private String Category;
    private String Description;
    private String UnitOfMeasure;
    private String ImageUrl;

    public long getItemId() {
        return ItemId;
    }

    public void setItemId(long itemId) {
        ItemId = itemId;
    }

    public String getItemCode() {
        return ItemCode;
    }

    public void setItemCode(String itemCode) {
        ItemCode = itemCode;
    }

    public String getBinNo() {
        return BinNo;
    }

    public void setBinNo(String binNo) {
        BinNo = binNo;
    }

    public int getStockLevel() {
        return StockLevel;
    }

    public void setStockLevel(int stockLevel) {
        StockLevel = stockLevel;
    }

    public int getReorderLevel() {
        return ReorderLevel;
    }

    public void setReorderLevel(int reorderLevel) {
        ReorderLevel = reorderLevel;
    }

    public int getReorderQty() {
        return ReorderQty;
    }

    public void setReorderQty(int reorderQty) {
        ReorderQty = reorderQty;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getUnitOfMeasure() {
        return UnitOfMeasure;
    }

    public void setUnitOfMeasure(String unitOfMeasure) {
        UnitOfMeasure = unitOfMeasure;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }
}
