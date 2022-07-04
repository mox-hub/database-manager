package com.foocode.manager.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * @ClassName :employee
 * @Description :
 * @Author :Mox
 * @Date :2022/6/15 9:23
 * @Version : v1.0
 **/

@TableName("employee")
public class Employee {
    @TableId(type = IdType.INPUT)
    private String graduateId;
    private String regId;

    public String getGraduateId() {
        return graduateId;
    }

    public void setGraduateId(String graduateId) {
        this.graduateId = graduateId;
    }

    public String getRegId() {
        return regId;
    }

    public void setRegId(String regId) {
        this.regId = regId;
    }
}
