package com.cod.service;


import com.cod.dao.OrderDao;
import com.cod.entity.Order;
import com.cod.entity.tmp.OrderProduct;
import com.cod.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderService {


    @Autowired
    OrderDao orderDao;
    /**
     * 依据时间，状态为1，商品id，获取订单
     * @return
     */
    public List<Order> getOrderPriceByLaunchTime(String launch_time,int status,int product_id){
        Map<String,Object> map=new HashMap();
        map.put("launch_time",launch_time);
        map.put("status",status);
        map.put("pid",product_id);
        String afterTime=DateUtil.getSpecifiedDayAfter(launch_time);
        map.put("after_time",afterTime);
        List<Order> orders=orderDao.getOrders(map);
        return orders;
    }

    /**
     * 依据时间获取订单
     * @param
     * @return
     */
    public List<OrderProduct> getOrderProductByTime(String launch_time,String after_time){
        Map<String,Object>statusMap=new HashMap();
        statusMap.put("launch_time",launch_time);
        statusMap.put("after_time",after_time);
        return orderDao.getOrderProductByTime(statusMap);
    }


    /**
     * 依据时间，状态，商品id,获取订单
     * @param
     * @return
     */
    public List<OrderProduct> getOrderProductByTimeAndProductId(String launch_time,String after_time,int product_id){
        Map<String,Object> statusMap =new HashMap<>();
        statusMap.put("product_id",product_id);
        statusMap.put("launch_time",launch_time);
        statusMap.put("after_time",after_time);
        return orderDao.getOrderProductByTime(statusMap);
    }



}
