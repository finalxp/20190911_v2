package cn.productivetech.cmos.zhongbao.model;

import java.util.Date;

public class ProcessAudioItem {
    private Long id;

    private String agentId;

    private String voiceLocation;

    private Byte downloadStatus;

    private Date downloadDate;

    private String downloadError;

    private Byte processStatus;

    private Date processDate;

    private Byte processResult;

    private String processError;

    private Long createBy;

    private Date createDate;

    private Date upateDate;

    private String engineVersion;

    private String processRemark;

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

    public Byte getDownloadStatus() {
        return downloadStatus;
    }

    public void setDownloadStatus(Byte downloadStatus) {
        this.downloadStatus = downloadStatus;
    }

    public Date getDownloadDate() {
        return downloadDate;
    }

    public void setDownloadDate(Date downloadDate) {
        this.downloadDate = downloadDate;
    }

    public String getDownloadError() {
        return downloadError;
    }

    public void setDownloadError(String downloadError) {
        this.downloadError = downloadError == null ? null : downloadError.trim();
    }

    public Byte getProcessStatus() {
        return processStatus;
    }

    public void setProcessStatus(Byte processStatus) {
        this.processStatus = processStatus;
    }

    public Date getProcessDate() {
        return processDate;
    }

    public void setProcessDate(Date processDate) {
        this.processDate = processDate;
    }

    public Byte getProcessResult() {
        return processResult;
    }

    public void setProcessResult(Byte processResult) {
        this.processResult = processResult;
    }

    public String getProcessError() {
        return processError;
    }

    public void setProcessError(String processError) {
        this.processError = processError == null ? null : processError.trim();
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

    public Date getUpateDate() {
        return upateDate;
    }

    public void setUpateDate(Date upateDate) {
        this.upateDate = upateDate;
    }

    public String getEngineVersion() {
        return engineVersion;
    }

    public void setEngineVersion(String engineVersion) {
        this.engineVersion = engineVersion == null ? null : engineVersion.trim();
    }

    public String getProcessRemark() {
        return processRemark;
    }

    public void setProcessRemark(String processRemark) {
        this.processRemark = processRemark == null ? null : processRemark.trim();
    }
}