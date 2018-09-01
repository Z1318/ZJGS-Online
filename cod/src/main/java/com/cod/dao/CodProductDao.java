package com.cod.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ Author     ：ZYP
 * @ Date       ：Created in 16:30 2018/6/5
 * @ Description：${description}
 * @ Modified By：
 * @Version: $version$
 */
@Repository
public class CodProductDao {

    @Autowired
    SqlSessionTemplate template;

    private final String MAPPER_NAMESPACE = "com.cod.dao.mapper.CodProductMapper";


    public String getDomainById(int id){
        String sqlId  = MAPPER_NAMESPACE  + ".selectByProductId";
        return template.selectOne(sqlId,id);
    }

    /**
     * create by:ZYP
     * description:根据uid 集合 获取 pid 集合
     * create time: 11:54 2018/6/8
     *
     * @return a
     * @Param: null
     */
    public List<Integer> selectProductIdByBlgroup(List<Integer> uids){
        String sqlId  = MAPPER_NAMESPACE  + ".selectProductIdByBlgroup";
        return template.selectList(sqlId,uids);
    }

}
