package com.foocode.manager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.foocode.manager.model.Company;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CompanyMapper extends BaseMapper<Company> {
}
