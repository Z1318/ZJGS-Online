package com.cod.entity.chart;

import com.cod.entity.BaseEntity;
import lombok.Data;

/**
 * @ Author     ：ZYP
 * @ Date       ：Created in 14:15 2018/6/6
 * @ Description：订单统计实体类
 * @ Modified By：
 * @Version: $version$
 */
@Data
public class OrderCount extends BaseEntity{
    // 真实收入
    private String realIncome;
    // 真实率
    private float realRate;
    // 待审核 1
    private int checkPending ;
    //订单创建时间
    private String date;
    // 总金额
    private String totalAmount;
    // 真实单
    private int realOder;
    //总计单数
    private int totalNum;
    //组别
    private String groupName;



}
