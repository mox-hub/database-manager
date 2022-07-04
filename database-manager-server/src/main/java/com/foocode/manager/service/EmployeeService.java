package com.foocode.manager.service;


import com.foocode.manager.model.Department;
import com.foocode.manager.model.Employee;

import java.util.Map;

public interface EmployeeService {

    Object getAllEmployee();

    Object queryEmployee(Map<String,String> data);

    Object createEmployee(Employee employee);

    Object updateEmployee(Map<String,String> data);

    Object deleteEmployee(String id);

}
