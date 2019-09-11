package cn.productivetech.cmos.zhongbao.model;

import java.util.Date;

public class EnrollModelItem {
    private Long id;

    private String agentId;

    private Byte modelType;

    private Long createBy;

    private Date createDate;

    private Long updateBy;

    private Date updateDate;

    private byte[] enrolledModel;

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

    public Byte getModelType() {
        return modelType;
    }

    public void setModelType(Byte modelType) {
        this.modelType = modelType;
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

    public Long getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(Long updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public byte[] getEnrolledModel() {
        return enrolledModel;
    }

    public void setEnrolledModel(byte[] enrolledModel) {
        this.enrolledModel = enrolledModel;
    }
}