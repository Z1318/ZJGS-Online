package com.cod.service;

import com.cod.dao.UserDao;
import com.cod.entity.CodPsku;
import com.cod.entity.Exchangerate;
import com.cod.entity.Order;
import com.cod.entity.chart.ChartProduct;
import com.cod.entity.chart.ChartPstatistics;
import com.cod.utils.RealGroupDataUtils;
import com.cod.utils.DateUtil;
import com.cod.utils.GroupDataUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class GroupDataService {
    @Autowired
    UserDao userDao;
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
     *依据组名获取投放最终数据
     * @param launch_time  投放时间
     * @param groupname   组名称
     * @return
     */
    public List<ChartPstatistics> getGroupPstatisticsData(String launch_time, String groupname){
        //1.依据组名和时间获取初始导入数据
        List<ChartProduct> chartProducts=chartProductService.getProductByTeamAndLaunchTime(groupname,launch_time);
        //2.按照sku将数据进行区分
        Map<String,List<ChartProduct>> map=new HashMap<>();
        for (ChartProduct chartProduct:chartProducts){
            String sku=chartProduct.getSku();
            if (map.get(sku)!=null){
                map.get(chartProduct.getSku()).add(chartProduct);
            }
            else{
                List<ChartProduct> chartProductList=new ArrayList<>();
                chartProductList.add(chartProduct);
                map.put(chartProduct.getSku(),chartProductList);
            }
        }
        //遍历每一个商品集合数据
        List<ChartPstatistics> chartPstatisticsList=new ArrayList<>();
        for (Map.Entry<String, List<ChartProduct>> entry : map.entrySet()) {
            String sku=entry.getKey();
            List<ChartProduct> chartProducts1=entry.getValue();
            //计算初始导入数据总和
            ChartProduct chartProduct=this.getTotalChartProduct(chartProducts1);
            //获取商品最终投放数据
            ChartPstatistics chartPstatistics=this.productPstatistics(chartProduct);
            chartPstatisticsList.add(chartPstatistics);
        }

        return chartPstatisticsList;

    }

    /**
     * 获取初始导入数据总和
     * @return
     */
    public ChartProduct getTotalChartProduct(List<ChartProduct> products){
        if(products!=null&&products.size()>0) {
            ChartProduct chartProduct = new ChartProduct();
            float fbcost = 0;
            float fbincome=0;
            int order_num=0;
            int cart_num=0;
            int show_num=0;
            int site_conversion = 0;
            List<Float> singleFreeList=new ArrayList<>();
            for (ChartProduct chartProduct1 : products) {
                fbcost = fbcost + chartProduct1.getFb_cost();
                fbincome = fbincome + chartProduct1.getFb_income();
                order_num = order_num + chartProduct1.getOrder_num();
                cart_num = cart_num + chartProduct1.getCart_num();
                show_num = show_num + chartProduct1.getShow_num();
                site_conversion = site_conversion + chartProduct1.getSite_conversion();
                singleFreeList.add(chartProduct1.getSingleFree());
            }
            Collections.sort(singleFreeList);
            int minndelnum=singleFreeList.size()/2;

            chartProduct.setTeam(products.get(0).getTeam());//获取所属组
            chartProduct.setName(products.get(0).getName());//获取商品名称
            chartProduct.setSku(products.get(0).getSku());//获取商品sku
            chartProduct.setPerson(products.get(0).getPerson());//获取投放人
            chartProduct.setFb_cost(fbcost); //faceboook支出
            chartProduct.setFb_income(fbincome);//faceboo收入
            chartProduct.setOrder_num(order_num);//设置订单数
            chartProduct.setCart_num(cart_num);//加车数
            chartProduct.setShow_num(show_num);//展示数
            chartProduct.setSite_conversion(site_conversion);//网站转化
            chartProduct.setSingleResult(fbcost / order_num);//单次成效
            chartProduct.setSingleFree(singleFreeList.get(minndelnum));//单次点击费用
            chartProduct.setLaunch_time(products.get(0).getLaunch_time());//投放时间
            chartProduct.setImport_time(products.get(0).getImport_time());//导入时间
            return chartProduct;
        }
        return null;

    }


    /**
     *依据导入初始数据计算
     * @param chartProduct  导入的初始商品
     * @return
     */
    public ChartPstatistics productPstatistics(ChartProduct chartProduct){
        //1：获取商品初始导入数据
        if(chartProduct!=null){
            int product_id=RealGroupDataUtils.getProductId(chartProduct.getSku());//商品id
            float fb_cost=chartProduct.getFb_cost();//商品当前facebook支出
            float fb_income=chartProduct.getFb_income();//商品facebook收入
            int cart_num=chartProduct.getCart_num();//加车次数
            int order_num=chartProduct.getOrder_num();
            int site_conversion=chartProduct.getSite_conversion();//网站转化
            String launch_time=chartProduct.getLaunch_time();//投放时间
            String person=chartProduct.getPerson();//投放人
            String product_name=chartProduct.getName();//商品名称
            String team=chartProduct.getTeam();
            //获取汇率
            Exchangerate exchangerate=this.getExchangerate(product_id);
            //2：计算调整收入
            float sign_rate=1-Float.parseFloat(exchangerate.getRefusalrate());//品指数
            float realPrice= GroupDataUtils.realIncome(fb_income,sign_rate);//获取调整价格

            //3.真实Roi
            //获取总物流，采购成本，单个采购成本,套餐售价，套餐物流成本
            Map<String,Float> map=this.getbuyAndDeliever(product_id,exchangerate,order_num,fb_income);

            float initialroi=GroupDataUtils.realRoi(fb_income,sign_rate,fb_cost,map.get("logistics"),map.get("totalbuyrmb"));
            BigDecimal b = new BigDecimal(Float.toString(initialroi));
            BigDecimal one = new BigDecimal("1");
            float roi=b.divide(one,2,BigDecimal.ROUND_HALF_UP).floatValue()*100;
            //4.计算今日盈亏，昨日盈亏
            float todayProfit=GroupDataUtils.profit(realPrice,fb_cost,map.get("logistics"),map.get("totalbuyrmb"));

            float yestadayProfit=getYesterdayProfit(DateUtil.getSpecifiedDayBefore(launch_time), product_id,team);

            //5.单次成效
            float singleResult=GroupDataUtils.singleResult(fb_cost,order_num);

            //6.cpc
            float cpc=chartProduct.getSingleFree();

            //7.同比
            float increase=RealGroupDataUtils.increase(todayProfit,yestadayProfit);
            float salePrice=map.get("salePrice");//套餐售价
            float deleverfree=map.get("deleverfree");//套餐物流成本
            float caigoufree=map.get("singlebuy");//套餐采购成本


            //8.推广成本
            int spreadPay=GroupDataUtils.spreadPay(salePrice*Float.parseFloat(exchangerate.getExchangerate()),deleverfree,caigoufree);

            //9盈亏roi
            float initprofitRoi=GroupDataUtils.profitRoi(salePrice*Float.parseFloat(exchangerate.getExchangerate()),spreadPay);
            BigDecimal c = new BigDecimal(Float.toString(initprofitRoi));
            BigDecimal two = new BigDecimal("1");
            float profitRoi=c.divide(two,2,BigDecimal.ROUND_HALF_UP).floatValue()*100;
            //10.真实推广成本
            int realspreadPay=GroupDataUtils.realspreadPay(salePrice*Float.parseFloat(exchangerate.getExchangerate()),Float.parseFloat(exchangerate.getRealpromotion()),deleverfree,map.get("singlebuy"));



            ChartPstatistics chartPstatistics=new ChartPstatistics();
            chartPstatistics.setProduct_name(product_name);//产品名称
            chartPstatistics.setPerson(person);//投放人
            chartPstatistics.setSku(chartProduct.getSku());//产品sku
            chartPstatistics.setTeam(chartProduct.getTeam());//所属组
            chartPstatistics.setAddcart_num(cart_num);//加车数
            chartPstatistics.setSale_price((int)salePrice);//套餐售价
            chartPstatistics.setFb_cost(fb_cost);//facebook支出
            chartPstatistics.setFb_income(fb_income);//facebook收入
            chartPstatistics.setCpc(cpc);//cpc
            chartPstatistics.setIncrease(increase);//同比
            chartPstatistics.setLogistics(map.get("logistics"));//物流成本
            chartPstatistics.setProcurement(map.get("totalbuyrmb"));//采购成本
            chartPstatistics.setProfit(todayProfit);//今日盈亏
            chartPstatistics.setOrder_num(order_num);//下单数
            chartPstatistics.setReal_income(realPrice); //调整收入
            chartPstatistics.setSingle_result(singleResult);//单次成效
            chartPstatistics.setReal_roi(roi);//真实roi
            chartPstatistics.setPromotion_cost(spreadPay);//推广成本
            chartPstatistics.setReal_promotion_cost(realspreadPay);//真实推广成本
            chartPstatistics.setCountry(exchangerate.getCounrty());//国家
            chartPstatistics.setProfit_roi(profitRoi);//盈亏roi
            chartPstatistics.setSite_conversion(site_conversion);//网站转化
            return chartPstatistics;
        }
        return null;
    }


    /**
     * 计算真实收入
     * @return
     */
    public float realPrice(List<Order> orders,Exchangerate exchangerate){
        float totalPrice=Float.parseFloat("0");
        float realPrice;//真实收入

        //获取订单总价
        for(Order order:orders){
            totalPrice=totalPrice+Float.parseFloat(order.getPayprice());
        }
        realPrice=totalPrice*Float.parseFloat(exchangerate.getExchangerate());
        return realPrice;
    }

    /**
     * 获取汇率对象
     * @param product_id
     * @return
     */
    public Exchangerate getExchangerate(int product_id){
        //获取域名
        String domain=productService.getDomainById(product_id);
        int eid=productService.getEidByDomain(domain);
        //获取汇率
        Exchangerate exchangerate=exchangerateService.getExchangeRateById(eid);
        return exchangerate;
    }

    //计算盈亏
    public float getYesterdayProfit(String time,int product_id,String team){
        //1.依据商品id,时间查询出初始数据
        List<ChartProduct> chartProductList=chartProductService.selectChartProductBySkuAndLaunchTime(time,RealGroupDataUtils.getSku(product_id),team);
        if(chartProductList!=null&&chartProductList.size()>0){
            //获取初始导入总数据
            ChartProduct chartProduct=this.getTotalChartProduct(chartProductList);

            float fb_cost=chartProduct.getFb_cost();//商品当前facebook支出
            float fb_income=chartProduct.getFb_income();//商品facebook收入
            int order_num=chartProduct.getOrder_num();
            //获取汇率
            Exchangerate exchangerate=this.getExchangerate(product_id);
            //2：计算调整收入
            float sign_rate=1-Float.parseFloat(exchangerate.getRefusalrate());//品指数
            float realPrice= GroupDataUtils.realIncome(fb_cost,sign_rate);//获取调整价格
            //获取物流成本，采购成本
            Map<String,Float> map=this.getbuyAndDeliever(product_id,exchangerate,order_num,fb_income);

            //4.计算盈亏
            float profit=GroupDataUtils.profit(realPrice,fb_cost,map.get("logistics"),map.get("totalbuyrmb"));

            return profit;
        }
       return 0;
    }



    //依据产品id获取套餐采购成本，和物流成本,手续费,  总的采购成本，物流成本
    public Map<String,Float> getbuyAndDeliever(int product_id,Exchangerate exchangerate,int order_num,float fb_income){

        //依据产品id从配件表获取最小的套餐id
        Map<String,String> map=partService.getlibSkuByProductId(product_id);

        //依据libpsku获取采购成本
        CodPsku codPsku=partService.getCodPskuById(map);
        float totalbuyrmb=Float.parseFloat("0");//总采购成本
        float logistics=Float.parseFloat("0");//总物流
        //获取计费规则
        String freight=exchangerate.getFreight();//派送费，计费规则
        String deliever=exchangerate.getDeliveryfee();//运费计费规则
        //单个采购成本
        float singlebuy=Float.parseFloat(codPsku.getBuyrmb())*codPsku.getNum()/6;

        //手续费()
          float salePrice=Float.parseFloat(map.get("salePrice"));//套餐费用
          float handleFree=salePrice*Float.parseFloat(exchangerate.getFees());//单个套餐手续费
          float totalhandleFree=handleFree*order_num*Float.parseFloat(exchangerate.getExchangerate());
         //获取重量
        float product_weight=Float.parseFloat(codPsku.getPweight())*codPsku.getNum();
         //依据重量获取套餐物流成本
        float deleverfree=RealGroupDataUtils.getProductDelieverFree(freight,deliever,product_weight);//物流成本

        totalbuyrmb=(fb_income/(salePrice*Float.parseFloat(exchangerate.getExchangerate())))*singlebuy;//总采购成本
        logistics=order_num*deleverfree+totalhandleFree;//手续费加派送费加物流费

        Map<String,Float> maps=new HashMap<>();
        maps.put("totalbuyrmb",totalbuyrmb);//总采购成本
        maps.put("logistics",logistics); //总物流成本
        maps.put("deleverfree",deleverfree); //单个套餐物流成本
        maps.put("singlebuy",singlebuy); //单个套餐采购成本
        maps.put("salePrice",salePrice);//套餐售价
        return maps;

    }


}
