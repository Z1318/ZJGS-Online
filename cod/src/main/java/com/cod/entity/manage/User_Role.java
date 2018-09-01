package com.cod.entity.manage;

import com.cod.entity.BaseEntity;
import lombok.Data;

@Data
public class User_Role extends BaseEntity {
    private int user_id; //用户id
    private int role_id; //角色id
}
