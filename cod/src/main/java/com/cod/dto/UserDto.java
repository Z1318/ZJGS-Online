package com.cod.dto;

import com.cod.entity.BaseEntity;
import lombok.Data;


@Data
public class UserDto extends BaseEntity {

    private String username;  //姓名
    private String password;//密码
    private String phone; //手机号
    private String blgroup;//所属组
    private int status;//状态
    private String ip;//ip
    private String roles;//当前用户所拥有的角色
    private String groupNames;//组名
}
