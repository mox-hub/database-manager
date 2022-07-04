package com.foocode.manager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.foocode.manager.mapper.RegistrationMapper;
import com.foocode.manager.model.Registration;
import com.foocode.manager.model.Response;
import com.foocode.manager.service.RegistrationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @ClassName :RegistrationServicelmpl
 * @Description :
 * @Author :Mox
 * @Date :2022/6/11 18:39
 * @Version : v1.0
 **/
@Service("registration")
public class RegistrationServiceImpl implements RegistrationService {

    @Resource
    private RegistrationMapper registrationMapper;
    //  配置logger
    private final static Logger logger = LoggerFactory.getLogger(RegistrationService.class);

    @Override
    public Object getAllRegistration() {
        try {
            List<Registration> registrations = registrationMapper.selectList(null);
            logger.info("[database-manager] RegistrationService :: 查询所有登记表信息 >>> 查询成功 [{}]", registrations);
            return new Response<>(registrations);
        }catch (NullPointerException e) {
            Response<Object> response = new Response<>(-1, "未查询到用户！");
            logger.error("[database-manager] RegistrationService::查询所有登记表信息 >>> 查询失败！[{}]",e);
            return response;
        }
    }

    @Override
    public Object queryRegistration(Map<String, String> data) {
        String mode = data.get("mode");
        String options = data.get("options");
        IPage page = new Page(Integer.parseInt(data.get("pageIndex")),Integer.parseInt(data.get("pageSize")));
        try {
            if (options.equals("all")) {
                QueryWrapper<Registration> wrapper = new QueryWrapper<>();
                registrationMapper.selectPage(page,null);
                List<Registration> registrations = page.getRecords();
                int pageTotal = (int) page.getTotal();
                logger.info("[database-manager] RegistrationService :: 查询所有登记表信息 >>> 查询成功");
                return new Response<>(registrations,pageTotal);
            }
            else if (mode.equals("uid")) {
                Registration registration = registrationMapper.selectById(options);
                List<Registration> registrations = new ArrayList<>();
                registrations.add(registration);
                logger.info("[database-manager] RegistrationService :: 查询登记表信息:: 查询模式->"+ mode + ">>> 查询成功 [{}]",registration.toString());
                return new Response<>(registrations);
            }
            else if (mode.equals("jid")) {
                QueryWrapper<Registration> wrapper = new QueryWrapper<>();
                wrapper.eq("job_id",options);
                registrationMapper.selectPage(page,wrapper);
                List<Registration> registrations = page.getRecords();
                int pageTotal = (int) page.getTotal();
                logger.info("[database-manager] RegistrationService :: 查询登记表信息:: 查询模式->"+ mode + ">>> 查询成功");
                return new Response<>(registrations,pageTotal);

            }
            else if (mode.equals("cid")) {
                QueryWrapper<Registration> wrapper = new QueryWrapper<>();
                wrapper.eq("company_id",options);
                registrationMapper.selectPage(page,wrapper);
                List<Registration> registrations = page.getRecords();
                int pageTotal = (int) page.getTotal();
                logger.info("[database-manager] RegistrationService :: 查询登记表信息:: 查询模式->"+ mode + ">>> 查询成功");
                return new Response<>(registrations,pageTotal);
            }
            else {
                Response<Object> response = new Response<>(-2, "查询模式错误！");
                logger.error("[database-manager] RegistrationService :: 查询所有登记表信息 >>> 查询失败 [{}]" , response);
                return response;
            }
        }catch (NullPointerException e) {
            Response<Object> response = new Response<>(-1, "未查询到用户！");
            logger.error("[database-manager] RegistrationService::查询登记表信息 >>> 查询失败！[{}]",response);
            return response;
        }
    }

    @Override
    public Object createRegistration(Registration registration) {
        try {
            int res = registrationMapper.insert(registration);
            Response<Object> response = new Response<>(res, "已添加一条数据！");
            logger.info("[database-manager] RegistrationService::添加登记表数据 >>> 添加成功！[{}]",response);
            return response;
        } catch (NullPointerException e) {
            Response<Object> response = new Response<>(-1, "创建失败！");
            logger.error("[database-manager] RegistrationService::添加登记表数据 >>> 添加失败！[{}]",e);
            return response;
        }
    }

    @Override
    public Object updateRegistration(Map<String, String> data) {
        try {
            Registration registration = new Registration();
            registration.setRegistrationId(data.get("registrationId"));
            registration.setCompanyId(data.get("companyId"));
            registration.setJobId(data.get("jobId"));
            registration.setRequiredNum(data.get("requiredNum"));
            registration.setHiresNum(data.get("hiresNum"));
            int res = registrationMapper.updateById(registration);
            Response<Object> response = new Response<>(res, "已更新一条数据！");
            logger.info("[database-manager] RegistrationService::更新登记表数据 >>> 更新成功！[{}]",response);
            return response;
        } catch (NullPointerException e) {
            Response<Object> response = new Response<>(-1, "更新失败！");
            logger.error("[database-manager] RegistrationService::更新登记表数据 >>> 更新失败！[{}]",e);
            return response;
        }
    }

    @Override
    public Object deleteRegistration(String id) {
        try {
            int res = registrationMapper.deleteById(id);
            Response<Object> response = new Response<>(res, "已删除一条数据！");
            logger.info("[database-manager] RegistrationService::删除登记表数据 >>> 删除成功！[{}]", response);
            return response;
        } catch (NullPointerException e) {
            Response<Object> response = new Response<>(-1, "删除失败！");
            logger.error("[database-manager] RegistrationService::删除登记表数据 >>> 删除失败！[{}]",e);
            return response;
        }
    }
}
