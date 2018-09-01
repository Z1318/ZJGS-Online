package com.cod.entity.chart;

import com.cod.entity.BaseEntity;
import lombok.Data;


@Data
public class ChartProduct extends BaseEntity {
    private String team;   //所属组
    private String name;   //产品名称
    private String sku;   //产品id
    private String person;   //投放人
    private float fb_cost;   //facebook支出
    private float fb_income;    //facebook收入
    private int order_num;      //订单数
    private int cart_num;       //加入购物车数量
    private int show_num;       //展示数量
    private int site_conversion;  //网站转化
    private float singleResult; //单次成效
    private float singleFree;//单次点击费用
    private String launch_time;   //投放时间
    private String import_time;    //导入时间

}
