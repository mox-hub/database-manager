package com.foocode.manager.controller;

import com.foocode.manager.model.Company;
import com.foocode.manager.model.Professional;
import com.foocode.manager.service.CompanyService;
import com.foocode.manager.service.ProfessionalService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @ClassName :ProfessionalController
 * @Description :
 * @Author :Mox
 * @Date :2022/6/11 18:28
 * @Version : v1.0
 **/
@CrossOrigin
@RestController
public class ProfessionalController {
    @Resource(name = "professional")
    private ProfessionalService professionalService;

    @GetMapping(value = "/prof/getAllProfessional")
    public Object getAllProfessional() {
        Object res = professionalService.getAllProfessional();
        return res;
    }

    @GetMapping(value = "/prof/getCascadeData")
    public Object getCascadeData() {
        Object res = professionalService.getCascadeData();
        return res;
    }

    @GetMapping(value = "/prof/queryProfessional")
    public Object queryProfessional(@RequestParam Map<String,String> data) {
        Object res = professionalService.queryProfessional(data);
        return res;
    }

    @PostMapping(value = "/prof/createProfessional")
    public Object createProfessional(@RequestBody Professional professional) {
        Object res = professionalService.createProfessional(professional);
        return res;
    }

    @PostMapping(value = "/prof/updateProfessional")
    public Object updateProfessional(@RequestBody Map<String,String> data) {
        Object res = professionalService.updateProfessional(data);
        return res;
    }

    @PostMapping(value = "/prof/deleteProfessional")
    public Object deleteProfessional(@RequestParam("professionalId") String id) {
        Object res = professionalService.deleteProfessional(id);
        return res;
    }
}
