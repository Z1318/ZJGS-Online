package com.cod.service;


import com.cod.dao.ExchangerateDao;
import com.cod.entity.Exchangerate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExchangerateService {

    @Autowired
    ExchangerateDao exchangerateDao;
    //依据id从cod_exchangerate查询出记录
    public Exchangerate getExchangeRateById(int id){
        Exchangerate exchangerate = exchangerateDao.getExchangerateId(id);
        return exchangerate;

    }

}
