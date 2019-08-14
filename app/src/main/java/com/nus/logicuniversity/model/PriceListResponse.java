package com.nus.logicuniversity.model;

import java.util.ArrayList;

public class PriceListResponse {

    private ArrayList<PriceList> priceLists;

    public ArrayList<PriceList> getPriceLists() {
        return priceLists;
    }

    public void setPriceLists(ArrayList<PriceList> priceLists) {
        this.priceLists = priceLists;
    }
}
