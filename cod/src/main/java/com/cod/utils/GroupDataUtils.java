package com.cod.utils;


import lombok.Data;

@Data
public class GroupDataUtils {


    /*
     * 真实收入
     * fbIncome Facebook收入
     * productIndex 品指数
     * */
    public static float realIncome(float fbIncome,float productIndex){
        float result;
        if (fbIncome > 0 && productIndex > 0){
            return  result = Arith.floatMul(fbIncome,productIndex);
        }
        return 0;
    }

    /*
     *真实Roi
     * fbIncome facebook收入
     * productIndex 品指数
     * fb_cost Facebook支出
     * logistics 物流成本
     * procurement 采购成本
     * */
    public static float realRoi(float fbIncome,float productIndex,float fb_cost,float logistics,float procurement){
        float result;
        if (fbIncome > 0 && productIndex > 0 && fb_cost >0 && logistics > 0 && procurement > 0){

            return  result = Arith.floatDiv(Arith.floatMul(fbIncome,productIndex),(Arith.floatAdd(Arith.floatAdd(fb_cost,logistics),procurement)));
        }
        return 0;
    }


    /*
     * 盈亏
     * realIncome 真实收入
     * fb_cost Facebook支出
     * logistics 物流成本
     * procurement 采购成本
     * */
    public static float profit(float realIncome,float fb_cost,float logistics,float procurement){
        float result;
        if (realIncome > 0 && fb_cost > 0 && logistics > 0 && procurement >0){
            return result = Arith.floatSub(Arith.floatSub(Arith.floatSub(realIncome,fb_cost),logistics),procurement);
        }
        return 0;
    }

    /*
     * 单次成效
     * FB_cost FB支出
     * order_num 一个商品的订单数
     * */
    public  static float singleResult(float FB_cost,int order_num){
        float result;
        if (FB_cost > 0 && order_num > 0){
            return result = Arith.floatDiv(FB_cost,order_num);

        }
        return 0;
    }

    /*
     * 采购成本
     * fbIncome facebook收入
     * price 商品售价
     * */
    public float procurement(float fbIncome,float price){
        float result;
        if (fbIncome > 0 && price > 0) {

            return result = Arith.floatDiv(fbIncome,price);

        }
        return  0;
    }

    /*
     * 物流成本
     * order_num 订单数
     * single_logistics 物流成本
     * */
    public float logistics(float order_num,float single_logistics){
        float result;
        if (order_num > 0 && single_logistics > 0){
            return result = Arith.floatMul(order_num,single_logistics);
        }
        return 0;
    }


    /*
     * cpc
     * fb_cost Facebook支出
     * click 点击量
     * */
    public float cpc(float fb_cost,float click){
        float result;
        if (fb_cost > 0 && click >0){
            return result = Arith.floatDiv(fb_cost,click);
        }
        return 0;
    }

    /*
     * 同比
     * today_profit 今日盈亏
     * yestoday_profit 昨日盈亏
     * */
    public float increase(float today_profit,float yestoday_profit){
        float result;
        if (today_profit > 0 && yestoday_profit > 0){
            return result = Arith.floatSub(today_profit,yestoday_profit);
        }
        return 0;

    }
    /*
     * 推广成本
     * price 套餐售价
     * logistics 物流成本
     * */
    public static int spreadPay(float price,float logistics,float caigou){
        int result;
        if (price > 0 && logistics > 0){
            return result = (int) (Arith.floatSub(Arith.floatSub(price,logistics),caigou));
        }
        return 0;
    }


    /*
     * 真实推广成本
     * price 套餐售价
     * realspreadRate 真推率
     * logistics 物流成本
     * procurement 套餐成本（采购成本）
     * */
    public static int realspreadPay(float price,float realspreadRate,float logistics,float procurement){
        int result;
        if (price > 0 && realspreadRate > 0 && logistics > 0 && procurement  >0){
            return result = (int)(Arith.floatSub(Arith.floatSub(Arith.floatMul(price,realspreadRate),logistics),procurement));
        }
        return 0;
    }

    /*
     * 盈亏ROI
     * price 套餐售价
     * spreadpay 推广成本
     * */
    public static float profitRoi(float price,float spreadpay){
        float result;
        if (price > 0 && spreadpay > 0){
            return result = Arith.floatDiv(price,spreadpay);
        }
        return 0;
    }

}
