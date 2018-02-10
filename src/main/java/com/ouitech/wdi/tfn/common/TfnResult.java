package com.ouitech.wdi.tfn.common;

public abstract class TfnResult {

    protected TfnStateEnum status;

    public TfnStateEnum getStatus() {
        return status;
    }

    public void setStatus(TfnStateEnum status) {
        this.status = status;
    }

    public abstract String getState();

}
