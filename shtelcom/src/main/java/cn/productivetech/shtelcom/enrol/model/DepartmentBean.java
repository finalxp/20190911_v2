package cn.productivetech.shtelcom.enrol.model;

public class DepartmentBean {
    private Short id;

    private String departmentId;

    private String departmentName;

    private String departmentType;

    private String departmentSupId;

    private String departmentSupName;

    public Short getId() {
        return id;
    }

    public void setId(Short id) {
        this.id = id;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId == null ? null : departmentId.trim();
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName == null ? null : departmentName.trim();
    }

    public String getDepartmentType() {
        return departmentType;
    }

    public void setDepartmentType(String departmentType) {
        this.departmentType = departmentType == null ? null : departmentType.trim();
    }

    public String getDepartmentSupId() {
        return departmentSupId;
    }

    public void setDepartmentSupId(String departmentSupId) {
        this.departmentSupId = departmentSupId == null ? null : departmentSupId.trim();
    }

    public String getDepartmentSupName() {
        return departmentSupName;
    }

    public void setDepartmentSupName(String departmentSupName) {
        this.departmentSupName = departmentSupName == null ? null : departmentSupName.trim();
    }
}