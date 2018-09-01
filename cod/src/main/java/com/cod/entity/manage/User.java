package com.cod.entity.manage;


import com.cod.entity.BaseEntity;
import lombok.Data;

@Data
public class User extends BaseEntity {
    private String username;  //姓名
    private String password;//密码
    private String blgroup; //用户所属组
    private String phone;   //手机号
    private int status; //状态
    private String ip;   //ip

}
