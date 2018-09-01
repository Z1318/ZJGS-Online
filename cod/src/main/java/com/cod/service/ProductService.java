package com.cod.service;


import com.cod.dao.CodExchangerateDomainDao;
import com.cod.dao.CodProductDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    @Autowired
    CodProductDao codProductDao;
    @Autowired
    CodExchangerateDomainDao codExchangerateDomain;

    /**
     * create by:ZYP
     * description:依据商品id从商品表 cod_product 查询出投放域名 domainname
     * create time: 16:33 2018/6/5
     *
     * @return a
     * @Param: null
     */
    public String getDomainById(int id){

        return codProductDao.getDomainById(id);
    }

    /**
     * create by:ZYP
     * description:依据域名  domainname 从cod_exchangerate_domain表查询出eid
     * create time: 17:09 2018/6/5
     *
     * @return a
     * @Param: null
     */
    public int getEidByDomain(String domainname){
        int eid=codExchangerateDomain.getEidByDomain(domainname);
        return eid;
    }



}
