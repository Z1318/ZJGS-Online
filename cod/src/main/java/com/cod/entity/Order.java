package com.cod.entity;


import lombok.Data;

@Data
public class Order extends BaseEntity{
    private int pid;
    private String num;
    private String size;
    private String payprice;
    private String orderid;
    private String uname;
    private String phone;
    private String email;
    private String zcode;
    private String payway;
    private String location_p;
    private String location_c;
    private String location_a;
    private String location_d;
    private String address;
    private String message;
    private String remark;
    private String checkstatus;
    private String status;
    private String istory;
    private String optuid;
    private String recoid;
    private String postnum;
    private String posttype;
    private String postid;
    private String poststate;
    private String teluser;
    private String ip;
    private String stop_date;
    private String createtime;
    private String update_order_time;
    private String receptionid;
    private String ordernumber;
}
