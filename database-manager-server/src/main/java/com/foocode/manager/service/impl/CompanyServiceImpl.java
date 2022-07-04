package com.foocode.manager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.foocode.manager.mapper.CompanyMapper;
import com.foocode.manager.model.CascadeSon;
import com.foocode.manager.model.Company;
import com.foocode.manager.model.Department;
import com.foocode.manager.model.Response;
import com.foocode.manager.service.CompanyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @ClassName :CompanyServiceImpl
 * @Description :
 * @Author :Mox
 * @Date :2022/6/11 18:42
 * @Version : v1.0
 **/

@Service("company")
public class CompanyServiceImpl implements CompanyService {
    @Resource
    private CompanyMapper companyMapper;
    //  配置logger
    private final static Logger logger = LoggerFactory.getLogger(CompanyService.class);

    @Override
    public Object getCascadeData() {
        List<CascadeSon> cascadeData = new ArrayList<>();
        CascadeSon cascadeSon;
        List<Company> companies = companyMapper.selectList(null);

        for (Company company : companies) {
            cascadeSon = new CascadeSon();
            cascadeSon.setValue(company.getCompanyId());
            cascadeSon.setLabel(company.getCompanyName());
            cascadeData.add(cascadeSon);
        }
        logger.info("[database-manager] ProfessionalService :: 查询学院级联信息 >>> 查询成功 [{}]",cascadeData);
        return new Response<>(cascadeData);
    }

    @Override
    public Object getAllCompany() {
        try {
            List<Company> companies = companyMapper.selectList(null);
            logger.info("[database-manager] CompanyService :: 查询所有公司信息 >>> 查询成功 [{}]", companies);
            return new Response<>(companies);
        }catch (NullPointerException e) {
            Response<Object> response = new Response<>(-1, "未查询到用户！");
            logger.error("[database-manager] CompanyService::查询所有公司信息 >>> 查询失败！[{}]",response);
            return response;
        }
    }

    @Override
    public Object queryCompany(Map<String, String> data) {
        String mode = data.get("mode");
        String options = data.get("options");
        IPage page = new Page(Integer.parseInt(data.get("pageIndex")),Integer.parseInt(data.get("pageSize")));
        try {
            if (options.equals("all")) {
                QueryWrapper<Company> wrapper = new QueryWrapper<>();
                companyMapper.selectPage(page,null);
                List<Company> companies = page.getRecords();
                int pageTotal = (int) page.getTotal();
                logger.info("[database-manager] CompanyService :: 查询所有公司信息 >>> 查询成功");
                return new Response<>(companies,pageTotal);
            }
            else if (mode.equals("uid")) {
                Company company = companyMapper.selectById(options);
                List<Company> companies = new ArrayList<>();
                companies.add(company);
                logger.info("[database-manager] CompanyService :: 查询公司信息:: 查询模式->"+ mode + ">>> 查询成功 [{}]",companies);
                return new Response<>(companies);
            }
            else if (mode.equals("uname")) {
                QueryWrapper<Company> wrapper = new QueryWrapper<>();
                wrapper.eq("company_name",options);
                companyMapper.selectPage(page,wrapper);
                List<Company> Companys = page.getRecords();
                int pageTotal = (int) page.getTotal();
                logger.info("[database-manager] CompanyService :: 查询公司信息:: 查询模式->"+ mode + ">>> 查询成功");
                return new Response<>(Companys,pageTotal);
            }
            else {
                Response<Object> response = new Response<>(-2, "查询模式错误！");
                logger.error("[database-manager] CompanyService :: 查询所有公司信息 >>> 查询失败 [{}]" , response);
                return response;
            }
        }catch (NullPointerException e) {
            Response<Object> response = new Response<>(-1, "未查询到用户！");
            logger.error("[database-manager] CompanyService::查询公司信息 >>> 查询失败！[{}]",response);
            return response;
        }
    }

    @Override
    public Object createCompany(Company company) {
        try {
            int res = companyMapper.insert(company);
            Response<Object> response = new Response<>(res, "已添加一条数据！");
            logger.info("[database-manager] CompanyService::添加公司数据 >>> 添加成功！[{}]",response);
            return response;
        } catch (NullPointerException e) {
            Response<Object> response = new Response<>(-1, "创建失败！");
            logger.error("[database-manager] CompanyService::添加公司数据 >>> 添加失败！[{}]",e);
            return response;
        }
    }

    @Override
    public Object updateCompany(Map<String, String> data) {
        try {
            Company company = new Company();
            company.setCompanyId(data.get("companyId"));
            company.setCompanyName(data.get("companyName"));
            company.setContactDetails(data.get("contactDetails"));
            company.setAddress(data.get("address"));
            company.setDetailedAddress(data.get("detailedAddress"));
            int res = companyMapper.updateById(company);
            Response<Object> response = new Response<>(res, "已更新一条数据！");
            logger.info("[database-manager] CompanyService::更新公司数据 >>> 更新成功！[{}]",response);
            return response;
        } catch (NullPointerException e) {
            Response<Object> response = new Response<>(-1, "更新失败！");
            logger.error("[database-manager] CompanyService::更新公司数据 >>> 更新失败！[{}]",e);
            return response;
        }
    }

    @Override
    public Object deleteCompany(String id) {
        try {
            System.out.println(id);
            int res = companyMapper.deleteById(id);
            Response<Object> response = new Response<>(res, "已删除一条数据！");
            logger.info("[database-manager] CompanyService::删除公司数据 >>> 删除成功！[{}]", response);
            return response;
        } catch (NullPointerException e) {
            Response<Object> response = new Response<>(-1, "删除失败！");
            logger.error("[database-manager] CompanyService::删除公司数据 >>> 删除失败！[{}]",e);
            return response;
        }
    }
}
