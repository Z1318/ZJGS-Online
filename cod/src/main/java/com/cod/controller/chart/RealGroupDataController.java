package com.cod.controller.chart;


import com.cod.controller.common.CommonController;
import com.cod.entity.chart.ChartPstatistics;
import com.cod.entity.manage.Func;
import com.cod.entity.manage.User;
import com.cod.enums.OrderStatusEnum;
import com.cod.service.ChartProductService;
import com.cod.service.FuncService;
import com.cod.service.MycountService;
import com.cod.service.RealGroupDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 投放商品，组，真实数据控制器
 */
@Controller
public class RealGroupDataController extends CommonController {

    @Autowired
    MycountService mycountService;

    @Autowired
    ChartProductService chartProductService;

    @Autowired
    RealGroupDataService realGroupDataService;

    @Autowired
    FuncService funcService;

    /**
     * 依据时间获取所有组的总体统计数据
     * @return
     */
    @RequestMapping(value = "/allgroup",method = RequestMethod.GET)
    public String allgroup(HttpServletRequest request,Model model){
        String team=request.getParameter("team");
        //获取当前登录用户
        User user=super.getUsername(request);
        if(team==null||team.equals("")){
            team=user.getBlgroup();
        }

        String launch_time=request.getParameter("launch_time");
        if(launch_time==null||launch_time.equals("")){
            launch_time=chartProductService.selectMaxDate(team);
        }

        //依据时间获取导入数据中的组
        List<String> groupNameList=chartProductService.getGroupNameByLaunchTime(launch_time);
        List<ChartPstatistics> chartPstatisticsList=realGroupDataService.getGroupData(groupNameList,launch_time);
        //获取当前的用户对应的角色，依据角色构造菜单
        List<Func> funcs=super.getFucByUserId(request);
        model.addAttribute("funcs",funcs);
        String currenturl="/allgroup";
        //依据url获取菜单名称
        Func func=funcService.getFuncByUrl(currenturl);
        model.addAttribute("currentname",func.getName());
        model.addAttribute("currenturl",currenturl);
        model.addAttribute("launch_time",launch_time);
        model.addAttribute("chartPstatisticsList",chartPstatisticsList);
        return "/cod/allgroup";
    }


    /**
     * 依据时间获取登录用户组的真实统计数据
     * @return
     */
    @RequestMapping(value = "/currentgroup",method = RequestMethod.GET)
    public String currentgroup(HttpServletRequest request,Model model){
        String team=request.getParameter("team");
        //获取当前登录用户
        User user=super.getUsername(request);
        if(team==null||team.equals("")){
            team=user.getBlgroup();
        }
        String launch_time=request.getParameter("launch_time");
        if(launch_time==null||launch_time.equals("")){
            launch_time=chartProductService.selectMaxDate(team);
        }

        //依据当前用户获取所拥有的数据权限
        List<String> grouplist=mycountService.getGroupsByUser(user.getId());
        List<ChartPstatistics> chartPstatisticsList=realGroupDataService.getProductData(user.getBlgroup(),launch_time);
        //获取当前的用户对应的角色，依据角色构造菜单
        List<Func> funcs=super.getFucByUserId(request);
        model.addAttribute("funcs",funcs);
        String currenturl="/currentgroup";
        //依据url获取菜单名称
        Func func=funcService.getFuncByUrl(currenturl);
        model.addAttribute("currentname",func.getName());
        model.addAttribute("currenturl",currenturl);
        model.addAttribute("grouplist",grouplist);
        model.addAttribute("team",team);
        model.addAttribute("launch_time",launch_time);
        model.addAttribute("chartPstatisticsList",chartPstatisticsList);
        return "/cod/groupdata";
    }

}
