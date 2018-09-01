package com.cod.service.common;

import com.cod.entity.chart.ChartProduct;
import com.cod.entity.manage.User;
import com.cod.entity.tmp.OrderProduct;

import java.util.*;

public class CommonGroupDataService {

    /**
     * 用户id和组名对应关系
     * @param users
     * @return
     */
    public Map<Long,String> getgroupMap(List<User> users){
        Map <Long,String> map=new HashMap<>();
        for(User user:users){
            map.put(user.getId(),user.getBlgroup());
        }
        return map;
    }

    /**
     * 用户id和用户名对应关系
     * @param users
     * @return
     */
    public Map<Long,String> getUsernameMap(List<User> users){
        Map <Long,String> map=new HashMap<>();
        for(User user:users){
            map.put(user.getId(),user.getUsername());
        }
        return map;
    }

    /**
     * 依据订单集合将订单进行分组，key为组名，value为订单集合
     * @param orderProducts
     * @param usermap
     * @return
     */
    public Map<String,List<OrderProduct>> orderProductToMap(List<OrderProduct> orderProducts,Map<Long,String> usermap){
        Map<String,List<OrderProduct>> map=new HashMap<>();
        if(orderProducts!=null&&orderProducts.size()>0) {
            for (OrderProduct orderProduct : orderProducts) {
                if (map.get(usermap.get(orderProduct.getUser_id())) != null) {
                    map.get(usermap.get(orderProduct.getUser_id())).add(orderProduct);
                } else {
                    List<OrderProduct> orderProducts1 = new ArrayList<>();
                    orderProducts1.add(orderProduct);
                    map.put(usermap.get(orderProduct.getUser_id()), orderProducts1);
                }
            }
        }
        return map;
    }


    /**
     * 依据订单集合将订单进行分组，key为产品id，value为订单集合
     * @param orderProducts
     * @return
     */
    public Map<Integer,List<OrderProduct>> orderProductToMapBySku(List<OrderProduct> orderProducts){
        Map<Integer, List<OrderProduct>> map = new HashMap<>();
        if(orderProducts!=null&&orderProducts.size()>0) {
            for (OrderProduct orderProduct : orderProducts) {
                if (map.get(orderProduct.getProduct_id()) != null) {
                    map.get(orderProduct.getProduct_id()).add(orderProduct);
                } else {
                    List<OrderProduct> orderProducts1 = new ArrayList<>();
                    orderProducts1.add(orderProduct);
                    map.put(orderProduct.getProduct_id(), orderProducts1);
                }
            }
        }
        return map;
    }

    /**
     * 依据订单集合将订单进行分组，key为组名，value为订单集合
     * @param orderProducts
     * @return
     */
    public Map<String,List<ChartProduct>> chartProductToMapBySku(List<ChartProduct> orderProducts){
        Map<String,List<ChartProduct>> map=new HashMap<>();
        if(orderProducts!=null&&orderProducts.size()>0) {
            for (ChartProduct chartProduct : orderProducts) {
                if (map.get(chartProduct.getSku()) != null) {
                    map.get(chartProduct.getSku()).add(chartProduct);
                } else {
                    List<ChartProduct> chartProductList = new ArrayList<>();
                    chartProductList.add(chartProduct);
                    map.put(chartProduct.getSku(), chartProductList);
                }
            }
        }
        return map;
    }



    /**
     * 获取初始导入数据总和
     *
     * @return
     */
    public ChartProduct getTotalChartProduct(List<ChartProduct> products) {
        if (products != null && products.size() > 0) {
            ChartProduct chartProduct = new ChartProduct();
            float fbcost = 0;
            float fbincome = 0;
            int order_num = 0;
            int cart_num = 0;
            int show_num = 0;
            int site_conversion = 0;
            List<Float> singleFreeList = new ArrayList<>();
            for (ChartProduct chartProduct1 : products) {
                fbcost = fbcost + chartProduct1.getFb_cost();
                fbincome = fbincome + chartProduct1.getFb_income();
                order_num = order_num + chartProduct1.getOrder_num();
                cart_num = cart_num + chartProduct1.getCart_num();
                show_num = show_num + chartProduct1.getShow_num();
                site_conversion = site_conversion + chartProduct1.getSite_conversion();
                singleFreeList.add(chartProduct1.getSingleFree());
            }
            Collections.sort(singleFreeList);
            int minndelnum = singleFreeList.size() / 2;

            chartProduct.setTeam(products.get(0).getTeam());//获取所属组
            chartProduct.setName(products.get(0).getName());//获取商品名称
            chartProduct.setSku(products.get(0).getSku());//获取商品sku
            chartProduct.setPerson(products.get(0).getPerson());//获取投放人
            chartProduct.setFb_cost(fbcost); //faceboook支出
            chartProduct.setFb_income(fbincome);//faceboo收入
            chartProduct.setOrder_num(order_num);//设置订单数
            chartProduct.setCart_num(cart_num);//加车数
            chartProduct.setShow_num(show_num);//展示数
            chartProduct.setSite_conversion(site_conversion);//网站转化
            chartProduct.setSingleResult(fbcost / order_num);//单次成效
            chartProduct.setSingleFree(singleFreeList.get(minndelnum));//单次点击费用
            chartProduct.setLaunch_time(products.get(0).getLaunch_time());//投放时间
            chartProduct.setImport_time(products.get(0).getImport_time());//导入时间
            return chartProduct;
        }
        return null;

    }
}
