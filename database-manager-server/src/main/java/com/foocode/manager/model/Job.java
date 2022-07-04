package com.foocode.manager.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * @ClassName :Job
 * @Description :
 * @Author :Mox
 * @Date :2022/6/11 18:02
 * @Version : v1.0
 **/
@TableName(value = "job")
public class Job {
    @TableId(type = IdType.INPUT)
    private String jobId;
    private String jobIntroduction;
    private String jobName;

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getJobIntroduction() {
        return jobIntroduction;
    }

    public void setJobIntroduction(String jobIntroduction) {
        this.jobIntroduction = jobIntroduction;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    @Override
    public String toString() {
        return "Job{" +
                "jobId='" + jobId + '\'' +
                ", jobIntroduction='" + jobIntroduction + '\'' +
                ", jobName='" + jobName + '\'' +
                '}';
    }
}
