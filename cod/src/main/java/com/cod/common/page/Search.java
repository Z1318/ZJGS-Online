package com.cod.common.page;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 查询类
 */
public class Search implements Serializable {

    private static final long serialVersionUID = -328270505586112262L;

    /**
     * 当前页码
     */
    @Getter private int page = 1;

    /**
     * 每页记录数
     */
    @Getter private int rows = 20;

    @Getter @Setter private String url;
    /**
     * 查询条件组
     */
    @Getter @Setter private List<Condition> conditionList = new ArrayList<>();

    /**
     * @param page the page to set
     */
    public void setPage(int page) {
        this.page = page;

        if (page < 1) {
            this.page = 1;
        }
    }


    /**
     * @param rows the rows to set
     */
    public void setRows(int rows) {
        this.rows = rows;

        if (rows < 1) {
            this.rows = 1;
        }
    }

    /**
     * 得到当前页第一行数据的行号
     * @return
     */
    public int getFirstRowNum() {
        return ((page - 1) * rows);
    }

}
