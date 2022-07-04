package com.foocode.manager.service;

import com.foocode.manager.model.Company;
import java.util.Map;

public interface CompanyService {

    Object getCascadeData();

    Object getAllCompany();

    Object queryCompany(Map<String,String> data);

    Object createCompany(Company company);

    Object updateCompany(Map<String,String> data);

    Object deleteCompany(String id);

}
