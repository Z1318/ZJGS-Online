package com.cod.entity.chart;

import lombok.Data;

/**
 * @ Author     ：ZYP
 * @ Date       ：Created in 15:22 2018/6/2
 * @ Description：${description}
 * @ Modified By：
 * @Version: $version$
 */
@Data
public class ChartPstatistics{
    private String sku;   //商品sku
    private String product_name;//商品名称
    private int sale_price;//商品售价
    private String person;   //投放人
    private String team;     //所属组
    private float fb_cost;  //facebook支出
    private Float fb_income;//facebook收入
    private float real_income;  //真实收入
    private float predict_income;//预估收入
    private float real_roi;   //真实roi
    private float profit;     //盈亏
    private int order_num;    //订单/order 数
    private float unit_price;   //客单价
    private float single_result;   //单次成效
    private float refuse_money;  //拒签金额
    private float procurement;  //采购成本
    private float logistics;    //物流成本
    private float cart_rate;   //加购物车
    private float order_rate;   //网站购物
    private float cpc;          //cpc
    private float increase;     //同比
    private int addcart_num;   //加车数
    private int site_conversion;   //网站转化
    private int promotion_cost;//推广成本
    private int real_promotion_cost;//真实推广成本
    private float profit_roi;//盈亏roi
    private String country;//国家
    private int errorordernum;//异常订单
    private int totalnum;//总订单数
    private float errororderrate;//异常订单率
    private String create_time;     //创建时间

    //国家统计新属性
    private float realCost;//真实支出
    private float incomePercentage;  //收入占比
    private float grossProfit;      //毛利
    private float abnormalPercentage; // 异常率
    private float CPO; // FB支出/order
    private int totalOrderNum;    // 总订单数
    private int abnormalOrderNum; // 异常单数
}
