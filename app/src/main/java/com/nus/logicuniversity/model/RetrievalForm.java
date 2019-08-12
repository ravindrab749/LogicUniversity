package com.nus.logicuniversity.model;

import java.util.ArrayList;

public class RetrievalForm {

    private String binNo;

    private long itemId;

    private String description;

    private int totalNeeded;

    private int totalRetrieved;

    private ArrayList<DeptNeeded> deptNeeds;

    public String getBinNo() {
        return binNo;
    }

    public void setBinNo(String binNo) {
        this.binNo = binNo;
    }

    public long getItemId() {
        return itemId;
    }

    public void setItemId(long itemId) {
        this.itemId = itemId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getTotalNeeded() {
        return totalNeeded;
    }

    public void setTotalNeeded(int totalNeeded) {
        this.totalNeeded = totalNeeded;
    }

    public int getTotalRetrieved() {
        return totalRetrieved;
    }

    public void setTotalRetrieved(int totalRetrieved) {
        this.totalRetrieved = totalRetrieved;
    }

    public ArrayList<DeptNeeded> getDeptNeeds() {
        return deptNeeds;
    }

    public void setDeptNeeds(ArrayList<DeptNeeded> deptNeeds) {
        this.deptNeeds = deptNeeds;
    }
}
