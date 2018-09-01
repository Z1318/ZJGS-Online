package com.cod.dto;

import com.cod.entity.BaseEntity;
import lombok.Data;


@Data
public class RoleDto extends BaseEntity {
    private String name;
    private String funcid;//caidanid
    private String create_time;

}
