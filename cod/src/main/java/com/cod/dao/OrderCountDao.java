package com.cod.dao;

import com.cod.dao.common.BaseMybatisDAO;
import com.cod.entity.tmp.OrderCountInfo;
import com.cod.utils.DateUtil;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @ Author     ：ZYP
 * @ Date       ：Created in 15:57 2018/6/6
 * @ Description：${description}
 * @ Modified By：
 * @Version: $version$
 */
@Repository
public class OrderCountDao extends BaseMybatisDAO{

    @Autowired
    SqlSessionTemplate template;

    private final String MAPPER_NAMESPACE = "com.cod.dao.mapper.OrderCountMapper";

    /**
     * create by:ZYP
     * description: 从order表中获取payprice
     * create time: 16:07 2018/6/13
     *
     * @return a
     * @Param: null     */
    public List<String> getPayPriceByPid(int pid){

        String sqlId = MAPPER_NAMESPACE + ".getPayPriceByPid";
        /*float payPrice = Float.parseFloat(template.selectOne(sqlId, pid));*/
        List<String> payPrices = template.selectList(sqlId, pid);
        return payPrices;
    }
    /**
     * create by:ZYP
     * description:获取最大的日期
     * create time: 18:53 2018/6/7
     *
     * @Param: null
     * @return
     */
    public String getMaxDate(){
        String sqlId = MAPPER_NAMESPACE + ".selectMaxDate";
        String maxDate = template.selectOne(sqlId);
        return maxDate;
    }
    /**
     * create by:ZYP
     *description:获取 OrderCount的信息
     * create time: 15:14 2018/6/14
     *
     * @eturn a
     * @Param: null
     */
    public List<OrderCountInfo> getOrderCountInfo(Map<String,Object> argsMap){
        String sqlId  = MAPPER_NAMESPACE  + ".getOrderCountInfo";
        return template.selectList(sqlId,argsMap);
    }

    /**
     * create by:ZYP
     * description: 国家统计联合查询 国家/汇率/minprice/pid/status/
     * create time: 10:07 2018/6/22
     *
     * @Param: date after_date
     * @return 带国家/汇率/minprice/pid/status 集合
     */
    public List<OrderCountInfo> getCountByCountry(Map<String,Object> argsMap){
        String sqlId  = MAPPER_NAMESPACE  + ".getCountByCountry";
        return template.selectList(sqlId,argsMap);
    }

    /**
     * create by:ZYP
     * description: 获取所有的国家名称
     * create time: 14:13 2018/6/22
     *
     * @return a
     * @Param: null
    */
    public List<String> getCountryName(){
        String sqlId  = MAPPER_NAMESPACE  + ".getCountryName";
        return template.selectList(sqlId);
    }

}
