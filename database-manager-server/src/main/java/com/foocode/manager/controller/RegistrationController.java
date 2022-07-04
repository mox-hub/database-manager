package com.foocode.manager.controller;

import com.foocode.manager.model.Company;
import com.foocode.manager.model.Registration;
import com.foocode.manager.service.CompanyService;
import com.foocode.manager.service.RegistrationService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @ClassName :RegistraionContration
 * @Description :
 * @Author :Mox
 * @Date :2022/6/11 18:35
 * @Version : v1.0
 **/

@CrossOrigin
@RestController
public class RegistrationController {
    @Resource(name = "registration")
    private RegistrationService registrationService;

    @GetMapping(value = "/reg/getAllRegistration")
    public Object getAllRegistration() {
        Object res = registrationService.getAllRegistration();
        return res;
    }

    @GetMapping(value = "/reg/queryRegistration")
    public Object queryRegistration(@RequestParam Map<String,String> data) {
        Object res = registrationService.queryRegistration(data);
        return res;
    }

    @PostMapping(value = "/reg/createRegistration")
    public Object createRegistration(@RequestBody Registration registration) {
        Object res = registrationService.createRegistration(registration);
        return res;
    }

    @PostMapping(value = "/reg/updateRegistration")
    public Object updateRegistration(@RequestBody Map<String,String> data) {
        Object res = registrationService.updateRegistration(data);
        return res;
    }

    @PostMapping(value = "/reg/deleteRegistration")
    public Object deleteRegistration(@RequestParam("registrationId") String id) {
        Object res = registrationService.deleteRegistration(id);
        return res;
    }
}
