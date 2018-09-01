package com.cod.entity.manage;

import com.cod.entity.BaseEntity;
import lombok.Data;

@Data
public class User_Group extends BaseEntity {
    private  int user_id;//用户id
    private  String groupName;//组名
}
