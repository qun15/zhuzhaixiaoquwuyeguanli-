package com.nb.crm.settings.domain;

public class Park {
    private String id;
    private String userId;
    private String chePai;
    private String cheWei;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Park{" +
                "id='" + id + '\'' +
                ", userId='" + userId + '\'' +
                ", chePai='" + chePai + '\'' +
                ", cheWei='" + cheWei + '\'' +
                '}';
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getChePai() {
        return chePai;
    }

    public void setChePai(String chePai) {
        this.chePai = chePai;
    }

    public String getCheWei() {
        return cheWei;
    }

    public void setCheWei(String cheWei) {
        this.cheWei = cheWei;
    }
}
