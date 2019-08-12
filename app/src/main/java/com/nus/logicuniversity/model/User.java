package com.nus.logicuniversity.model;

public class User {

    private String username;
    private String password;
    private String role;
    private String displayRole;
    private boolean enrolledFingerprint;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnrolledFingerprint() {
        return enrolledFingerprint;
    }

    public void setEnrolledFingerprint(boolean enrolledFingerprint) {
        this.enrolledFingerprint = enrolledFingerprint;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getDisplayRole() {
        return displayRole;
    }

    public void setDisplayRole(String displayRole) {
        this.displayRole = displayRole;
    }
}
