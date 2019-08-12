package com.nus.logicuniversity.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Date;

public class DisbursementItem {

    @SerializedName("ListId")
    private long listId;

    @SerializedName("AcknowledgedBy")
    private String acknowledgedBy;

    private Date date;

    @SerializedName("Department")
    private Department department;

    @SerializedName("CollectionPoint")
    private CollectionPoint collectionPoint;

    @SerializedName("DisbursementListDetails")
    private ArrayList<DisbursementDetailItem> disbursementDetails;

    public long getListId() {
        return listId;
    }

    public void setListId(long listId) {
        this.listId = listId;
    }

    public String getAcknowledgedBy() {
        return acknowledgedBy;
    }

    public void setAcknowledgedBy(String acknowledgedBy) {
        this.acknowledgedBy = acknowledgedBy;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public CollectionPoint getCollectionPoint() {
        return collectionPoint;
    }

    public void setCollectionPoint(CollectionPoint collectionPoint) {
        this.collectionPoint = collectionPoint;
    }

    public ArrayList<DisbursementDetailItem> getDisbursementDetails() {
        return disbursementDetails;
    }

    public void setDisbursementDetails(ArrayList<DisbursementDetailItem> disbursementDetails) {
        this.disbursementDetails = disbursementDetails;
    }
}
