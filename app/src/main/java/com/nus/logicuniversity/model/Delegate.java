package com.nus.logicuniversity.model;

import java.sql.Timestamp;

public class Delegate {
    private Timestamp FromDate;
    private Timestamp ToDate;
    private Employee Employee;
    private Department Department;

    public Timestamp getFromDate() {
        return FromDate;
    }

    public void setFromDate(Timestamp fromDate) {
        FromDate = fromDate;
    }

    public Timestamp getToDate() {
        return ToDate;
    }

    public void setToDate(Timestamp toDate) {
        ToDate = toDate;
    }

    public com.nus.logicuniversity.model.Employee getEmployee() {
        return Employee;
    }

    public void setEmployee(com.nus.logicuniversity.model.Employee employee) {
        Employee = employee;
    }

    public com.nus.logicuniversity.model.Department getDepartment() {
        return Department;
    }

    public void setDepartment(com.nus.logicuniversity.model.Department department) {
        Department = department;
    }
}
