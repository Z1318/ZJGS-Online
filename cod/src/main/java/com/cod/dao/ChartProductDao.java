package com.cod.dao;

import com.cod.dao.common.BaseMybatisDAO;
import com.cod.entity.chart.ChartProduct;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author ZYP
 * @date 2018/5/31
 * @description (ChartProductDao)
 */
@Repository
public class ChartProductDao extends BaseMybatisDAO{
    @Autowired
    SqlSessionTemplate template;


    private final String MAPPER_NAMESPACE = "com.cod.dao.mapper.ChartProductMapper";

    public void insertProduct(ChartProduct chartProduct){
        String sqlId = MAPPER_NAMESPACE + ".insert";
        super.save(sqlId,chartProduct);
    }


    /**
     * create by:ZYP
     * description: 插入
     * create time: 17:51 2018/5/31
     *
     * @Param: ChartProduct chartProduct
     * @return void
     */
    public void batchinsertProduct(List<ChartProduct> list){
        String sqlId = MAPPER_NAMESPACE + ".batchinsert";
        super.saveBatch(sqlId,list);
    }


    /**
     * create by:ZYP
     * description: 根据名称查询
     * create time: 18:05 2018/5/31
     *
     * @return String name
     * @Param: List<ChartProduct>    */
    public List<ChartProduct> getProductByName(String name){
        String sqlId = MAPPER_NAMESPACE +  ".getProductByName";
        return super.findAll(sqlId,name);
    }

    /**
     * create by:ZYP
     * description:组名和时间查询
     * create time: 18:06 2018/5/31
     *
     * @Param: null
     * @return
     */
    public List<ChartProduct> getProductByTeamAndLaunchTime(Map<String,Object> map){
        String sqlId = MAPPER_NAMESPACE + ".getProductByTeamAndLaunchTime";
        return super.findAll(sqlId,map);
    }

    /**
     * create by:ZYP
     * description: 根据组名查询
     * create time: 18:19 2018/5/31
     *
     * @return List<ChartProduct>
     * @Param: String name     */
    public List<ChartProduct> getProductByTeam(String name){
        String sqlId = MAPPER_NAMESPACE + ".getProductByTeam";
        return super.findAll(sqlId,name);
    }

    /**
     * create by:ZYP
     * description:根据组名和投放时间删除记录
     * create time: 18:23 2018/5/31
     *
     * @return int
     * @Param: Map<String,Object> map 组 和 时间     */
    public int delProductByTeamAndLaunchTime(Map<String,Object> map){
        String sqlId = MAPPER_NAMESPACE + ".delProductByTeamAndLaunchTime";
        return super.delete(sqlId,map);
    }

    /**
     * 依据组获取当前组最近的投放时间
     * @param team
     * @return
     */
    public String selectMaxDate(String team){
        String sqlId=MAPPER_NAMESPACE + ".selectMaxDate";
        return template.selectOne(sqlId,team);
    }

    /**
     * 依据投放时间和投放人获取商品sku
     * @return
     */
    public List<String> selectSkuByLaunchTimeAndPerson(Map<String,Object> map){
        String sqlId = MAPPER_NAMESPACE + ".selectSkuByLaunchTimeAndPerson";
        return template.selectList(sqlId,map);
    }


    /**
     * 依据投放时间和sku获取投放初始数据
     * @return
     */
    public List<ChartProduct> selectChartProductBySkuAndLaunchTime(Map<String,Object> map){
        String sqlId = MAPPER_NAMESPACE + ".selectChartProductBySkuAndLaunchTime";
        return super.findAll(sqlId,map);
    }

    /**
     * 依据投放时间和组名获取所有sku
     * @return
     */
    public List<String> selectSkuByLaunchTime(Map<String,Object> map){
        String sqlId = MAPPER_NAMESPACE + ".selectSkuByLaunchTime";
        return template.selectList(sqlId,map);
    }


    /**
     * by DL
     * 传入产品，根据传入产品的id更新产品
     */
    public int updateProduct(ChartProduct chartProduct){
        String sqlId=MAPPER_NAMESPACE + ".updateById";
        return template.update(sqlId,chartProduct);

    }

    /**
     * 依据时间获取组名
     * @return
     */
    public List<String> getGroupNameByLaunchTime(String launch_time){
        String sqlId=MAPPER_NAMESPACE + ".selectGroupNameByLaunchTime";
        return template.selectList(sqlId,launch_time);
    }

    /**
     * create by:ZYP
     * description: 获取当前最大投放时间
     * create time: 10:54 2018/6/22
     *
     * @Param: null
     * @return
     */
    public String selectMaxLaunchTime(){
        String sqlId=MAPPER_NAMESPACE + ".selectMaxLaunchTime";
        return template.selectOne(sqlId);
    }

    /**
     * create by:ZYP
     * description: 依据 投放时间获取 fb_cost/sku
     * create time: 11:01 2018/6/22
     *
     * @Param: null
     * @return 
     */
    public List<ChartProduct> getFB_CostAndSKUByLaunchTime(String launchTime){
        String sqlId=MAPPER_NAMESPACE + ".getFB_CostAndSKUByLaunchTime";
        return template.selectList(sqlId,launchTime);
    }

}
