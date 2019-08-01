package com.nus.logicuniversity.model;

public class Employee {
    private long EmpId;
    private String EmpName;
    private String EmpRole;
    private String EmpDisplayRole;
    private String UserName;
    private String Password;
    private Department Department;


    public long getEmpId() {
        return EmpId;
    }

    public void setEmpId(long empId) {
        EmpId = empId;
    }

    public String getEmpName() {
        return EmpName;
    }

    public void setEmpName(String empName) {
        EmpName = empName;
    }

    public String getEmpRole() {
        return EmpRole;
    }

    public void setEmpRole(String empRole) {
        EmpRole = empRole;
    }

    public String getEmpDisplayRole() {
        return EmpDisplayRole;
    }

    public void setEmpDisplayRole(String empDisplayRole) {
        EmpDisplayRole = empDisplayRole;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public com.nus.logicuniversity.model.Department getDepartment() {
        return Department;
    }

    public void setDepartment(com.nus.logicuniversity.model.Department department) {
        Department = department;
    }
}
