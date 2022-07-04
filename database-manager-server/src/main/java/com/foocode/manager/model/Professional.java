package com.foocode.manager.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * @ClassName :Professional
 * @Description :
 * @Author :Mox
 * @Date :2022/6/10 17:55
 * @Version : v1.0
 **/

@TableName(value = "professional")
public class Professional {
    @TableId(type = IdType.INPUT)
    private String professionalId;
    private String professionalName;
    private String professionalInfo;
    private String professionalTel;
    private String departmentId;

    public String getProfessionalInfo() {
        return professionalInfo;
    }

    public void setProfessionalInfo(String professionalInfo) {
        this.professionalInfo = professionalInfo;
    }

    public String getProfessionalTel() {
        return professionalTel;
    }

    public void setProfessionalTel(String professionalTel) {
        this.professionalTel = professionalTel;
    }

    public String getProfessionalId() {
        return professionalId;
    }

    public void setProfessionalId(String professionalId) {
        this.professionalId = professionalId;
    }

    public String getProfessionalName() {
        return professionalName;
    }

    public void setProfessionalName(String professionalName) {
        this.professionalName = professionalName;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    @Override
    public String toString() {
        return "Professional{" +
                "professionalId='" + professionalId + '\'' +
                ", professionalName='" + professionalName + '\'' +
                ", professionalInfo='" + professionalInfo + '\'' +
                ", professionalTel='" + professionalTel + '\'' +
                ", departmentId='" + departmentId + '\'' +
                '}';
    }
}
