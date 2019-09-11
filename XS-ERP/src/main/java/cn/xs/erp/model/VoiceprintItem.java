package cn.xs.erp.model;

import java.util.Date;

public class VoiceprintItem {
    private Integer id;

    private Integer empId;

    private String voiceprintMode;

    private String engineVision;

    private Integer updateNumber;

    private Date updateTime;

    private Date createTime;

    private byte[] voiceprintFeature;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getEmpId() {
        return empId;
    }

    public void setEmpId(Integer empId) {
        this.empId = empId;
    }

    public String getVoiceprintMode() {
        return voiceprintMode;
    }

    public void setVoiceprintMode(String voiceprintMode) {
        this.voiceprintMode = voiceprintMode == null ? null : voiceprintMode.trim();
    }

    public String getEngineVision() {
        return engineVision;
    }

    public void setEngineVision(String engineVision) {
        this.engineVision = engineVision == null ? null : engineVision.trim();
    }

    public Integer getUpdateNumber() {
        return updateNumber;
    }

    public void setUpdateNumber(Integer updateNumber) {
        this.updateNumber = updateNumber;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public byte[] getVoiceprintFeature() {
        return voiceprintFeature;
    }

    public void setVoiceprintFeature(byte[] voiceprintFeature) {
        this.voiceprintFeature = voiceprintFeature;
    }
}