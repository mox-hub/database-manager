package com.foocode.manager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.foocode.manager.mapper.DepartmentMapper;
import com.foocode.manager.mapper.ProfessionalMapper;
import com.foocode.manager.model.*;
import com.foocode.manager.service.DepartmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @ClassName :DepartmentImpl
 * @Description :
 * @Author :Mox
 * @Date :2022/6/10 17:42
 * @Version : v1.0
 **/
@Service("department")
public class DepartmentServiceImpl implements DepartmentService {
    //  配置logger
    private final static Logger logger = LoggerFactory.getLogger(DepartmentServiceImpl.class);

    @Resource
    private DepartmentMapper departmentMapper;

    @Resource
    private ProfessionalMapper professionalMapper;

    @Override
    public Object getCascadeData() {
        List<Cascade> cascadeData = new ArrayList<Cascade>();
        Cascade cascade;
        CascadeSon cascadeSon;
        List<Department> departments = departmentMapper.selectList(null);

        for (Department department : departments) {
            cascade = new Cascade();
            cascade.setValue(department.getDepartmentId());
            cascade.setLabel(department.getDepartmentName());

            QueryWrapper<Professional> wrapper = new QueryWrapper<>();
            wrapper.eq("department_id",department.getDepartmentId());
            List<Professional> professionals = professionalMapper.selectList(wrapper);

            for (Professional professional : professionals) {
                cascadeSon = new CascadeSon();
                cascadeSon.setValue(professional.getProfessionalId());
                cascadeSon.setLabel(professional.getProfessionalName());

                cascade.addChildren(cascadeSon);
            }
            cascadeData.add(cascade);
        }
        logger.info("[database-manager] DepartmentService :: 查询系别级联信息 >>> 查询成功 [{}]",cascadeData);
        return new Response<>(cascadeData);
    }

    @Override
    public Object getAllDepartment() {
        try {
            List<Department> departments = departmentMapper.selectList(null);
            logger.info("[database-manager] DepartmentService :: 查询所有学院信息 >>> 查询成功 [{}]", departments);
            return new Response<>(departments);
        }catch (NullPointerException e) {
            Response<Object> response = new Response<>(-1, "未查询到学院！");
            logger.error("[database-manager] DepartmentService::查询所有学院信息 >>> 查询失败！[{}]",response);
            return response;
        }
    }

    @Override
    public Object queryDepartment(Map<String, String> data) {
        String mode = data.get("mode");
        String options = data.get("options");
        IPage page = new Page(Integer.parseInt(data.get("pageIndex")),Integer.parseInt(data.get("pageSize")));
        try {
            if (options.equals("all")) {
                QueryWrapper<Department> wrapper = new QueryWrapper<>();
                departmentMapper.selectPage(page,null);
                List<Department> departments = page.getRecords();
                int pageTotal = (int) page.getTotal();
                logger.info("[database-manager] DepartmentService :: 查询所有学院信息 >>> 查询成功");
                return new Response<>(departments,pageTotal);
            }
            else if (mode.equals("uid")) {
                Department department = departmentMapper.selectById(options);
                List<Department> departments = new ArrayList<>();
                departments.add(department);
                logger.info("[database-manager] DepartmentService :: 查询学院信息:: 查询模式->"+ mode + ">>> 查询成功 [{}]",departments);
                return new Response<>(departments);
            }
            else if (mode.equals("uname")) {
                QueryWrapper<Department> wrapper = new QueryWrapper<>();
                wrapper.eq("Department_name",options);
                departmentMapper.selectPage(page,wrapper);
                List<Department> Departments = page.getRecords();
                int pageTotal = (int) page.getTotal();
                logger.info("[database-manager] DepartmentService :: 查询学院信息:: 查询模式->"+ mode + ">>> 查询成功");
                return new Response<>(Departments,pageTotal);
            }
            else {
                Response<Object> response = new Response<>(-2, "查询模式错误！");
                logger.error("[database-manager] DepartmentService :: 查询所有学院信息 >>> 查询失败 [{}]" , response);
                return response;
            }
        }catch (NullPointerException e) {
            Response<Object> response = new Response<>(-1, "未查询到学院！");
            logger.error("[database-manager] DepartmentService::查询学院信息 >>> 查询失败！[{}]",response);
            return response;
        }
    }

    @Override
    public Object createDepartment(Department department) {
        try {
            int res = departmentMapper.insert(department);
            Response<Object> response = new Response<>(res, "已添加一条数据！");
            logger.info("[database-manager] DepartmentService::添加学院数据 >>> 添加成功！[{}]",response);
            return response;
        } catch (NullPointerException e) {
            Response<Object> response = new Response<>(-1, "创建失败！");
            logger.error("[database-manager] DepartmentService::添加学院数据 >>> 添加失败！[{}]",e);
            return response;
        }
    }

    @Override
    public Object updateDepartment(Map<String, String> data) {
        try {
            Department department = new Department();
            department.setDepartmentId(data.get("departmentId"));
            department.setDepartmentInfo(data.get("departmentInfo"));
            department.setDepartmentName(data.get("departmentName"));
            department.setDepartmentTel(data.get("departmentTel"));

            int res = departmentMapper.updateById(department);
            Response<Object> response = new Response<>(res, "已更新一条数据！");
            logger.info("[database-manager] DepartmentService::更新学院数据 >>> 更新成功！[{}]",response);
            return response;
        } catch (NullPointerException e) {
            Response<Object> response = new Response<>(-1, "更新失败！");
            logger.error("[database-manager] DepartmentService::更新学院数据 >>> 更新失败！[{}]",e);
            return response;
        }
    }

    @Override
    public Object deleteDepartment(String id) {
        try {
            System.out.println(id);
            int res = departmentMapper.deleteById(id);
            Response<Object> response = new Response<>(res, "已删除一条数据！");
            logger.info("[database-manager] DepartmentService::删除学院数据 >>> 删除成功！[{}]", response);
            return response;
        } catch (NullPointerException e) {
            Response<Object> response = new Response<>(-1, "删除失败！");
            logger.error("[database-manager] DepartmentService::删除学院数据 >>> 删除失败！[{}]",e);
            return response;
        }
    }
}
