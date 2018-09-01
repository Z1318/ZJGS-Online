package com.cod.service;


import com.cod.entity.CodPsku;
import com.cod.entity.Exchangerate;
import com.cod.entity.Order;
import com.cod.entity.chart.ChartProduct;
import com.cod.entity.chart.ChartPstatistics;
import com.cod.entity.manage.User;
import com.cod.entity.tmp.OrderProduct;

import com.cod.service.common.CommonGroupDataService;
import com.cod.utils.Arith;
import com.cod.utils.RealGroupDataUtils;
import com.cod.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @Author  wjn
 * 获取组，商品的真实投放数据
 */
@Slf4j
@Service
public class RealGroupDataService extends CommonGroupDataService {

    @Autowired
    MycountService mycountService;
    @Autowired
    ChartProductService chartProductService;
    @Autowired
    OrderService orderService;
    @Autowired
    ProductService productService;
    @Autowired
    ExchangerateService exchangerateService;
    @Autowired
    PartService partService;

    /**
     * 获取组真实 整体数据
     *
     * @param groupNameList  组集合
     * @param launch_time 投放时间
     * @return 组的总体数据
     */
    public List<ChartPstatistics> getGroupData(List<String> groupNameList, String launch_time) {
        List<ChartPstatistics> groupChartPstatisticsList=new ArrayList<>();
        //联合查询出当前的所有订单
        List<OrderProduct> orderProducts=orderService.getOrderProductByTime(launch_time,DateUtil.getSpecifiedDayAfter(launch_time));
        //查询出所有的用户，依据key为id,value为组名
        List<User> userList=mycountService.getAllMycount();
        //用户id组对应关系
        Map<Long,String> usermap=super.getgroupMap(userList);
        Map<Long,String> usernamemap=super.getUsernameMap(userList);
        //获取组名订单集合
        Map<String,List<OrderProduct>> orderProductMap=super.orderProductToMap(orderProducts,usermap);
        for(String team:groupNameList){
            List<ChartPstatistics> chartPstatisticsList=this.getGroupPstatisticsData(orderProductMap,usernamemap,launch_time,team);
            float totalfb_cost=0;
            int cart_num=0;
            int order_num=0;
            float realPrice=0;
            float predictPrice=0;
            float logistics=0;
            float totalbuyrmb=0;
            float refusePrice=0;
            int site_conversion=0;
            float singleResult=0;
            float increase=0;
            int errornum=0;
            int totalnum=0;
            for (ChartPstatistics chartPstatistics:chartPstatisticsList){
                totalfb_cost=totalfb_cost+chartPstatistics.getFb_cost();//总支出
                cart_num=cart_num+chartPstatistics.getAddcart_num();//加车数
                order_num=order_num+chartPstatistics.getOrder_num();//订单数
                realPrice=realPrice+chartPstatistics.getReal_income();//真实收入
                predictPrice=predictPrice+chartPstatistics.getPredict_income();//预估收入
                totalbuyrmb=totalbuyrmb+chartPstatistics.getProcurement();//采购成本
                logistics=logistics+chartPstatistics.getLogistics();//物流成本
                refusePrice=refusePrice+chartPstatistics.getRefuse_money();//拒签金额
                site_conversion=site_conversion+chartPstatistics.getSite_conversion();//网站转化
                increase=increase+chartPstatistics.getIncrease();//同比
                errornum=errornum+chartPstatistics.getErrorordernum();
                totalnum=totalnum+chartPstatistics.getTotalnum();
            }
            if(order_num!=0){
                singleResult=totalfb_cost/order_num;//单次成效
            }
            float roi = RealGroupDataUtils.Real_roi(realPrice, totalfb_cost, logistics, totalbuyrmb);//roi
            //计算今日盈亏，昨日盈亏
            float todayProfit = RealGroupDataUtils.Profit(realPrice, totalfb_cost, logistics, totalbuyrmb);

            //加购物车率
            float cart_rate = RealGroupDataUtils.cartRate(cart_num, site_conversion);
            //网站购物率
            float orderRate = RealGroupDataUtils.orderRate(order_num, site_conversion);
            //cpc
            float cpc = RealGroupDataUtils.cpc(totalfb_cost, site_conversion);
            //异常订单率
            float errorate=Arith.floatDiv(errornum,totalnum);
            ChartPstatistics chartPstatistics = new ChartPstatistics();
            chartPstatistics.setAddcart_num(cart_num);//加车数
            chartPstatistics.setFb_cost(totalfb_cost);//facebook支出
            chartPstatistics.setCart_rate(cart_rate);//加车率
            chartPstatistics.setOrder_rate(orderRate);//网站购物
            chartPstatistics.setCpc(cpc);//cpc
            chartPstatistics.setIncrease(increase);//同比
            chartPstatistics.setLogistics(logistics);//物流成本
            chartPstatistics.setProcurement(totalbuyrmb);//采购成本
            chartPstatistics.setProfit(todayProfit);//今日盈亏
            chartPstatistics.setOrder_num(order_num);//下单数
            chartPstatistics.setReal_income(realPrice); //真实收入
            chartPstatistics.setPredict_income(predictPrice);//预估收入
            chartPstatistics.setRefuse_money(refusePrice);//拒签金额
            chartPstatistics.setSingle_result(singleResult);//单次成效
            chartPstatistics.setReal_roi(roi*100);//真实roi
            chartPstatistics.setSite_conversion(site_conversion);//网站转化
            chartPstatistics.setTeam(team);//所属组
            chartPstatistics.setErrororderrate(errorate);//异常订单率
            chartPstatistics.setErrorordernum(errornum);
            chartPstatistics.setTotalnum(totalnum);
            groupChartPstatisticsList.add(chartPstatistics);
        }

        return groupChartPstatisticsList;

    }

    /**
     * 某个组商品统计真实数据
     * @param team
     * @param launch_time
     * @return
     */
    public List<ChartPstatistics> getProductData(String team, String launch_time) {
        //联合查询出当前的所有订单
        List<OrderProduct> orderProducts=orderService.getOrderProductByTime(launch_time,DateUtil.getSpecifiedDayAfter(launch_time));

        //查询出所有的用户，依据key为id,value为组名
        List<User> userList=mycountService.getAllMycount();
        //用户id,组对应关系
        Map<Long,String> usermap=super.getgroupMap(userList);

        Map<Long,String> usernamemap=super.getUsernameMap(userList);
        //获取组名订单集合
        Map<String,List<OrderProduct>> orderProductMap=super.orderProductToMap(orderProducts,usermap);

        List<ChartPstatistics> chartPstatisticsList=this.getGroupPstatisticsData(orderProductMap,usernamemap,launch_time,team);
        return chartPstatisticsList;
    }


    /**
     * 依据组名,投放时间，状态，组名获取商品最终投放数据
     * @param launch_time  投放时间
     * @param team   组名
     * @return
     */
    public List<ChartPstatistics> getGroupPstatisticsData(Map<String,List<OrderProduct>> orderProductMap,Map<Long,String> usernamemap,String launch_time, String team) {

        //依据组名获取当前组的导入数据
        List<ChartProduct> chartProductList = chartProductService.getProductByTeamAndLaunchTime(team, launch_time);
        //将导入的初始数据进行区分(sku,list)
        Map<String,List<ChartProduct>> chartProductMap=super.chartProductToMapBySku(chartProductList);

        //获取当前组的订单集合
         List<OrderProduct> orderProductList=orderProductMap.get(team);
         //按照product_id将订单进行区分
         Map<Integer,List<OrderProduct>> skuOderProduct=super.orderProductToMapBySku(orderProductList);

        //遍历每一个商品
        List<ChartPstatistics> chartPstatisticsList=new ArrayList<>();
         for (Map.Entry<Integer, List<OrderProduct>> entry : skuOderProduct.entrySet()) {
            int product_id = entry.getKey();
            String sku=RealGroupDataUtils.getSku(product_id);//商品sku
             // 获取当前商品导入的初始数据
             List<ChartProduct>chartProductList1=chartProductMap.get(sku);
             //导入的商品初始总数据
             ChartProduct chartProduct=super.getTotalChartProduct(chartProductList1);
             //当前商品的订单
             List<OrderProduct> orderProducts1 = entry.getValue();

             ChartPstatistics chartPstatistics=this.productPstatistics(usernamemap,launch_time,chartProduct,orderProducts1,team);
             if(chartPstatistics!=null){
                 chartPstatisticsList.add(chartPstatistics);
             }
            }

        return chartPstatisticsList;
    }


    /**
     *  获取当前商品的最终投放数据
     * @param usernamemap    用户id,投放人对应关系
     * @param launch_time   投放时间
     * @param chartProduct   当前商品的facebook导入数据
     * @param orders     当前商品的订单
     * @param team   当前组名
     * @return
     */
    public ChartPstatistics productPstatistics(Map<Long,String> usernamemap,String launch_time,ChartProduct chartProduct,List<OrderProduct> orders,String team ) {
       //获取异常订单数，有效单
            List<OrderProduct> realorders=new ArrayList<>();
            int errornum=0;
            for(OrderProduct orderProduct:orders){
                if(orderProduct.getStatus()==2||orderProduct.getStatus()==3||orderProduct.getStatus()==4){
                    realorders.add(orderProduct);
                }
                if(orderProduct.getStatus()==5){
                    errornum++;
                }
            }
        //异常订单率
        float errorate=Arith.floatDiv(errornum,orders.size())*100;
        //1：获取商品初始导入数据
        if (chartProduct != null) {
            int product_id = RealGroupDataUtils.getProductId(chartProduct.getSku());//依据sku获取商品id
            float fb_cost = chartProduct.getFb_cost();//商品当前facebook支出
            int cart_num = chartProduct.getCart_num();//加车次数
            int site_conversion = chartProduct.getSite_conversion();//网站转化
            String person = chartProduct.getPerson();//投放人
            String product_name = chartProduct.getName();//商品名称
            //获取汇率
            Exchangerate exchangerate=null;
            try{
                exchangerate = this.getExchangerate(product_id);
            }
            catch (Exception e){
                log.error("【依据产品id获取域名出错】","产品id:"+product_id);
                return null;
            }

            //2：计算预估收入
            float predictPrice = this.realPrice(realorders, exchangerate);
            //3.真实Roi
            //计算拒签金额(美金收入*拒签率)
            float refusePrice = this.getRefusePrice(predictPrice, exchangerate);
            //计算真实收入
            float realPrice = Arith.floatSub(predictPrice,refusePrice);
            //获取物流成本，采购成本
            Map<String, Float> map = this.getbuyAndDeliever(realorders, exchangerate);

            float roi = RealGroupDataUtils.Real_roi(realPrice, fb_cost, map.get("logistics"), map.get("totalbuyrmb"));

            //4.计算今日盈亏，昨日盈亏
            float todayProfit = RealGroupDataUtils.Profit(realPrice, fb_cost, map.get("logistics"), map.get("totalbuyrmb"));

            float yestadayProfit = getYesterdayProfit(DateUtil.getSpecifiedDayBefore(launch_time), product_id,team);
            //5.客单价
            int ordernum = realorders.size();
            float unit_price = RealGroupDataUtils.unitPrice(realPrice, ordernum);
            //6.单次成效
            float singleResult = RealGroupDataUtils.singleResult(fb_cost, ordernum);
            //7.加购物车率
            float cart_rate = RealGroupDataUtils.cartRate(cart_num, site_conversion);
            //8.网站购物率
            float orderRate = RealGroupDataUtils.orderRate(ordernum, site_conversion);
            //9.cpc
            float cpc = RealGroupDataUtils.cpc(fb_cost, site_conversion);
            //10.同比
            float increase = RealGroupDataUtils.increase(todayProfit, yestadayProfit);

            ChartPstatistics chartPstatistics = new ChartPstatistics();
            chartPstatistics.setAddcart_num(cart_num);//加车数
            chartPstatistics.setFb_cost(fb_cost);//facebook支出
            chartPstatistics.setCart_rate(cart_rate);//加购物车
            chartPstatistics.setOrder_rate(orderRate);//网站购物
            chartPstatistics.setCpc(cpc);//cpc
            chartPstatistics.setIncrease(increase);//同比
            chartPstatistics.setLogistics(map.get("logistics"));//物流成本
            chartPstatistics.setProcurement(map.get("totalbuyrmb"));//采购成本
            chartPstatistics.setProfit(todayProfit);//今日盈亏
            chartPstatistics.setOrder_num(ordernum);//下单数
            chartPstatistics.setPerson(person);//投放人
            chartPstatistics.setReal_income(realPrice); //真实收入
            chartPstatistics.setPredict_income(predictPrice); //预估收入
            chartPstatistics.setRefuse_money(refusePrice);//拒签金额
            chartPstatistics.setSingle_result(singleResult);//单次成效
            chartPstatistics.setReal_roi(roi*100);//真实roi
            chartPstatistics.setSite_conversion(site_conversion);//网站转化
            chartPstatistics.setTeam(team);//所属组
            chartPstatistics.setProduct_name(product_name);//商品名称
            chartPstatistics.setSku(chartProduct.getSku());//sku
            chartPstatistics.setCountry(exchangerate.getCounrty());//设置所属国家
            chartPstatistics.setErrororderrate(errorate);//异常订单率
            chartPstatistics.setErrorordernum(errornum);
            chartPstatistics.setTotalnum(orders.size());
            return  chartPstatistics;
        }
        //自然流量的订单
        else{
            if(orders!=null&&orders.size()>0){
                int product_id=orders.get(0).getProduct_id();
                //获取汇率
                Exchangerate exchangerate = this.getExchangerate(product_id);
                //计算预估收入
                float predictPrice = this.realPrice(orders, exchangerate);
                //获取物流成本，采购成本
                Map<String, Float> map = this.getbuyAndDeliever(orders, exchangerate);
                //计算拒签金额(美金收入*拒签率)
                float refusePrice = this.getRefusePrice(predictPrice, exchangerate);
                float realPrice=Arith.floatSub(predictPrice,refusePrice);
                //下单数
                int ordernum = orders.size();

                float todayProfit = RealGroupDataUtils.Profit(realPrice, 0, map.get("logistics"), map.get("totalbuyrmb"));
                float yestadayProfit = getYesterdayProfit(DateUtil.getSpecifiedDayBefore(launch_time), product_id,team);
                 //同比
                float increase = RealGroupDataUtils.increase(todayProfit, yestadayProfit);

                ChartPstatistics chartPstatistics = new ChartPstatistics();
                chartPstatistics.setLogistics(map.get("logistics"));//物流成本
                chartPstatistics.setProcurement(map.get("totalbuyrmb"));//采购成本
                chartPstatistics.setReal_income(realPrice); //真实收入
                chartPstatistics.setPredict_income(predictPrice); //预估收入
                chartPstatistics.setRefuse_money(refusePrice);//拒签金额
                chartPstatistics.setSku(RealGroupDataUtils.getSku(product_id));//sku
                chartPstatistics.setCountry(exchangerate.getCounrty());//设置所属国家
                chartPstatistics.setTeam(team);//所属组
                chartPstatistics.setOrder_num(ordernum);//下单数
                chartPstatistics.setProfit(todayProfit);//今日盈亏
                chartPstatistics.setIncrease(increase);//同比
                chartPstatistics.setProduct_name(orders.get(0).getTitle());//商品名称
                chartPstatistics.setSite_conversion(0);//网站转化
                chartPstatistics.setPerson(usernamemap.get(orders.get(0).getUser_id()));//投放人
                chartPstatistics.setErrororderrate(errorate);//异常订单率
                chartPstatistics.setErrorordernum(errornum);
                chartPstatistics.setTotalnum(orders.size());
                return  chartPstatistics;
            }
        }
        return null;
    }


    /**
     * 计算真实收入
     *
     * @return
     */
    public float realPrice(List<OrderProduct> orders, Exchangerate exchangerate) {
        float totalPrice = 0;
        float realPrice=0;//真实收入
        if(orders!=null&&orders.size()>0){
            //获取订单总价
            for (OrderProduct order : orders) {
                try{
                    totalPrice = totalPrice + Float.parseFloat(order.getPayprice());
                }
                catch (Exception e){
                    log.error("【获取订单价格出错】",order.getPayprice());
                };
            }
            realPrice = totalPrice * Float.parseFloat(exchangerate.getExchangerate());
        }

        return realPrice;
    }


    /**
     * 获取汇率对象
     *
     * @param product_id
     * @return
     */
    public Exchangerate getExchangerate(int product_id) {
        //获取域名
        String domain = productService.getDomainById(product_id);
        Exchangerate exchangerate=null;
        int eid = productService.getEidByDomain(domain);
        if(eid==-1){
            log.error("【依据域名获取国家汇率出错】",domain);
            return exchangerate;
        }
        //获取汇率
         exchangerate= exchangerateService.getExchangeRateById(eid);
        return exchangerate;
    }

    /**
     * 计算昨日盈亏
     * @param time   投放时间
     * @param product_id    商品id
     * @return
     */
    public Float getYesterdayProfit(String time, int product_id,String team) {
        //1.依据商品id,时间查询出初始数据
        List<ChartProduct> chartProductList = chartProductService.selectChartProductBySkuAndLaunchTime(time, RealGroupDataUtils.getSku(product_id),team);
        //获取初始导入总数据
        ChartProduct chartProduct = this.getTotalChartProduct(chartProductList);
        float fb_cost=0;
        if(chartProduct!=null){
            fb_cost = chartProduct.getFb_cost();//商品当前facebook支出
        }

        //2：计算真实收入
        //计算此商品当天所有订单总价
        List<OrderProduct> orders = orderService.getOrderProductByTimeAndProductId(time,DateUtil.getSpecifiedDayAfter(time), product_id);
        List<OrderProduct> realorders=new ArrayList<>();
        for(OrderProduct orderProduct:orders){
            if(orderProduct.getStatus()==2||orderProduct.getStatus()==3||orderProduct.getStatus()==4){
                realorders.add(orderProduct);
            }
        }
        //获取汇率
        Exchangerate exchangerate = this.getExchangerate(product_id);
        float predictPrice = this.realPrice(realorders, exchangerate);//预估收入

        //计算拒签金额(美金收入*拒签率)
        float refusePrice = getRefusePrice(predictPrice, exchangerate);
        float realPrice=Arith.floatSub(predictPrice,refusePrice);
        //获取物流成本，采购成本
        Map<String, Float> map = this.getbuyAndDeliever(realorders, exchangerate);
        float Profit = RealGroupDataUtils.Profit(realPrice, fb_cost, map.get("logistics"), map.get("totalbuyrmb"));
        return Profit;
    }

    /**
     * 计算拒签金额
     * @param realPrice    真实收入
     * @param exchangerate  汇率
     * @return
     */
    public float getRefusePrice(float realPrice, Exchangerate exchangerate) {
        float refusePrice = realPrice * Float.parseFloat(exchangerate.getRefusalrate());
        return refusePrice;
    }

    /**
     * 获取采购，物流成本
     * @param orders   订单
     * @param exchangerate   汇率
     * @return
     */
    public Map<String, Float> getbuyAndDeliever(List<OrderProduct> orders, Exchangerate exchangerate) {
        float totalbuyrmb = 0;//总采购成本
        float logistics = 0;//总物流
        if(orders!=null&&orders.size()>0) {
            List<Integer> partsids = new ArrayList<>();
            //从订单表获取配件id
            for (OrderProduct order : orders) {
                try {
                    partsids.add(Integer.parseInt(order.getSize().split("&&")[0]));
                }
                catch(Exception e){
                    log.error("【从订单获取配件id出错】",order.getSize());
                }
            }
            //依据配件id获取配件的数量,采购表id
            List<Map<String, String>> pskus = partService.getLibPsku(partsids);

            //获取采购成本表数据
            List<CodPsku> codPskus = partService.getBuyrmbAndPWeightBySku(pskus);

            //获取计费规则
            String freight = exchangerate.getFreight(); //运费，计费规则
            String deliever = exchangerate.getDeliveryfee();//派送费计费规则
            //遍历采购成本
            for (CodPsku codPsku : codPskus) {
                totalbuyrmb = totalbuyrmb + Float.parseFloat(codPsku.getBuyrmb()) * codPsku.getNum() / 6;//采购成本
                String weight=codPsku.getPweight();
                if(!weight.equals("")){
                    //获取重量
                    float product_weight = Float.parseFloat(weight) * codPsku.getNum();
                    //依据重量获取物流成本
                    float deleverfree = RealGroupDataUtils.getProductDelieverFree(freight, deliever, product_weight);//物流成本
                    logistics = logistics + deleverfree;
                }


            }
        }
        Map<String, Float> map = new HashMap<>();
        map.put("totalbuyrmb", totalbuyrmb);//总采购成本
        map.put("logistics", logistics);//总物流成本
        return map;

    }

    /**
     * 依据投放时间和组名获取初始导入数据总和
     *
     * @param launch_time
     * @param groupname
     * @return
     */
    public List<ChartProduct> getChartProducts(String launch_time, String groupname) {
        //1.依据组名和时间获取初始导入数据
        List<ChartProduct> chartProducts = chartProductService.getProductByTeamAndLaunchTime(launch_time, groupname);
        //2.按照sku将数据进行区分
        Map<String, List<ChartProduct>> map = new HashMap<>();
        for (ChartProduct chartProduct : chartProducts) {
            String sku = chartProduct.getSku();
            if (map.get(sku) != null) {
                map.get(chartProduct.getSku()).add(chartProduct);
            } else {
                List<ChartProduct> chartProductList = new ArrayList<>();
                chartProductList.add(chartProduct);
                map.put(chartProduct.getSku(), chartProductList);
            }
        }
        //遍历每一个商品集合数据
        List<ChartProduct> chartProductList = new ArrayList<>();
        for (Map.Entry<String, List<ChartProduct>> entry : map.entrySet()) {
            String sku = entry.getKey();
            List<ChartProduct> chartProducts1 = entry.getValue();
            //计算初始导入数据总和
            ChartProduct chartProduct = this.getTotalChartProduct(chartProducts1);
            chartProductList.add(chartProduct);
        }
        return chartProductList;
    }





}
