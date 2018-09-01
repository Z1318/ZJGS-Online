package com.cod.entity.tmp;

import lombok.Data;

/**
 * @ Author     ：ZYP
 * @ Date       ：Created in 15:01 2018/6/14
 * @ Description：${description}
 * @ Modified By：
 * @Version: $version$
 */
@Data
public class OrderCountInfo {

    //订单价格
    private String payPrice;
    //订单状态
    private int status;
    //组别
    private String groupName;
    //汇率
    private String exchangerate;
}
