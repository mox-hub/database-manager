package com.foocode.manager.model;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import javax.xml.bind.annotation.XmlEnumValue;

/**
 * @ClassName :Graduate
 * @Description :
 * @Author :Mox
 * @Date :2022/6/8 21:24
 * @Version : v1.0
 **/

@TableName(value = "graduate")
public class Graduate {
    @TableId(type = IdType.INPUT)
    private String graduateId;
    private String graduateName;
    private String graduatePassword;
    @EnumValue()
    private String sex;
    @EnumValue()
    private String status;
    private String grade;
    private String departmentId;
    private String professionalId;

    public String getGraduatePassword() {
        return graduatePassword;
    }

    public void setGraduatePassword(String graduatePassword) {
        this.graduatePassword = graduatePassword;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getProfessionalId() {
        return professionalId;
    }

    public void setProfessionalId(String professionalId) {
        this.professionalId = professionalId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getGraduateId() {
        return graduateId;
    }

    public void setGraduateId(String graduateId) {
        this.graduateId = graduateId;
    }

    public String getGraduateName() {
        return graduateName;
    }

    public void setGraduateName(String graduateName) {
        this.graduateName = graduateName;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }
}

