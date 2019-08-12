package com.nus.logicuniversity.model;

import java.util.ArrayList;

public class RequisitionDetailResponse {

    private ArrayList<RequisitionDetails> reqDataList;

    private long reqId;

    public ArrayList<RequisitionDetails> getReqDataList() {
        return reqDataList;
    }

    public void setReqDataList(ArrayList<RequisitionDetails> reqDataList) {
        this.reqDataList = reqDataList;
    }

    public long getReqId() {
        return reqId;
    }

    public void setReqId(long reqId) {
        this.reqId = reqId;
    }
}
