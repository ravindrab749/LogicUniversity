package com.nus.logicuniversity.model;

public class DelegatedInfoResponse {

    private boolean auth;
    private boolean delegated;
    private Delegate userInfo;

    public boolean isAuth() {
        return auth;
    }

    public void setAuth(boolean auth) {
        this.auth = auth;
    }

    public boolean isDelegated() {
        return delegated;
    }

    public void setDelegated(boolean delegated) {
        this.delegated = delegated;
    }

    public Delegate getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(Delegate userInfo) {
        this.userInfo = userInfo;
    }
}
