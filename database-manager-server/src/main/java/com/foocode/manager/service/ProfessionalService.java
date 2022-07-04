package com.foocode.manager.service;

import com.foocode.manager.model.Professional;

import java.util.Map;

public interface ProfessionalService {

    Object getAllProfessional();

    Object getCascadeData();

    Object queryProfessional(Map<String,String> data);

    Object createProfessional(Professional professional);

    Object updateProfessional(Map<String,String> data);

    Object deleteProfessional(String id);
}
