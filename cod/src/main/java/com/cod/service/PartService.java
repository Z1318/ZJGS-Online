package com.cod.service;

import com.cod.dao.CodPartsDao;
import com.cod.dao.CodPskuDao;
import com.cod.entity.CodParts;
import com.cod.entity.CodPsku;
import com.cod.exception.CodException;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @ Author     ：ZYP
 * @ Date       ：Created in 10:40 2018/6/5
 * @ Description： 配件service
 * @ Modified By：
 * @Version: $version$
 *
 */
@Slf4j
@Service
public class PartService {
    @Autowired
    CodPartsDao codPartsDao;
    @Autowired
    CodPskuDao pskuDao;
    /**
     * create by:ZYP
     * description:依据配件id集合获取libPsku集合  cod_part表 获取minPrice
     * create time: 10:45 2018/6/5
     * @Param: List<Integer> ids    配件id
     * TODO 待优化
    */
    public List<Map<String,String>> getLibPsku(List<Integer> ids){
        List<Map<String,String>> libpsku = new ArrayList<>();
        if(ids.size()>0){
            for (int i = 0; i < ids.size(); i++) {
                String minPrice = codPartsDao.selectById(ids.get(i));//获取采购id,与数量字符串
                if (!minPrice.equals("")) {
                    String[] libpskus = minPrice.split("&");
                    try {
                        if(libpskus.length>0){
                        for (String libpskust : libpskus) {
                            String libpskuArray[] = libpskust.split(",");
                            Map<String,String> map=new HashMap<>();
                            map.put("libpsku", libpskuArray[0]);//采购表id
                            int num = Integer.parseInt(libpskuArray[1]);
                            map.put("partsnum", libpskuArray[1]);//配件数量
                            libpsku.add(map);
                        }
                        }
                    } catch (Exception e) {
                       log.error("【依据配件id获取采购id与采购数量出错】",minPrice);
                    }
                }


            }
        }

        return libpsku;
    }

    /**
     * create by:ZYP
     * escription:从cod_psku表中依据库存sku获取采购成本 buyrmb 和重量  pweight
     * create time: 14:27 2018/6/5
     *
     * @rturn a
     * @Param: null    getCodPSku
     */
    //TODO 待优化
    public List<CodPsku> getBuyrmbAndPWeightBySku(List<Map<String,String>> libPskus){

        ArrayList<CodPsku> codPskus = new ArrayList<>();
        for (int i = 0; i < libPskus.size(); i++) {
            CodPsku codPsku = pskuDao.getBuyrmbAndPWeightBySku(libPskus.get(i).get("libpsku"));//获取采购记录
            codPsku.setNum(Integer.parseInt(libPskus.get(i).get("partsnum")));//设置数量

            codPskus.add(codPsku);
        }
        return codPskus;
    }

    /**
     * 获取最小的套餐
     * @param product_id
     * @return
     */
    public Map<String,String> getlibSkuByProductId(int product_id){
        List<CodParts> codParts= codPartsDao.selectMinPrice(4954);
        List<CodParts> partlist =codPartsDao.selectMinPrice(product_id);
        Map<String,String> map=new HashMap<>();
        //依据sort正序排序，获取最小的
        String info = partlist.get(0).getMinprice();
        map.put("libpsku",info.split(",")[0]);
        map.put("partsnum",info.split(",")[1]);//套餐所包含产品数量
        map.put("salePrice",partlist.get(0).getMaxprice());//套餐费用
        return map;
    }

    /**
     * 依据id获取codPsku
     */
    public CodPsku getCodPskuById(Map<String,String> map){
        CodPsku codPsku = pskuDao.getBuyrmbAndPWeightBySku(map.get("libpsku"));//获取
        codPsku.setNum(Integer.parseInt(map.get("partsnum")));//设置数量
        return codPsku;
    }
}
