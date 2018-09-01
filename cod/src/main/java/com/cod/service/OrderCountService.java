package com.cod.service;

import com.cod.dao.*;
import com.cod.entity.Exchangerate;

import com.cod.entity.chart.OrderCount;
import com.cod.entity.tmp.OrderCountInfo;
import com.cod.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @ Author     ：ZYP
 * @ Date       ：Created in 17:32 2018/6/6
 * @ Description：${description}
 * @ Modified By：
 * @Version: $version$
 */
@Service
public class OrderCountService {

    @Autowired
    OrderCountDao orderCountDao;
    @Autowired
    UserDao userDao;

    ArrayList<OrderCount> orderCounts = new ArrayList<>();

    /**
     * create by:ZYP
     * description:判断字符串是否是数字字符串
     * create time: 18:43 2018/6/13
     *
     * @Param: null
     * @return
     */
    public final static boolean isNumeric(String str) {
        if (str != null && !"".equals(str.trim()))
            return str.matches("^[0-9]*$");
        else
            return false;
    }

    /**
     * create by:ZYP
     * description: 获取全部组的 订单信息
     * create time: 16:29 2018/6/14
     *
     * @return a
     * @Param: null
    */
    public List<OrderCount> getOrderCountInfo(String trueDate){

        int totalNum = 0;         //各组总订单数
        float totalAmount = 0;   //各组总金额
        int realOderNum = 0;    // 各组 真实单数
        float realIncome = 0;   // 各组 真实收入
        float realRate = 0;     // 各组 真单率

        int allTotalNum = 0;      // 所有组 总计订单统计
        float allTotalAmout = 0; // 所有组 总计 总金额
        int allRealOderNum = 0;    // 所有组 真实单数
        float allRealIncome = 0;   // 所有组 真实收入
        float allRealRate = 0;     // 总计真单率

        // 创建 返回的对象list
        ArrayList<OrderCount> orderCounts = new ArrayList<>();
        //参数集合
        TreeMap<String, Object> argsMap = new TreeMap<>();
        //获取日期的后一天
        String specifiedDayAfter = DateUtil.getSpecifiedDayAfter(trueDate);

        argsMap.put("date",trueDate);
        argsMap.put("specifiedDayAfter",specifiedDayAfter);

        // 获取当天所有的 OrderCountInfo  payprice,status,glgroup,汇率
        List<OrderCountInfo> orderCountInfo = orderCountDao.getOrderCountInfo(argsMap);

        OrderCount allOrderCount = new OrderCount();
        //获取所有组
        List<String> allGroup = userDao.getAllGroup();
        //计算每个组的信息
        for (int i = 0; i < allGroup.size() ; i++) {
            OrderCount orderCount = new OrderCount();
            //客服组不参与投放
            if(allGroup.get(i).equals("客服组")){
                continue;
            }
            //遍历该天数据
            for (int j = 0; j < orderCountInfo.size(); j++) {
                //获取 该组 订单信息 ,并判断订单价格是否为数字字符串
                if(allGroup.get(i).equals(orderCountInfo.get(j).getGroupName()) && isNumeric(orderCountInfo.get(j).getPayPrice())){
                    totalNum++;//总单数累计
                    //总计金额 = payprice * 汇率
                    totalAmount += Float.parseFloat(orderCountInfo.get(j).getPayPrice())*Float.parseFloat(orderCountInfo.get(j).getExchangerate());
                    //订单状态为 2，3，4 为真实单
                    if(orderCountInfo.get(j).getStatus() == 2 || orderCountInfo.get(j).getStatus() == 3 || orderCountInfo.get(j).getStatus() == 4){
                        realOderNum++;//真实单数 累计
                        realIncome += Float.parseFloat(orderCountInfo.get(j).getPayPrice())*Float.parseFloat(orderCountInfo.get(j).getExchangerate());//有效收入累计
                    }
                }
            }
            //计算 该组真实率
            DecimalFormat df  = new DecimalFormat("###.000");//小数保留3位
            if(totalNum != 0){
                realRate = Float.parseFloat(df.format(((float)realOderNum/totalNum)));
            }else {
                realRate = 0;
            }
            orderCount.setGroupName(allGroup.get(i));//组名
            orderCount.setTotalNum(totalNum);// 总计单数
            orderCount.setTotalAmount(bigNumber((float)totalAmount));// 总计金额
            orderCount.setRealOder(realOderNum);//真实单
            orderCount.setRealIncome(bigNumber((float) realIncome));// 有效收入（真实收入）
            orderCount.setRealRate(realRate*100);   //真实率
            orderCount.setDate(trueDate);//日期

            // 各组对象加入list
            orderCounts.add(orderCount);

            //总计  计算
            allTotalNum = orderCountInfo.size();//当天总单数
            allTotalAmout += totalAmount; // 总计金额
            allRealOderNum += realOderNum; // 总计有效单
            allRealIncome += realIncome;  // 总计有效收入
            //计算 总计真实率
            if(allTotalNum != 0){
                allRealRate = Float.parseFloat(df.format(((float)allRealOderNum/allTotalNum)));
            }else {
                allRealRate = 0;
            }

            // 初始化
            totalNum = 0;         //各组总订单数
            totalAmount = 0;   //各组总金额
            realOderNum = 0;    // 各组 真实单数
            realIncome = 0;   // 各组 真实收入
            realRate = 0;    // 真实率
        }
        allOrderCount.setGroupName("总计");//组名
        allOrderCount.setTotalNum(allTotalNum);// 总计单数
        allOrderCount.setTotalAmount(bigNumber((float)allTotalAmout));// 总计金额
        allOrderCount.setRealOder(allRealOderNum);//真实单
        allOrderCount.setRealIncome(bigNumber((float)allRealIncome));// 有效收入（真实收入）
        allOrderCount.setRealRate(allRealRate*100);   //真实率
        allOrderCount.setDate(trueDate);//日期
        // 总计对象加入list
        orderCounts.add(allOrderCount);
        // 返回全部订单信息
        return orderCounts;
    }

    /**
     * create by:ZYP
     * description: 大数据 不以 科学计数法显示
     * create time: 11:20 2018/6/15
     *
     * @Param: null
     * @return
     */
    private static String bigNumber(float d) {
        BigDecimal d1 = new BigDecimal(Float.toString(d));
        BigDecimal d2 = new BigDecimal(Integer.toString(1));
        // 四舍五入,保留2位小数
        return d1.divide(d2,2,BigDecimal.ROUND_HALF_UP).toString();
    }
}
