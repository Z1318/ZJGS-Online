package com.cod.controller.chart;

import com.cod.controller.common.CommonController;
import com.cod.dao.OrderCountDao;
import com.cod.dao.OrderDao;
import com.cod.entity.chart.OrderCount;
import com.cod.entity.manage.Func;
import com.cod.service.ChartProductService;
import com.cod.service.FuncService;
import com.cod.service.MycountService;
import com.cod.service.OrderCountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 报表订单统计控制器
 */
@Controller
public class OrderCountController extends CommonController {
     @Autowired
     ChartProductService chartProductService;
     @Autowired
     MycountService mycountService;
     @Autowired
     OrderCountService orderCountService;
     @Autowired
     OrderCountDao orderCountDao;
     @Autowired
    FuncService funcService;
    /**
     * create by:DL
     * @param request
     * @param model
     * @return
     */

    @RequestMapping(value = "/allorder",method={RequestMethod.POST, RequestMethod.GET})
     public String chartProduct(HttpServletRequest request,Model model){
         //依据当前用户查询出所在组投放的产品
         HttpSession session = request.getSession(true);
         String username=(String) session.getAttribute("loginName");
        //查询最新初始数据
        String newtime=request.getParameter("sTime");
        //日期为空则默认获取数据库中值最大的日期
        if(newtime == null || newtime.length() <= 0) {
            // 获取日期最大值
            String maxDate = orderCountDao.getMaxDate();
            newtime = maxDate.split(" ")[0];
        }

         List<OrderCount> groupOrders = orderCountService.getOrderCountInfo(newtime);
         model.addAttribute("OrderCount",groupOrders);
         List<Func> funcs=super.getFucByUserId(request);
         model.addAttribute("funcs",funcs);
         model.addAttribute("newtime",newtime);
        String currenturl="/allorder";
        //依据url获取菜单名称
        Func func=funcService.getFuncByUrl(currenturl);
        model.addAttribute("currentname",func.getName());
        model.addAttribute("currenturl",currenturl);
         return "/cod/allorder";

     }

}
