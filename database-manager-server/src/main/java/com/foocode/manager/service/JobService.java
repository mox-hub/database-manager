package com.foocode.manager.service;

import com.foocode.manager.model.Job;

import java.util.Map;

public interface JobService {

    Object getCascadeData();

    Object getAllJob();

    Object queryJob(Map<String,String> data);

    Object createJob(Job job);

    Object updateJob(Map<String,String> data);

    Object deleteJob(String id);
}
