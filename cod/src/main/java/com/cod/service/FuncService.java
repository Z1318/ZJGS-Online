package com.cod.service;


import com.cod.dao.FuncDao;
import com.cod.entity.manage.Func;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FuncService {
    @Autowired
    FuncDao funcDao;

    /**
     * 获取菜单
     * @return
     */
    public List<Func> getFuncs(){
        return funcDao.getFuncs();
    }

    /**
     * 插入数据
     * @param func
     */
    public void insertData(Func func){
        funcDao.insertData(func);
    }

    /**
     * 更新菜单
     * @param func
     * @return
     */
    public int updateFunc(Func func){
        return funcDao.updateFunc(func);
    }
    /**
     * 删除菜单
     * @param id
     */
    public void deleteFuncById(int id){
        funcDao.deleteFuncById(id);
    }

    /**
     * 依据url查询出菜单
     * @param url
     * @return
     */
    public Func getFuncByUrl(String url){
        return funcDao.getFuncByUrl(url);
    }

}
