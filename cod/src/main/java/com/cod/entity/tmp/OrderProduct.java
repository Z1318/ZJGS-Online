package com.cod.entity.tmp;

import com.cod.entity.BaseEntity;
import lombok.Data;

/**
 * 订单商品临时表
 */
@Data
public class OrderProduct extends BaseEntity {
    private String size;//订单套餐
    private String payprice;//订单价格
    private int product_id;//商品id
    private long user_id;//用户id
    private String cnname;//商品名称
    private String title;//商品名称
    private String domainname;//域名
    private int status;//状态
}
