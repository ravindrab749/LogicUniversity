package com.nus.logicuniversity.model;

public class Department {
    private long DeptId ;
    private String DeptCode ;
    private String Name ;
    private String Contact ;
    private String Telephone ;
    private String Fax ;
    private String Head ;
    private Employee Representative ;

    public long getDeptId() {
        return DeptId;
    }

    public void setDeptId(long deptId) {
        DeptId = deptId;
    }

    public String getDeptCode() {
        return DeptCode;
    }

    public void setDeptCode(String deptCode) {
        DeptCode = deptCode;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getContact() {
        return Contact;
    }

    public void setContact(String contact) {
        Contact = contact;
    }

    public String getTelephone() {
        return Telephone;
    }

    public void setTelephone(String telephone) {
        Telephone = telephone;
    }

    public String getFax() {
        return Fax;
    }

    public void setFax(String fax) {
        Fax = fax;
    }

    public String getHead() {
        return Head;
    }

    public void setHead(String head) {
        Head = head;
    }

    public Employee getRepresentative() {
        return Representative;
    }

    public void setRepresentative(Employee representative) {
        Representative = representative;
    }
//    private CollectionPoint CollectionPoint ;
}
