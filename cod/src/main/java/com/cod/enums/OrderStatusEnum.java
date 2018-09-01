package com.cod.enums;

import lombok.Getter;

@Getter
public enum  OrderStatusEnum {

    NEW("status1",1, "待审核"),
    TOSEND("status2",2, "待发货"),
    DELIVERY("status3",3, "已发货"),
    FINISHED("status4",4, "已经完成"),
    PROBLEMS("status5",5, "问题单"),
    HISTORY("status10",10, "历史"),
    CANCEL("status100",100, "已取消"),
    ;

    private String statuskey;

    private Integer code;

    private String message;

    OrderStatusEnum(String statuskey,Integer code, String message) {
        this.statuskey=statuskey;
        this.code = code;
        this.message = message;
    }

}
