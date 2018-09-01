package com.cod.utils;

import java.security.PublicKey;


public class RealGroupDataUtils {
    /*真实收入
    * income_all 一个商品的所有订单收入总和
    * exchange_rate 汇率
    * */
    public static float Real_income(float income_all,float exchange_rate){
            float result=0;
            result = Arith.floatMul(income_all,exchange_rate);
            return result;

    }

    /*
     * 真实Rio
     * real_income 真实收入
     * FB_pay FB支出
     * logistics_cost 物流成本
     * purchase_cost 采购成本
     * */
    public static float Real_roi(float real_income, float fb_pay,float logistics_cost,float purchase_cost){

        float result=0;
        if (fb_pay+logistics_cost+purchase_cost>0){
            result = Arith.floatDiv(real_income,Arith.floatAdd(Arith.floatAdd(fb_pay,logistics_cost),purchase_cost));
            return result;
        }
        return 0;

    }

    /*
     * 盈亏
     * real_income 真实收入
     * FB_pay FB支出
     * logistics_cost 物流成本
     * purchase_cost 采购成本
     * */
    public static float  Profit(float real_income,float FB_pay,float logistics_cost,float purchase_cost){
        float result=0;
        result = Arith.floatSub(real_income,Arith.floatAdd(Arith.floatAdd(FB_pay,logistics_cost),purchase_cost));
        return result;
    }

    /*
    * 客单价
    * real_income 真实收入
    * order_num 一个商品的订单数
    * */
    public static float unitPrice (float real_income,int order_num){
        float result=0;
        if (real_income > 0 && order_num > 0){

            result = Arith.floatDiv(real_income,order_num);
            return result;
        }
        return 0;
    }

    /*
    * 单次成效
    * FB_cost FB支出
    * order_num 一个商品的订单数
    * */
    public static float singleResult(float FB_cost,int order_num){
        float result=0;
        if (FB_cost > 0 && order_num > 0){

            result = Arith.floatDiv(FB_cost,order_num);

            return result;
        }
        return 0;
    }



    /*
    *拒签金额
    * dollar_income 美金收入
    * deny_rate 拒签率
    * */
    public static float Refuse_money(float dollar_income,float deny_rate){
        float result;
            result = Arith.floatMul(dollar_income,deny_rate);
            return result;

    }


    /*
    *加购物车率
    * cart_num 加车次数
    * site_conversion 网站转化
    * */
    public static float cartRate(int cart_num,float site_conversion){
        float result=0;
        if (cart_num > 0 && site_conversion > 0){

            result = Arith.floatDiv(cart_num,site_conversion);

            return result;
        }
        return 0;

    }


    /*
    * 网站购物率
    * order_num 订单数
    * site_conversion 网站转化
    * */
    public static float orderRate(int order_num,float site_conversion){
        float result=0;
        if (order_num > 0 && site_conversion > 0){

            result = Arith.floatDiv(order_num,site_conversion);

            return result;
        }
        return 0;
    }

    /*
    * cpc
    * FB_cost 支出
    * site_conversion 网站转化
    * */
    public static float cpc(float FB_cost,float site_conversion){
        float result=0;
        if (FB_cost > 0 && site_conversion > 0){

            result = Arith.floatDiv(FB_cost,site_conversion);

            return result;
        }
        return 0;

    }


    /*
    * 同比
    * today_profit 今日盈亏
    * yestoday_profit 昨日盈亏
    * */
    public static float increase(float today_profit,float yestoday_profit){
            float result=0;
            result = Arith.floatSub(today_profit,yestoday_profit);
            return result;

    }

    /**
     * 计算产品id
     */
    public static int getProductId(String sku){
         int product_id=Integer.parseInt(sku.substring(1,sku.length()))-100000;
         return product_id;
    }

    /**
     * 依据产品id获取产品sku
     */
    public static String getSku(int product_id){
        String sku="P"+String.valueOf(product_id+100000);
        return sku;
    }


    /**
     *
     * @param freight   运费，计费规则
     * @param delieverst  派送费计费规则
     * @param product_weight   产品重量
     * @return   运费
     */
    public static float getProductDelieverFree(String freight,String delieverst,float product_weight){
        float maxDeliever = 0;
        if(!delieverst.equals("0")&&product_weight>0) {
            String delieverArray[] = delieverst.split(",");
            //计算派送费
            maxDeliever = Float.parseFloat(delieverArray[0].split(":")[1]);//配送价格
            for (int i = 0; i < delieverArray.length; i++) {
                if (!delieverArray[i].split(":")[0].equals("m")) {
                    float weight = Float.parseFloat(delieverArray[i].split(":")[0]);//获取重量

                    if (product_weight > weight) {
                        maxDeliever = Float.parseFloat(delieverArray[i + 1].split(":")[1]);
                    }

                }


            }
        }
        float freightfree = 0;
        if(!freight.equals("0")&&product_weight>0) {
            String freightArray[] = freight.split(",");
            //计算运费
            freightfree = Float.parseFloat(freightArray[0].split(":")[1]);//配送价格
                for (int i = 0; i < freightArray.length; i++) {

                    if (!freightArray[i].split(":")[0].equals("m")) {
                        float weight = Float.parseFloat(freightArray[i].split(":")[0]);//获取重量

                        if (product_weight > weight) {
                            freightfree = Float.parseFloat(freightArray[i + 1].split(":")[1]);
                        }

                    }


                }

        }
        return Arith.floatAdd(maxDeliever,freightfree);
    }

}
