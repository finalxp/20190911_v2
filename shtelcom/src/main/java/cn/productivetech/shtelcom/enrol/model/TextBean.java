package cn.productivetech.shtelcom.enrol.model;

import java.util.Date;

public class TextBean {
    private Short id;

    private String textId;

    private String textWord;

    private Integer textStatus;

    private Date createTime;

    private Date updateTime;

    private String createUser;

    private String updateUser;

    public Short getId() {
        return id;
    }

    public void setId(Short id) {
        this.id = id;
    }

    public String getTextId() {
        return textId;
    }

    public void setTextId(String textId) {
        this.textId = textId == null ? null : textId.trim();
    }

    public String getTextWord() {
        return textWord;
    }

    public void setTextWord(String textWord) {
        this.textWord = textWord == null ? null : textWord.trim();
    }

    public Integer getTextStatus() {
        return textStatus;
    }

    public void setTextStatus(Integer textStatus) {
        this.textStatus = textStatus;
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

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser == null ? null : updateUser.trim();
    }
}