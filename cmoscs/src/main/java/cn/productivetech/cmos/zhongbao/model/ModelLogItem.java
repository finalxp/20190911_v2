package cn.productivetech.cmos.zhongbao.model;

import java.util.Date;

public class ModelLogItem {
    private Long id;

    private Long enrollId;

    private Date enrollDate;

    private Long createBy;

    private String engineVersion;

    private String voiceLocation;

    private Byte updateType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEnrollId() {
        return enrollId;
    }

    public void setEnrollId(Long enrollId) {
        this.enrollId = enrollId;
    }

    public Date getEnrollDate() {
        return enrollDate;
    }

    public void setEnrollDate(Date enrollDate) {
        this.enrollDate = enrollDate;
    }

    public Long getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    public String getEngineVersion() {
        return engineVersion;
    }

    public void setEngineVersion(String engineVersion) {
        this.engineVersion = engineVersion == null ? null : engineVersion.trim();
    }

    public String getVoiceLocation() {
        return voiceLocation;
    }

    public void setVoiceLocation(String voiceLocation) {
        this.voiceLocation = voiceLocation == null ? null : voiceLocation.trim();
    }

    public Byte getUpdateType() {
        return updateType;
    }

    public void setUpdateType(Byte updateType) {
        this.updateType = updateType;
    }
}