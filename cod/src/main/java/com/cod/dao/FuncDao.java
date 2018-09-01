package com.cod.dao;

import com.cod.dao.common.BaseMybatisDAO;
import com.cod.entity.manage.Func;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author 张亚平
 */
@Repository
public class FuncDao extends BaseMybatisDAO {
    private final String MAPPER_NAMESPACE = "com.cod.dao.mapper.FuncMapper";

    /**
     * 插入所有的菜单
     */
    public void insertData(Func func){
        String sqlId=MAPPER_NAMESPACE + ".insert";
        super.save(sqlId,func);
    }

    /**
     * 获取所有的菜单
     */
    public List<Func> getFuncs(){
        String sqlId=MAPPER_NAMESPACE + ".getAllFunc";
        return super.findAll(sqlId);
    }

    /**
     * 更新菜单
     */
    public int updateFunc(Func func){
        String sqlId=MAPPER_NAMESPACE + ".updateById";
        return super.update(sqlId,func);
    }

    /**
     * 删除我的菜单
     */
    public void deleteFuncById(int id){
        String sqlId=MAPPER_NAMESPACE + ".deleteFuncById";
        super.delete(sqlId,id);
    }

    /**
     * 依据url查询出菜单
     * @param url
     * @return
     */
    public Func getFuncByUrl(String url){
        String sqlId=MAPPER_NAMESPACE + ".getFunc";
        return super.findOne(sqlId,url);
    }


}