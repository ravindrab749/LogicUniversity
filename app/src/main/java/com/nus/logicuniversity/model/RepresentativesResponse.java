package com.nus.logicuniversity.model;

import java.util.ArrayList;

public class RepresentativesResponse {

    private ArrayList<Employee> repList;

    private Employee curRep;

    public ArrayList<Employee> getRepList() {
        return repList;
    }

    public void setRepList(ArrayList<Employee> repList) {
        this.repList = repList;
    }

    public Employee getCurRep() {
        return curRep;
    }

    public void setCurRep(Employee curRep) {
        this.curRep = curRep;
    }
}
