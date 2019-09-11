package cn.productivetech.cmos.zhongbao.model;

import java.util.Date;

public class EnrollAudioItem {
    private Long id;

    private String agentId;

    private String voiceLocation;

    private Long createBy;

    private Date createDate;

    private Byte downloadStatus;

    private String downloadError;

    private Date downloadDate;

    private Date updateDate;

    private Byte enrollStatus;

    private String enrollError;

    private Date enrollDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId == null ? null : agentId.trim();
    }

    public String getVoiceLocation() {
        return voiceLocation;
    }

    public void setVoiceLocation(String voiceLocation) {
        this.voiceLocation = voiceLocation == null ? null : voiceLocation.trim();
    }

    public Long getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Byte getDownloadStatus() {
        return downloadStatus;
    }

    public void setDownloadStatus(Byte downloadStatus) {
        this.downloadStatus = downloadStatus;
    }

    public String getDownloadError() {
        return downloadError;
    }

    public void setDownloadError(String downloadError) {
        this.downloadError = downloadError == null ? null : downloadError.trim();
    }

    public Date getDownloadDate() {
        return downloadDate;
    }

    public void setDownloadDate(Date downloadDate) {
        this.downloadDate = downloadDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Byte getEnrollStatus() {
        return enrollStatus;
    }

    public void setEnrollStatus(Byte enrollStatus) {
        this.enrollStatus = enrollStatus;
    }

    public String getEnrollError() {
        return enrollError;
    }

    public void setEnrollError(String enrollError) {
        this.enrollError = enrollError == null ? null : enrollError.trim();
    }

    public Date getEnrollDate() {
        return enrollDate;
    }

    public void setEnrollDate(Date enrollDate) {
        this.enrollDate = enrollDate;
    }
}