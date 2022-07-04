package com.foocode.manager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.foocode.manager.model.Graduate;
import org.apache.ibatis.annotations.Mapper;

/**
 * @InterfaceName :GraduateMapper
 * @Description :Graduate对应mapper类
 * @Author :Mox
 * @Date :2022/6/9 17:51
 * @Version : v1.0
 **/

@Mapper
public interface GraduateMapper extends BaseMapper<Graduate> {
}
