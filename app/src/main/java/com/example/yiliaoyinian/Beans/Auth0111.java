package com.example.yiliaoyinian.Beans;

public class Auth0111 {


    /**
     * linkageId : L.775802579423645696
     * updateTime : 1605007348742
     * createTime : 1605007348660
     * state : 1
     * localize : 1
     */

    private String linkageId;
    private long updateTime;
    private long createTime;
    private int state;
    private int localize;
    private String name;
    private String did;
    private String id;//司机后台id

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDid() {
        return did;
    }

    public void setDid(String did) {
        this.did = did;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLinkageId() {
        return linkageId;
    }

    public void setLinkageId(String linkageId) {
        this.linkageId = linkageId;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getLocalize() {
        return localize;
    }

    public void setLocalize(int localize) {
        this.localize = localize;
    }
}
