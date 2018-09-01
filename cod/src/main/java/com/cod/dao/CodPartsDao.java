package com.cod.dao;

import com.cod.dao.common.BaseMybatisDAO;
import com.cod.entity.CodParts;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CodPartsDao extends BaseMybatisDAO{
    @Autowired
    SqlSessionTemplate template;

    private final String MAPPER_NAMESPACE = "com.cod.dao.mapper.CodPartsMapper";

    public String selectById(int id){
        String sqlId = MAPPER_NAMESPACE + ".selectById";
        return template.selectOne(sqlId,id);
    }


    public List<CodParts> selectMinPrice(int pid){
        String sqlId = MAPPER_NAMESPACE + ".selectminpricebyPid";
        return template.selectList(sqlId,pid);
    }
    public String selectMaxpriceById(int id){
        String sqlId = MAPPER_NAMESPACE + ".selectMaxpriceById";
        return template.selectOne(sqlId,id);
    }

}
