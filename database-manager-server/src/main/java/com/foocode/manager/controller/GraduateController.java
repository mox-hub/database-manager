package com.foocode.manager.controller;

import com.foocode.manager.model.Graduate;
import com.foocode.manager.service.GraduateService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;
/**
 * @ClassName :GraduateController
 * @Description :GraduateController
 * @Author :Mox
 * @Date :2021/11/14 17:25
 * @Version : v2.0.0 接口版本v1
 **/

/**
 * 响应的请求域为：/usr
 * 查[获取数据]的使用方法一律为：get
 * 增、删、改的使用方法为：post
 */

@CrossOrigin
@RestController
public class GraduateController {

    @Resource(name = "graduate")
    private GraduateService graduateService;

    @GetMapping(value = "/usr/getCascadeData")
    public Object getCascadeData() {
        Object res = graduateService.getCascadeData();
        return res;
    }

    @GetMapping(value = "/usr/getAllGraduate")
    public Object getAllGraduate() {
        Object res = graduateService.getAllGraduate();
        return res;
    }

    @GetMapping(value = "/usr/queryGraduate")
    public Object queryGraduate(@RequestParam Map<String,String> data) {
        Object res = graduateService.queryGraduate(data);
        return res;
    }

    @PostMapping(value = "/usr/createGraduate")
    public Object createGraduate(@RequestBody Graduate graduate) {
        System.out.println(graduate);
        Object res = graduateService.createGraduate(graduate);
        return res;
    }

    @PostMapping(value = "/usr/updateGraduate")
    public Object updateGraduate(@RequestBody Map<String,String> data) {
        Object res = graduateService.updateGraduate(data);
        return res;
    }

    @PostMapping(value = "/usr/deleteGraduate")
    public Object deleteGraduate(@RequestParam("graduateId") String id) {
        Object res = graduateService.deleteGraduate(id);
        return res;
    }
}
