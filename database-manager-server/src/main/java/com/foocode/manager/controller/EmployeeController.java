package com.foocode.manager.controller;

import com.foocode.manager.model.Employee;
import com.foocode.manager.model.Graduate;
import com.foocode.manager.service.EmployeeService;
import com.foocode.manager.service.GraduateService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @ClassName :EmployeeController
 * @Description :
 * @Author :Mox
 * @Date :2022/6/15 9:58
 * @Version : v1.0
 **/
@CrossOrigin
@RestController
public class EmployeeController {

    @Resource(name = "employee")
    private EmployeeService employeeService;

    @GetMapping(value = "/emp/getAllEmployee")
    public Object getAllEmployee() {
        Object res = employeeService.getAllEmployee();
        return res;
    }

    @GetMapping(value = "/emp/queryEmployee")
    public Object queryEmployee(@RequestParam Map<String,String> data) {
        Object res = employeeService.queryEmployee(data);
        return res;
    }

    @PostMapping(value = "/emp/createEmployee")
    public Object createEmployee(@RequestBody Employee employee) {
        System.out.println(employee);
        Object res = employeeService.createEmployee(employee);
        return res;
    }

    @PostMapping(value = "/emp/updateEmployee")
    public Object updateEmployee(@RequestBody Map<String,String> data) {
        Object res = employeeService.updateEmployee(data);
        return res;
    }

    @PostMapping(value = "/emp/deleteEmployee")
    public Object deleteGraduate(@RequestParam("graduateId") String id) {
        Object res = employeeService.deleteEmployee(id);
        return res;
    }
}
