package com.foocode.manager.controller;

/**
 * @ClassName :DepartmentController
 * @Description :
 * @Author :Mox
 * @Date :2022/6/10 17:36
 * @Version : v1.0
 **/

import com.foocode.manager.model.Company;
import com.foocode.manager.model.Department;
import com.foocode.manager.service.CompanyService;
import com.foocode.manager.service.DepartmentService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 响应的请求域为：/dept
 * 查[获取数据]的使用方法一律为：get
 * 增、删、改的使用方法为：post
 */
@CrossOrigin
@RestController
public class DepartmentController {

    @Resource(name = "department")
    private DepartmentService departmentService;

    @GetMapping(value = "/dept/getCascadeData")
    public Object getCascadeData() {
        Object res = departmentService.getCascadeData();
        return res;
    }

    @GetMapping(value = "/dept/getAllDepartment")
    public Object getAllDepartment() {
        System.out.println("111");
        Object res = departmentService.getAllDepartment();
        return res;
    }

    @GetMapping(value = "/dept/queryDepartment")
    public Object queryDepartment(@RequestParam Map<String,String> data) {
        Object res = departmentService.queryDepartment(data);
        return res;
    }

    @PostMapping(value = "/dept/createDepartment")
    public Object createDepartment(@RequestBody Department department) {
        Object res = departmentService.createDepartment(department);
        return res;
    }

    @PostMapping(value = "/dept/updateDepartment")
    public Object updateDepartment(@RequestBody Map<String,String> data) {
        Object res = departmentService.updateDepartment(data);
        return res;
    }

    @PostMapping(value = "/dept/deleteDepartment")
    public Object deleteDepartment(@RequestParam("departmentId") String id) {
        Object res = departmentService.deleteDepartment(id);
        return res;
    }
}
