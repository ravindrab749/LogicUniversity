package com.nus.logicuniversity.model;

import java.sql.Timestamp;

public class Requisition {
    private long ReqId;
    private String ReqCode;
    private Timestamp DateOfRequest;
    private String Status;
    private Timestamp PickUpDate;
    private String ApprovedBy;
    private Employee Employee;

    public long getReqId() {
        return ReqId;
    }

    public void setReqId(long reqId) {
        ReqId = reqId;
    }

    public String getReqCode() {
        return ReqCode;
    }

    public void setReqCode(String reqCode) {
        ReqCode = reqCode;
    }

    public Timestamp getDateOfRequest() {
        return DateOfRequest;
    }

    public void setDateOfRequest(Timestamp dateOfRequest) {
        DateOfRequest = dateOfRequest;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public Timestamp getPickUpDate() {
        return PickUpDate;
    }

    public void setPickUpDate(Timestamp pickUpDate) {
        PickUpDate = pickUpDate;
    }

    public String getApprovedBy() {
        return ApprovedBy;
    }

    public void setApprovedBy(String approvedBy) {
        ApprovedBy = approvedBy;
    }

    public com.nus.logicuniversity.model.Employee getEmployee() {
        return Employee;
    }

    public void setEmployee(com.nus.logicuniversity.model.Employee employee) {
        Employee = employee;
    }
}
