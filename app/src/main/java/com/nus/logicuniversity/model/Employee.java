package com.nus.logicuniversity.model;

import com.google.gson.annotations.SerializedName;

public class Employee {

    @SerializedName("EmpId")
    private long empId;

    @SerializedName("DeptId")
    private long deptId;

    @SerializedName("EmpName")
    private String empName;

    @SerializedName("EmpRole")
    private String empRole;

    @SerializedName("EmpDisplayRole")
    private String empDisplayRole;

    @SerializedName("UserName")
    private String userName;

    @SerializedName("Password")
    private String password;

    @SerializedName("Email")
    private String email;

    @SerializedName("Department")
    private Department department;

    public long getEmpId() {
        return empId;
    }

    public void setEmpId(long empId) {
        this.empId = empId;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getEmpRole() {
        return empRole;
    }

    public void setEmpRole(String empRole) {
        this.empRole = empRole;
    }

    public String getEmpDisplayRole() {
        return empDisplayRole;
    }

    public void setEmpDisplayRole(String empDisplayRole) {
        this.empDisplayRole = empDisplayRole;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
}
