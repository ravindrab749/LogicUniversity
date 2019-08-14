package com.nus.logicuniversity.model;

import java.util.ArrayList;

public class DisbursementDetailsResponse {

    private ArrayList<DisbursementDetailItem> disbursementDetails;

    public ArrayList<DisbursementDetailItem> getDisbursementDetails() {
        return disbursementDetails;
    }

    public void setDisbursementDetails(ArrayList<DisbursementDetailItem> disbursementDetails) {
        this.disbursementDetails = disbursementDetails;
    }
}
