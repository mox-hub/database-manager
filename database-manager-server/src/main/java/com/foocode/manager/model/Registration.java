package com.foocode.manager.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * @ClassName :Registration
 * @Description :
 * @Author :Mox
 * @Date :2022/6/11 18:04
 * @Version : v1.0
 **/
@TableName(value = "registration")
public class Registration {
    @TableId(type = IdType.INPUT)
    private String registrationId;
    private String jobId;
    private String companyId;
    private String requiredNum;
    private String hiresNum;

    public String getRegistrationId() {
        return registrationId;
    }

    public void setRegistrationId(String registrationId) {
        this.registrationId = registrationId;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getRequiredNum() {
        return requiredNum;
    }

    public void setRequiredNum(String requiredNum) {
        this.requiredNum = requiredNum;
    }

    public String getHiresNum() {
        return hiresNum;
    }

    public void setHiresNum(String hiresNum) {
        this.hiresNum = hiresNum;
    }

    @Override
    public String toString() {
        return "Registration{" +
                "registrationId='" + registrationId + '\'' +
                ", jobId='" + jobId + '\'' +
                ", companyId='" + companyId + '\'' +
                ", requiredNum='" + requiredNum + '\'' +
                ", hiresNum='" + hiresNum + '\'' +
                '}';
    }
}
