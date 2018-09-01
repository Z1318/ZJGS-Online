package com.cod.dao;


import com.cod.dao.common.BaseMybatisDAO;
import com.cod.entity.Order;
import com.cod.entity.tmp.OrderProduct;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class OrderDao extends BaseMybatisDAO {

    private final String MAPPER_NAMESPACE = "com.cod.dao.mapper.OrderMapper";

    @Autowired
    SqlSessionTemplate sqlSessionTemplate;

    /**
     *依据时间获取订单
     * @return
     */
    public List<Order> getOrders(Map<String,Object> map){
        String sqlId=MAPPER_NAMESPACE + ".getOrderByTime";
        return super.findAll(sqlId,map);
    }

    /**
     *依据时间，状态查询出产品id，投放人，订单价格
     * @return
     */
    public List<OrderProduct> getOrderProductByTime(Map<String,Object> map){
        String sqlId=MAPPER_NAMESPACE + ".getOrderProductByTime";
        return sqlSessionTemplate.selectList(sqlId,map);
    }


}
