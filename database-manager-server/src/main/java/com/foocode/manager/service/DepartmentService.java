package com.foocode.manager.service;

import com.foocode.manager.model.Department;

import java.util.Map;

public interface DepartmentService {

    Object getCascadeData();

    Object getAllDepartment();

    Object queryDepartment(Map<String,String> data);

    Object createDepartment(Department department);

    Object updateDepartment(Map<String,String> data);

    Object deleteDepartment(String id);
}
