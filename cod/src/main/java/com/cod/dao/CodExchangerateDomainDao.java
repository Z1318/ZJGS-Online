package com.cod.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @ Author     ：ZYP
 * @ Date       ：Created in 17:14 2018/6/5
 * @ Description：${description}
 * @ Modified By：
 * @Version: $version$
 */
@Repository
public class CodExchangerateDomainDao {

    @Autowired
    SqlSessionTemplate template;

    private final String MAPPER_NAMESPACE = "com.cod.dao.mapper.CodExchangerateDomain";

    /**
     * 依据域名查询eid
     * @param domainname
     * @return
     */
    public int getEidByDomain(String domainname){
        try{
            String sqlId = MAPPER_NAMESPACE + ".selectByDomainName";
            return template.selectOne(sqlId,domainname);
        }catch (Exception e){
            return -1;
        }

    }
}
