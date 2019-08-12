package com.nus.logicuniversity.model;

import java.util.ArrayList;

public class DisbursementsResponse {

    private ArrayList<DisbursementItem> disbursementList;

    public ArrayList<DisbursementItem> getDisbursementList() {
        return disbursementList;
    }

    public void setDisbursementList(ArrayList<DisbursementItem> disbursementList) {
        this.disbursementList = disbursementList;
    }
}
