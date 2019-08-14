package com.nus.logicuniversity.model;

import java.util.ArrayList;

public class RetrievalResponse {

    private ArrayList<RetrievalForm> retrievalForms;

    private boolean pending;

    public ArrayList<RetrievalForm> getRetrievalForms() {
        return retrievalForms;
    }

    public void setRetrievalForms(ArrayList<RetrievalForm> retrievalForms) {
        this.retrievalForms = retrievalForms;
    }

    public boolean isPending() {
        return pending;
    }

    public void setPending(boolean pending) {
        this.pending = pending;
    }
}
