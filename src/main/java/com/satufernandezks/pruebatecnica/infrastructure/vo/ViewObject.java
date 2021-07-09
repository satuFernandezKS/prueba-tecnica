package com.satufernandezks.pruebatecnica.infrastructure.vo;

public abstract class ViewObject {

    protected int status;

    protected ViewObject(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
