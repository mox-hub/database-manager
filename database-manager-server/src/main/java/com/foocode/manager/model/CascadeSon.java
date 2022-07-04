package com.foocode.manager.model;

/**
 * @ClassName :CascadeSon
 * @Description :
 * @Author :Mox
 * @Date :2022/6/10 19:14
 * @Version : v1.0
 **/

public class CascadeSon {
    String value;
    String label;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return "CascadeSon{" +
                "value='" + value + '\'' +
                ", lable='" + label + '\'' +
                '}';
    }
}
