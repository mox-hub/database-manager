package com.foocode.manager.controller;

import com.foocode.manager.model.Job;
import com.foocode.manager.service.JobService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @ClassName :JobController
 * @Description :
 * @Author :Mox
 * @Date :2022/6/11 18:16
 * @Version : v1.0
 **/
@CrossOrigin
@RestController
public class JobController {
    @Resource(name = "job")
    private JobService jobService;

    @GetMapping(value = "/job/getAllJob")
    public Object getAllJob() {
        Object res = jobService.getAllJob();
        return res;
    }

    @GetMapping(value = "/job/getCascadeData")
    public Object getCascadeData() {
        Object res = jobService.getCascadeData();
        return res;
    }

    @GetMapping(value = "/job/queryJob")
    public Object queryJob(@RequestParam Map<String,String> data) {
        Object res = jobService.queryJob(data);
        return res;
    }

    @PostMapping(value = "/job/createJob")
    public Object createJob(@RequestBody Job job) {
        Object res = jobService.createJob(job);
        return res;
    }

    @PostMapping(value = "/job/updateJob")
    public Object updateJob(@RequestBody Map<String,String> data) {
        Object res = jobService.updateJob(data);
        return res;
    }

    @PostMapping(value = "/job/deleteJob")
    public Object deleteJob(@RequestParam("jobId") String id) {
        Object res = jobService.deleteJob(id);
        return res;
    }
}
