package com.example.yiliaoyinian.Beans;

public class Auto3 {


    /**
     * access_token : bbef520c8a8336572f7f1953b14814dc
     * refresh_token : c217d142789c197c5e82a1f4d31edfaf
     * openId : 324309574428744952573058379777
     * state : aiot
     * token_type : bearer
     * expires_in : 86400
     */
    private String access_token;
    private String refresh_token;
    private String openId;
    private String state;
    private String token_type;
    private int expires_in;

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    public void setExpires_in(int expires_in) {
        this.expires_in = expires_in;
    }

    public String getAccess_token() {
        return access_token;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public String getOpenId() {
        return openId;
    }

    public String getState() {
        return state;
    }

    public String getToken_type() {
        return token_type;
    }

    public int getExpires_in() {
        return expires_in;
    }
}
