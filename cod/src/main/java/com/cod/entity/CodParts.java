package com.cod.entity;

import lombok.Data;

/**
 * @ Author     ：ZYP
 * @ Date       ：Created in 10:44 2018/6/5
 * @ Description：${description}
 * @ Modified By：
 * @Version: $version$
 */
@Data
public class CodParts{

    private int id;

    private int pid;

    private String title;

    private String etitle;

    private String zhdesc;

    private String minprice;

    private String maxprice;

    private String picture;

    private int sort;

    private int status;

    private String createtime;

}
