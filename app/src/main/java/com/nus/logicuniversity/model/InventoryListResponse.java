package com.nus.logicuniversity.model;

import java.util.ArrayList;

public class InventoryListResponse {

    private ArrayList<Inventory> inventories;

    public ArrayList<Inventory> getInventories() {
        return inventories;
    }

    public void setInventories(ArrayList<Inventory> inventories) {
        this.inventories = inventories;
    }
}
