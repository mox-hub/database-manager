package com.foocode.manager.service;

import com.foocode.manager.model.Graduate;

import java.util.Map;

/**
 * @InterfaceName :GraduateService
 * @Description :毕业生部分的GraduateService接口
 * @Author :Mox
 * @Date :2022/06/09 17:47
 * @Version : v1.0
 **/
public interface GraduateService {

    Object getCascadeData();

    Object getAllGraduate();

    Object queryGraduate(Map<String,String> data);

    Object createGraduate(Graduate graduate);

    Object updateGraduate(Map<String,String> data);

    Object deleteGraduate(String id);
}
