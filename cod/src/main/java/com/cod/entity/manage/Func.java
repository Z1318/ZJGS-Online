package com.cod.entity.manage;


import com.cod.entity.BaseEntity;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Func extends BaseEntity implements Comparable<Func>{
    private String url;
    private String name;
    private int parent_id;
    private String icon;
    private List<Func> children=new ArrayList<>();//子菜单


    //由底到高排序
    @Override
    public int compareTo(Func o) {
        if(this.id>o.id)
            return 1;
        else if(this.id<o.id)
            return -1;
        else
            return 0;
    }

}
