package com.nb.crm.settings.domain;

public class Pay {
    private String id;
    private String userId;
    private String waterElr;
    private String parkPay;

    public Pay(String id, String userId, String waterElr, String parkPay) {
        this.id = id;
        this.userId = userId;
        this.waterElr = waterElr;
        this.parkPay = parkPay;
    }

    public Pay() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getWaterElr() {
        return waterElr;
    }

    public void setWaterElr(String waterElr) {
        this.waterElr = waterElr;
    }

    public String getParkPay() {
        return parkPay;
    }

    public void setParkPay(String parkPay) {
        this.parkPay = parkPay;
    }
}
