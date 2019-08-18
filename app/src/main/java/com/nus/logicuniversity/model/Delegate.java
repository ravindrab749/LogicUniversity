package com.nus.logicuniversity.model;

import com.google.gson.annotations.SerializedName;

import java.sql.Timestamp;
import java.util.Date;

public class Delegate {

    @SerializedName("FromDate")
    private Date fromDate;

    @SerializedName("ToDate")
    private Date toDate;

    @SerializedName("Employee")
    private Employee employee;

    @SerializedName("Department")
    private Department department;

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
}
