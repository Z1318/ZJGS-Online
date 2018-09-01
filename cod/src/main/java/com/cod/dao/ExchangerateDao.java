package com.cod.dao;

import com.cod.dao.common.BaseMybatisDAO;
import com.cod.entity.Exchangerate;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
@Repository
public class ExchangerateDao extends BaseMybatisDAO {
    private final String MAPPER_NAMESPACE = "com.cod.dao.mapper.ExchangerateMapper";

    public Exchangerate getExchangerateId(int id){
        String sqlId=MAPPER_NAMESPACE + ".getExchangerateById";

        return super.findOne(sqlId,id);
    }


}
