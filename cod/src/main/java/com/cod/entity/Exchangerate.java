package com.cod.entity;

import com.cod.entity.BaseEntity;
import lombok.Data;

@Data
public class Exchangerate extends BaseEntity {
    private String counrty;
    private String abbreviation;
    private String logistics;//物流金额
    private String exchangerate;//汇率
    private String refusalrate;//拒签率
    private String fees;   //手续费
    private String feesfix;
    private String vat;
    private String realpromotion;
    private String deliveryfee;
    private String freight;
    private String updatemessage;
    private String addtime;
    private String updatetime;
    private String adduser;
    private String uptateuser;
}
