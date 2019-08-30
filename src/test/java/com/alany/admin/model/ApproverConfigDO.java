package com.alany.admin.model;

import java.util.Date;

public class ApproverConfigDO {
    private Integer id;

    private Short platform;

    private Short step;

    private String approverName;

    private Byte itemStatus;

    private Date createTime;

    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Short getPlatform() {
        return platform;
    }

    public void setPlatform(Short platform) {
        this.platform = platform;
    }

    public Short getStep() {
        return step;
    }

    public void setStep(Short step) {
        this.step = step;
    }

    public String getApproverName() {
        return approverName;
    }

    public void setApproverName(String approverName) {
        this.approverName = approverName == null ? null : approverName.trim();
    }

    public Byte getItemStatus() {
        return itemStatus;
    }

    public void setItemStatus(Byte itemStatus) {
        this.itemStatus = itemStatus;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}