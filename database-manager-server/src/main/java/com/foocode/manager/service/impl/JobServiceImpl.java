package com.foocode.manager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.foocode.manager.mapper.JobMapper;
import com.foocode.manager.model.*;
import com.foocode.manager.service.JobService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @ClassName :JobServicelmpl
 * @Description :
 * @Author :Mox
 * @Date :2022/6/11 18:17
 * @Version : v1.0
 **/
@Service("job")
public class JobServiceImpl implements JobService {

    @Resource
    private JobMapper jobMapper;
    //  配置logger
    private final static Logger logger = LoggerFactory.getLogger(JobService.class);

    @Override
    public Object getCascadeData() {
        List<CascadeSon> cascadeData = new ArrayList<>();
        CascadeSon cascadeSon;
        List<Job> jobs = jobMapper.selectList(null);

        for (Job job : jobs) {
            cascadeSon = new CascadeSon();
            cascadeSon.setValue(job.getJobId());
            cascadeSon.setLabel(job.getJobName());
            cascadeData.add(cascadeSon);
        }
        logger.info("[database-manager] ProfessionalService :: 查询学院级联信息 >>> 查询成功 [{}]",cascadeData);
        return new Response<>(cascadeData);
    }

    @Override
    public Object getAllJob() {
        try {
            List<Job> jobs = jobMapper.selectList(null);
            logger.info("[database-manager] GraduateService :: 查询所有工作信息 >>> 查询成功 [{}]", jobs);
            return new Response<>(jobs);
        }catch (NullPointerException e) {
            Response<Object> response = new Response<>(-1, "未查询到用户！");
            logger.error("[database-manager] JobService::查询所有工作信息 >>> 查询失败！[{}]",e);
            return response;
        }
    }

    @Override
    public Object queryJob(Map<String, String> data) {
        String mode = data.get("mode");
        String options = data.get("options");
        IPage page = new Page(Integer.parseInt(data.get("pageIndex")),Integer.parseInt(data.get("pageSize")));
        try {
            if (options.equals("all")) {
                QueryWrapper<Job> wrapper = new QueryWrapper<>();
                jobMapper.selectPage(page,null);
                List<Job> jobs = page.getRecords();
                int pageTotal = (int) page.getTotal();
                logger.info("[database-manager] JobService :: 查询所有工作信息 >>> 查询成功");
                return new Response<>(jobs,pageTotal);
            }
            else if (mode.equals("uid")) {
                Job job = jobMapper.selectById(options);
                List<Job> jobs = new ArrayList<>();
                jobs.add(job);
                logger.info("[database-manager] JobService :: 查询工作信息:: 查询模式->"+ mode + ">>> 查询成功 [{}]",jobs.toString());
                return new Response<>(jobs);
            }
            else if (mode.equals("uname")) {
                QueryWrapper<Job> wrapper = new QueryWrapper<>();
                wrapper.eq("job_name",options);
                jobMapper.selectPage(page,wrapper);
                List<Job> jobs = page.getRecords();
                int pageTotal = (int) page.getTotal();
                logger.info("[database-manager] JobService :: 查询工作信息:: 查询模式->"+ mode + ">>> 查询成功");
                return new Response<>(jobs,pageTotal);

            }
            else {
                Response<Object> response = new Response<>(-2, "查询模式错误！");
                logger.error("[database-manager] JobService :: 查询所有工作信息 >>> 查询失败 [{}]" , response);
                return response;
            }
        }catch (NullPointerException e) {
            Response<Object> response = new Response<>(-1, "未查询到用户！");
            logger.error("[database-manager] JobService::查询工作信息 >>> 查询失败！[{}]",response);
            return response;
        }
    }

    @Override
    public Object createJob(Job job) {
        try {
            int res = jobMapper.insert(job);
            Response<Object> response = new Response<>(res, "已添加一条数据！");
            logger.info("[database-manager] JobService::添加工作数据 >>> 添加成功！[{}]",response);
            return response;
        } catch (NullPointerException e) {
            Response<Object> response = new Response<>(-1, "创建失败！");
            logger.error("[database-manager] JobService::添加工作数据 >>> 添加失败！[{}]",e);
            return response;
        }
    }

    @Override
    public Object updateJob(Map<String, String> data) {
        try {
            Job job = new Job();
            job.setJobId(data.get("jobId"));
            job.setJobName(data.get("jobName"));
            job.setJobIntroduction(data.get("jobIntroduction"));

            int res = jobMapper.updateById(job);
            Response<Object> response = new Response<>(res, "已更新一条数据！");
            logger.info("[database-manager] JobService::更新工作数据 >>> 更新成功！[{}]",response);
            return response;
        } catch (NullPointerException e) {
            Response<Object> response = new Response<>(-1, "更新失败！");
            logger.error("[database-manager] JobService::更新工作数据 >>> 更新失败！[{}]",e);
            return response;
        }
    }

    @Override
    public Object deleteJob(String id) {
        try {
            int res = jobMapper.deleteById(id);
            Response<Object> response = new Response<>(res, "已删除一条数据！");
            logger.info("[database-manager] JobService::删除工作数据 >>> 删除成功！[{}]", response);
            return response;
        } catch (NullPointerException e) {
            Response<Object> response = new Response<>(-1, "删除失败！");
            logger.error("[database-manager] JobService::删除工作数据 >>> 删除失败！[{}]",e);
            return response;
        }
    }
}
