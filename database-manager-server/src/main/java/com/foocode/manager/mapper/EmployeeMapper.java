package com.foocode.manager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.foocode.manager.model.Employee;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;

@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {

    @Select({ "call park.getSpaceCount(2,#{@i,mode=OUT,jdbcType=INTEGER})"})
    @Options(statementType = StatementType.CALLABLE)
    Select.List getAllRate();
}
