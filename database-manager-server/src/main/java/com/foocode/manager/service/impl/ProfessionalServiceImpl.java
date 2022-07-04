package com.foocode.manager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.foocode.manager.mapper.DepartmentMapper;
import com.foocode.manager.mapper.ProfessionalMapper;
import com.foocode.manager.model.*;
import com.foocode.manager.service.DepartmentService;
import com.foocode.manager.service.ProfessionalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @ClassName :ProfessionalSericelmpl
 * @Description :
 * @Author :Mox
 * @Date :2022/6/11 18:33
 * @Version : v1.0
 **/
@Service("professional")
public class ProfessionalServiceImpl implements ProfessionalService {
    //  配置logger
    private final static Logger logger = LoggerFactory.getLogger(ProfessionalService.class);

    @Resource
    private DepartmentMapper departmentMapper;

    @Resource
    private ProfessionalMapper professionalMapper;

    @Override
    public Object getCascadeData() {
        List<CascadeSon> cascadeData = new ArrayList<>();
        CascadeSon cascadeSon;
        List<Department> departments = departmentMapper.selectList(null);

        for (Department department : departments) {
            cascadeSon = new CascadeSon();
            cascadeSon.setValue(department.getDepartmentId());
            cascadeSon.setLabel(department.getDepartmentName());
            cascadeData.add(cascadeSon);
        }
        logger.info("[database-manager] ProfessionalService :: 查询学院级联信息 >>> 查询成功 [{}]",cascadeData);
        return new Response<>(cascadeData);
    }

    @Override
    public Object getAllProfessional() {
        try {
            List<Professional> professionals = professionalMapper.selectList(null);
            logger.info("[database-manager] ProfessionalService :: 查询所有学院信息 >>> 查询成功 [{}]", professionals);
            return new Response<>(professionals);
        }catch (NullPointerException e) {
            Response<Object> response = new Response<>(-1, "未查询到学院！");
            logger.error("[database-manager] ProfessionalService::查询所有学院信息 >>> 查询失败！[{}]",response);
            return response;
        }
    }

    @Override
    public Object queryProfessional(Map<String, String> data) {
        String mode = data.get("mode");
        String options = data.get("options");
        IPage page = new Page(Integer.parseInt(data.get("pageIndex")),Integer.parseInt(data.get("pageSize")));
        try {
            if (options.equals("all")) {
                QueryWrapper<Professional> wrapper = new QueryWrapper<>();
                professionalMapper.selectPage(page,null);
                List<Professional> professionals = page.getRecords();
                int pageTotal = (int) page.getTotal();
                logger.info("[database-manager] ProfessionalService :: 查询所有学院信息 >>> 查询成功");
                return new Response<>(professionals,pageTotal);
            }
            else if (mode.equals("uid")) {
                Professional professional = professionalMapper.selectById(options);
                List<Professional> professionals = new ArrayList<>();
                professionals.add(professional);
                logger.info("[database-manager] ProfessionalService :: 查询学院信息:: 查询模式->"+ mode + ">>> 查询成功 [{}]",professionals);
                return new Response<>(professionals);
            }
            else if (mode.equals("uname")) {
                QueryWrapper<Professional> wrapper = new QueryWrapper<>();
                wrapper.eq("professional_name",options);
                professionalMapper.selectPage(page,wrapper);
                List<Professional> Professionals = page.getRecords();
                int pageTotal = (int) page.getTotal();
                logger.info("[database-manager] ProfessionalService :: 查询学院信息:: 查询模式->"+ mode + ">>> 查询成功");
                return new Response<>(Professionals,pageTotal);
            }
            else if (mode.equals("dept")) {
                QueryWrapper<Professional> wrapper = new QueryWrapper<>();
                wrapper.eq("department_id",options);
                professionalMapper.selectPage(page,wrapper);
                List<Graduate> graduates = page.getRecords();
                int pageTotal = (int) page.getTotal();
                logger.info("[database-manager] GraduateService :: 查询毕业生信息:: 查询模式->"+ mode + ">>> 查询成功");
                return new Response<>(graduates,pageTotal);
            }
            else {
                Response<Object> response = new Response<>(-2, "查询模式错误！");
                logger.error("[database-manager] ProfessionalService :: 查询所有学院信息 >>> 查询失败 [{}]" , response);
                return response;
            }
        }catch (NullPointerException e) {
            Response<Object> response = new Response<>(-1, "未查询到学院！");
            logger.error("[database-manager] ProfessionalService::查询学院信息 >>> 查询失败！[{}]",response);
            return response;
        }
    }

    @Override
    public Object createProfessional(Professional professional) {
        try {
            int res = professionalMapper.insert(professional);
            Response<Object> response = new Response<>(res, "已添加一条数据！");
            logger.info("[database-manager] ProfessionalService::添加学院数据 >>> 添加成功！[{}]",response);
            return response;
        } catch (NullPointerException e) {
            Response<Object> response = new Response<>(-1, "创建失败！");
            logger.error("[database-manager] ProfessionalService::添加学院数据 >>> 添加失败！[{}]",e);
            return response;
        }
    }

    @Override
    public Object updateProfessional(Map<String, String> data) {
        try {
            Professional professional = new Professional();
            professional.setProfessionalId(data.get("professionalId"));
            professional.setProfessionalInfo(data.get("professionalInfo"));
            professional.setProfessionalName(data.get("professionalName"));
            professional.setDepartmentId(data.get("departmentId"));
            professional.setProfessionalTel(data.get("professionalTel"));

            int res = professionalMapper.updateById(professional);
            Response<Object> response = new Response<>(res, "已更新一条数据！");
            logger.info("[database-manager] ProfessionalService::更新学院数据 >>> 更新成功！[{}]",response);
            return response;
        } catch (NullPointerException e) {
            Response<Object> response = new Response<>(-1, "更新失败！");
            logger.error("[database-manager] ProfessionalService::更新学院数据 >>> 更新失败！[{}]",e);
            return response;
        }
    }

    @Override
    public Object deleteProfessional(String id) {
        try {
            System.out.println(id);
            int res = professionalMapper.deleteById(id);
            Response<Object> response = new Response<>(res, "已删除一条数据！");
            logger.info("[database-manager] ProfessionalService::删除学院数据 >>> 删除成功！[{}]", response);
            return response;
        } catch (NullPointerException e) {
            Response<Object> response = new Response<>(-1, "删除失败！");
            logger.error("[database-manager] ProfessionalService::删除学院数据 >>> 删除失败！[{}]",e);
            return response;
        }
    }
}
