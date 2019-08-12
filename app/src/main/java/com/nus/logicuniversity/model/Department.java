package com.nus.logicuniversity.model;

import com.google.gson.annotations.SerializedName;

public class Department {

    @SerializedName("DeptId")
    private long deptId;

    @SerializedName("DeptCode")
    private String deptCode;

    @SerializedName("Name")
    private String name;

    @SerializedName("Contact")
    private String contact;

    @SerializedName("Telephone")
    private String telephone;

    @SerializedName("Fax")
    private String fax;

    @SerializedName("Head")
    private String head;

    @SerializedName("Representative")
    private Employee representative;

    public long getDeptId() {
        return deptId;
    }

    public void setDeptId(long deptId) {
        this.deptId = deptId;
    }

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public Employee getRepresentative() {
        return representative;
    }

    public void setRepresentative(Employee representative) {
        this.representative = representative;
    }
}
