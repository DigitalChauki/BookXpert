package com.example.bookxpert;

public class BookXpertModelData {
    String actId ;
    String actName;
    String actAmount;

    public BookXpertModelData(String actId, String actName, String actAmount) {
        this.actId = actId;
        this.actName = actName;
        this.actAmount = actAmount;
    }

    public String getActId() {
        return actId;
    }

    public void setActId(String actId) {
        this.actId = actId;
    }

    public String getActName() {
        return actName;
    }

    public void setActName(String actName) {
        this.actName = actName;
    }

    public String getActAmount() {
        return actAmount;
    }

    public void setActAmount(String actAmount) {
        this.actAmount = actAmount;
    }
}
