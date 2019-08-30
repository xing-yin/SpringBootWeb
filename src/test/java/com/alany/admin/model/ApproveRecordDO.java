package com.alany.admin.model;

import java.util.Date;

public class ApproveRecordDO {
    private Integer id;

    private Integer applicationListId;

    private String approverName;

    private Short step;

    private String approveNote;

    private Date approveTime;

    private Byte approveStatus;

    private String passRightId;

    private String rejectRightId;

    private Date createTime;

    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getApplicationListId() {
        return applicationListId;
    }

    public void setApplicationListId(Integer applicationListId) {
        this.applicationListId = applicationListId;
    }

    public String getApproverName() {
        return approverName;
    }

    public void setApproverName(String approverName) {
        this.approverName = approverName == null ? null : approverName.trim();
    }

    public Short getStep() {
        return step;
    }

    public void setStep(Short step) {
        this.step = step;
    }

    public String getApproveNote() {
        return approveNote;
    }

    public void setApproveNote(String approveNote) {
        this.approveNote = approveNote == null ? null : approveNote.trim();
    }

    public Date getApproveTime() {
        return approveTime;
    }

    public void setApproveTime(Date approveTime) {
        this.approveTime = approveTime;
    }

    public Byte getApproveStatus() {
        return approveStatus;
    }

    public void setApproveStatus(Byte approveStatus) {
        this.approveStatus = approveStatus;
    }

    public String getPassRightId() {
        return passRightId;
    }

    public void setPassRightId(String passRightId) {
        this.passRightId = passRightId == null ? null : passRightId.trim();
    }

    public String getRejectRightId() {
        return rejectRightId;
    }

    public void setRejectRightId(String rejectRightId) {
        this.rejectRightId = rejectRightId == null ? null : rejectRightId.trim();
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