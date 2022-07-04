package com.foocode.manager.controller;

import com.foocode.manager.model.Company;
import com.foocode.manager.service.CompanyService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @ClassName :CompanyController
 * @Description :
 * @Author :Mox
 * @Date :2022/6/11 18:21
 * @Version : v1.0
 **/
@CrossOrigin
@RestController
public class CompanyController {
    @Resource(name = "company")
    private CompanyService companyService;

    @GetMapping(value = "/company/getCascadeData")
    public Object getCascadeData() {
        Object res = companyService.getCascadeData();
        return res;
    }

    @GetMapping(value = "/company/getAllCompany")
    public Object getAllCompany() {
        Object res = companyService.getAllCompany();
        return res;
    }

    @GetMapping(value = "/company/queryCompany")
    public Object queryCompany(@RequestParam Map<String,String> data) {
        Object res = companyService.queryCompany(data);
        return res;
    }

    @PostMapping(value = "/company/createCompany")
    public Object createCompany(@RequestBody Company company) {
        Object res = companyService.createCompany(company);
        return res;
    }

    @PostMapping(value = "/company/updateCompany")
    public Object updateCompany(@RequestBody Map<String,String> data) {
        Object res = companyService.updateCompany(data);
        return res;
    }

    @PostMapping(value = "/company/deleteCompany")
    public Object deleteCompany(@RequestParam("companyId") String id) {
        Object res = companyService.deleteCompany(id);
        return res;
    }
}
