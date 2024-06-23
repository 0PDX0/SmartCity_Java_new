package com.example.zhcs.bean;

import java.io.Serializable;

public class SubBean implements Serializable {

    private int lineId;
    private String lineName;

    public int getLineId() {
        return lineId;
    }

    public void setLineId(int lineId) {
        this.lineId = lineId;
    }

    public String getLineName() {
        return lineName;
    }

    public void setLineName(String lineName) {
        this.lineName = lineName;
    }
}
