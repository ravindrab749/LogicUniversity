package com.nus.logicuniversity.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Requisition {

    @SerializedName("ReqId")
    private long reqId;

    @SerializedName("ReqCode")
    private String reqCode;

    @SerializedName("DateOfRequest")
    private Date dateOfRequest;

    @SerializedName("Status")
    private String status;

    @SerializedName("PickUpDate")
    private Date pickUpDate;

    @SerializedName("ApprovedBy")
    private String approvedBy;

    @SerializedName("Employee")
    private Employee employee;

    public long getReqId() {
        return reqId;
    }

    public void setReqId(long reqId) {
        this.reqId = reqId;
    }

    public String getReqCode() {
        return reqCode;
    }

    public void setReqCode(String reqCode) {
        this.reqCode = reqCode;
    }

    public Date getDateOfRequest() {
        return dateOfRequest;
    }

    public void setDateOfRequest(Date dateOfRequest) {
        this.dateOfRequest = dateOfRequest;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getPickUpDate() {
        return pickUpDate;
    }

    public void setPickUpDate(Date pickUpDate) {
        this.pickUpDate = pickUpDate;
    }

    public String getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(String approvedBy) {
        this.approvedBy = approvedBy;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}
