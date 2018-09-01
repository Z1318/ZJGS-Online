package com.cod.dao;

import com.cod.entity.CodPsku;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ Author     ：ZYP
 * @ Date       ：Created in 14:41 2018/6/5
 * @ Description：${description}
 * @ Modified By：
 * @Version: $version$
 */
@Repository
public class CodPskuDao {

    @Autowired
    SqlSessionTemplate template;

    private final String MAPPER_NAMESPACE = "com.cod.dao.mapper.CodPskuMapper";

    /**
     * create by:ZYP
     * description:从cod_psku表中依据库存sku获取采购成本 buyrmb 和重量  pweight
     * create time: 14:45 2018/6/5
     *
     * @return CodPsku
     * @Param: String sku    */
    public CodPsku getBuyrmbAndPWeightBySku(String sku){
        String sqlId = MAPPER_NAMESPACE + ".selectBySku";
        return template.selectOne(sqlId,sku);
    }
}
