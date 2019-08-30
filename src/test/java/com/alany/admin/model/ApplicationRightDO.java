package com.alany.admin.model;

import java.util.Date;

public class ApplicationRightDO {
    private Integer id;

    private String approveBatchId;

    private Short platform;

    private Integer userId;

    private Integer roleId;

    private Integer resourceId;

    private Byte resourceType;

    private String resourceTypeName;

    private Date validTime;

    private Short step;

    private Byte approveStatus;

    private Byte activeStatus;

    private Date createTime;

    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getApproveBatchId() {
        return approveBatchId;
    }

    public void setApproveBatchId(String approveBatchId) {
        this.approveBatchId = approveBatchId == null ? null : approveBatchId.trim();
    }

    public Short getPlatform() {
        return platform;
    }

    public void setPlatform(Short platform) {
        this.platform = platform;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getResourceId() {
        return resourceId;
    }

    public void setResourceId(Integer resourceId) {
        this.resourceId = resourceId;
    }

    public Byte getResourceType() {
        return resourceType;
    }

    public void setResourceType(Byte resourceType) {
        this.resourceType = resourceType;
    }

    public String getResourceTypeName() {
        return resourceTypeName;
    }

    public void setResourceTypeName(String resourceTypeName) {
        this.resourceTypeName = resourceTypeName == null ? null : resourceTypeName.trim();
    }

    public Date getValidTime() {
        return validTime;
    }

    public void setValidTime(Date validTime) {
        this.validTime = validTime;
    }

    public Short getStep() {
        return step;
    }

    public void setStep(Short step) {
        this.step = step;
    }

    public Byte getApproveStatus() {
        return approveStatus;
    }

    public void setApproveStatus(Byte approveStatus) {
        this.approveStatus = approveStatus;
    }

    public Byte getActiveStatus() {
        return activeStatus;
    }

    public void setActiveStatus(Byte activeStatus) {
        this.activeStatus = activeStatus;
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