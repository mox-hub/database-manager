package com.foocode.manager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.foocode.manager.mapper.GraduateMapper;
import com.foocode.manager.model.CascadeSon;
import com.foocode.manager.model.Department;
import com.foocode.manager.model.Graduate;
import com.foocode.manager.model.Response;
import com.foocode.manager.service.GraduateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @ClassName :GraduateServiceImpl
 * @Description :
 * @Author :Mox
 * @Date :2022/6/9 17:49
 * @Version : v1.0
 **/

@Service("graduate")
public class GraduateServiceImpl implements GraduateService {
    @Resource
    private GraduateMapper graduateMapper;
    //  配置logger
    private final static Logger logger = LoggerFactory.getLogger(GraduateService.class);

    @Override
    public Object getCascadeData() {
        List<CascadeSon> cascadeData = new ArrayList<>();
        CascadeSon cascadeSon;
        List<Graduate> graduates = graduateMapper.selectList(null);

        for (Graduate graduate : graduates) {
            cascadeSon = new CascadeSon();
            cascadeSon.setValue(graduate.getGraduateId());
            cascadeSon.setLabel(graduate.getGraduateName()+graduate.getGraduateId());
            cascadeData.add(cascadeSon);
        }
        logger.info("[database-manager] ProfessionalService :: 查询学院级联信息 >>> 查询成功 [{}]",cascadeData);
        return new Response<>(cascadeData);
    }

    @Override
    public Object getAllGraduate() {
        try {
            List<Graduate> graduates = graduateMapper.selectList(null);
            logger.info("[database-manager] GraduateService :: 查询所有毕业生信息 >>> 查询成功 [{}]", graduates);
            return new Response<>(graduates);
        }catch (NullPointerException e) {
            Response<Object> response = new Response<>(-1, "未查询到用户！");
            logger.error("[database-manager] GraduateService::查询所有毕业生信息 >>> 查询失败！[{}]",e);
            return response;
        }
    }

    @Override
    public Object queryGraduate(Map<String,String> data) {
        String mode = data.get("mode");
        String options = data.get("options");
        IPage page = new Page(Integer.parseInt(data.get("pageIndex")),Integer.parseInt(data.get("pageSize")));
        try {
            if (options.equals("all")) {
                QueryWrapper<Graduate> wrapper = new QueryWrapper<>();
                graduateMapper.selectPage(page,null);
                List<Graduate> graduates = page.getRecords();
                int pageTotal = (int) page.getTotal();
                logger.info("[database-manager] GraduateService :: 查询所有毕业生信息 >>> 查询成功");
                return new Response<>(graduates,pageTotal);
            }
            else if (mode.equals("uid")) {
                Graduate graduate = graduateMapper.selectById(options);
                List<Graduate> graduates = new ArrayList<>();
                graduates.add(graduate);
                logger.info("[database-manager] GraduateService :: 查询毕业生信息:: 查询模式->"+ mode + ">>> 查询成功 [{}]",graduate.toString());
                return new Response<>(graduates);
            }
            else if (mode.equals("uname")) {
                QueryWrapper<Graduate> wrapper = new QueryWrapper<>();
                wrapper.eq("graduate_name",options);
                graduateMapper.selectPage(page,wrapper);
                List<Graduate> graduates = page.getRecords();
                int pageTotal = (int) page.getTotal();
                logger.info("[database-manager] GraduateService :: 查询毕业生信息:: 查询模式->"+ mode + ">>> 查询成功");
                return new Response<>(graduates,pageTotal);

            }
            else if (mode.equals("prof")) {
                QueryWrapper<Graduate> wrapper = new QueryWrapper<>();
                wrapper.eq("professional_id",options);
                graduateMapper.selectPage(page,wrapper);
                List<Graduate> graduates = page.getRecords();
                int pageTotal = (int) page.getTotal();
                logger.info("[database-manager] GraduateService :: 查询毕业生信息:: 查询模式->"+ mode + ">>> 查询成功");
                return new Response<>(graduates,pageTotal);
            }
            else {
                Response<Object> response = new Response<>(-2, "查询模式错误！");
                logger.error("[database-manager] GraduateService :: 查询所有毕业生信息 >>> 查询失败 [{}]" , response);
                return response;
            }
        }catch (NullPointerException e) {
            Response<Object> response = new Response<>(-1, "未查询到用户！");
            logger.error("[database-manager] GraduateService::查询毕业生信息 >>> 查询失败！[{}]",response);
            return response;
        }
    }

    @Override
    public Object createGraduate(Graduate graduate) {
        try {
            String prof = graduate.getProfessionalId();
            graduate.setDepartmentId(prof.substring(0,3));
            int res = graduateMapper.insert(graduate);
            Response<Object> response = new Response<>(res, "已添加一条数据！");
            logger.info("[database-manager] GraduateService::添加毕业生数据 >>> 添加成功！[{}]",response);
            return response;
        } catch (NullPointerException e) {
            Response<Object> response = new Response<>(-1, "创建失败！");
            logger.error("[database-manager] GraduateService::添加毕业生数据 >>> 添加失败！[{}]",e);
            return response;
        }
    }

    @Override
    public Object updateGraduate(Map<String,String> data) {
        try {
            Graduate graduate = new Graduate();
            graduate.setGraduateId(data.get("graduateId"));
            graduate.setGraduateName(data.get("graduateName"));
            graduate.setGraduatePassword(data.get("graduatePassword"));
            graduate.setGrade(data.get("grade"));
            graduate.setSex(data.get("sex"));
            graduate.setDepartmentId(data.get("departmentId"));
            graduate.setProfessionalId(data.get("professionalId"));
            int res = graduateMapper.updateById(graduate);
            Response<Object> response = new Response<>(res, "已更新一条数据！");
            logger.info("[database-manager] GraduateService::更新毕业生数据 >>> 更新成功！[{}]",response);
            return response;
        } catch (NullPointerException e) {
            Response<Object> response = new Response<>(-1, "更新失败！");
            logger.error("[database-manager] GraduateService::更新毕业生数据 >>> 更新失败！[{}]",e);
            return response;
        }
    }

    @Override
    public Object deleteGraduate(String id) {
        try {
            int res = graduateMapper.deleteById(id);
            Response<Object> response = new Response<>(res, "已删除一条数据！");
            logger.info("[database-manager] GraduateService::删除毕业生数据 >>> 删除成功！[{}]", response);
            return response;
        } catch (NullPointerException e) {
            Response<Object> response = new Response<>(-1, "删除失败！");
            logger.error("[database-manager] GraduateService::删除毕业生数据 >>> 删除失败！[{}]",e);
            return response;
        }
    }
}
