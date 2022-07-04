package com.foocode.manager.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName :Cascade
 * @Description :
 * @Author :Mox
 * @Date :2022/6/10 17:43
 * @Version : v1.0
 **/

public class Cascade {
    String value;
    String label;
    List<CascadeSon> children;


    public Cascade() {
        children = new ArrayList<CascadeSon>();
    }

    public void init(){
        this.value = "null";
        this.label = "null";
    }

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

    public List<CascadeSon> getChildren() {
        return children;
    }

    public void setChildren(List<CascadeSon> children) {
        this.children = children;
    }

    public void addChildren(CascadeSon child) {
        this.children.add(child);
    }

    @Override
    public String toString() {
        return "Cascade{" +
                "value='" + value + '\'' +
                ", label='" + label + '\'' +
                ", children=" + children +
                '}';
    }
}
