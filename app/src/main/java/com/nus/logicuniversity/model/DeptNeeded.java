package com.nus.logicuniversity.model;

public class DeptNeeded {

    private long deptId;

    private String deptCode;

    private int deptNeeded;

    private int deptActual;

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

    public int getDeptNeeded() {
        return deptNeeded;
    }

    public void setDeptNeeded(int deptNeeded) {
        this.deptNeeded = deptNeeded;
    }

    public int getDeptActual() {
        return deptActual;
    }

    public void setDeptActual(int deptActual) {
        this.deptActual = deptActual;
    }
}
