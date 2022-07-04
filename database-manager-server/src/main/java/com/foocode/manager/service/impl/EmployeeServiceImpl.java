package com.foocode.manager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.foocode.manager.mapper.EmployeeMapper;
import com.foocode.manager.model.Employee;
import com.foocode.manager.model.Response;
import com.foocode.manager.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @ClassName :EmployeeServiceImpl
 * @Description :
 * @Author :Mox
 * @Date :2022/6/15 9:33
 * @Version : v1.0
 **/
@Service("employee")
public class EmployeeServiceImpl implements EmployeeService {
    //  配置logger
    private final static Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    @Resource
    private EmployeeMapper employeeMapper;

    @Override
    public Object getAllEmployee() {
        try {
            List<Employee> employees = employeeMapper.selectList(null);
            logger.info("[database-manager] EmployeeService :: 查询所有雇员信息 >>> 查询成功 [{}]", employees);
            return new Response<>(employees);
        }catch (NullPointerException e) {
            Response<Object> response = new Response<>(-1, "未查询到雇员！");
            logger.error("[database-manager] EmployeeService::查询所有雇员信息 >>> 查询失败！[{}]",response);
            return response;
        }
    }

    @Override
    public Object queryEmployee(Map<String, String> data) {
        String mode = data.get("mode");
        String options = data.get("options");
        IPage page = new Page(Integer.parseInt(data.get("pageIndex")),Integer.parseInt(data.get("pageSize")));
        try {
            if (options.equals("all")) {
                QueryWrapper<Employee> wrapper = new QueryWrapper<>();
                employeeMapper.selectPage(page,null);
                List<Employee> employees = page.getRecords();
                int pageTotal = (int) page.getTotal();
                logger.info("[database-manager] EmployeeService :: 查询所有雇员信息 >>> 查询成功");
                return new Response<>(employees,pageTotal);
            }
            else if (mode.equals("uid")) {
                Employee employee = employeeMapper.selectById(options);
                List<Employee> employees = new ArrayList<>();
                employees.add(employee);
                logger.info("[database-manager] EmployeeService :: 查询雇员信息:: 查询模式->"+ mode + ">>> 查询成功 [{}]",employees);
                return new Response<>(employees);
            }
            else if (mode.equals("rid")) {
                QueryWrapper<Employee> wrapper = new QueryWrapper<>();
                wrapper.eq("reg_id",options);
                employeeMapper.selectPage(page,wrapper);
                List<Employee> employees = page.getRecords();
                int pageTotal = (int) page.getTotal();
                logger.info("[database-manager] EmployeeService :: 查询雇员信息:: 查询模式->"+ mode + ">>> 查询成功");
                return new Response<>(employees,pageTotal);
            }
            else {
                Response<Object> response = new Response<>(-2, "查询模式错误！");
                logger.error("[database-manager] EmployeeService :: 查询所有雇员信息 >>> 查询失败 [{}]" , response);
                return response;
            }
        }catch (NullPointerException e) {
            Response<Object> response = new Response<>(-1, "未查询到雇员！");
            logger.error("[database-manager] EmployeeService::查询雇员信息 >>> 查询失败！[{}]",response);
            return response;
        }
    }

    @Override
    public Object createEmployee(Employee employee) {
        try {
            System.out.println(employee.getGraduateId());
            System.out.println(employee.getRegId());
            int res = employeeMapper.insert(employee);
            Response<Object> response = new Response<>(res, "已添加一条数据！");
            logger.info("[database-manager] EmployeeService::添加雇员数据 >>> 添加成功！[{}]",response);
            return response;
        } catch (Exception e) {
            Response<Object> response = new Response<>(-1, "创建失败！");
            logger.error("[database-manager] EmployeeService::添加雇员数据 >>> 添加失败！[{}]",e);
            return response;
        }
    }

    @Override
    public Object updateEmployee(Map<String, String> data) {
        try {
            Employee employee = new Employee();
            employee.setGraduateId(data.get("graduateId"));
            employee.setRegId(data.get("regId"));

            int res = employeeMapper.update(employee,null);
            Response<Object> response = new Response<>(res, "已更新一条数据！");
            logger.info("[database-manager] EmployeeService::更新雇员数据 >>> 更新成功！[{}]",response);
            return response;
        } catch (NullPointerException e) {
            Response<Object> response = new Response<>(-1, "更新失败！");
            logger.error("[database-manager] EmployeeService::更新雇员数据 >>> 更新失败！[{}]",e);
            return response;
        }
    }

    @Override
    public Object deleteEmployee(String id) {
        try {
            System.out.println(id);
            int res = employeeMapper.deleteById(id);
            Response<Object> response = new Response<>(res, "已删除一条数据！");
            logger.info("[database-manager] EmployeeService::删除雇员数据 >>> 删除成功！[{}]", response);
            return response;
        } catch (NullPointerException e) {
            Response<Object> response = new Response<>(-1, "删除失败！");
            logger.error("[database-manager] EmployeeService::删除雇员数据 >>> 删除失败！[{}]",e);
            return response;
        }
    }
}
