package com.cod.controller.chart;


import com.cod.controller.common.CommonController;
import com.cod.entity.chart.ChartPstatistics;
import com.cod.entity.manage.Func;
import com.cod.entity.manage.User;
import com.cod.service.ChartProductService;
import com.cod.service.FuncService;
import com.cod.service.GroupDataService;
import com.cod.service.MycountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class GroupDataController extends CommonController {
    @Autowired
    GroupDataService groupDataService;

     @Autowired
    MycountService mycountService;

     @Autowired
    ChartProductService chartProductService;

     @Autowired
    FuncService funcService;
    /**
     * 当前组数据控制器
     * @return
     */
    @RequestMapping(value = "/deliverydata",method = RequestMethod.GET)
    public String groupData(HttpServletRequest request,Model model){
        String launch_time=request.getParameter("launch_time");
        String team=request.getParameter("team");
        //获取当前登录用户
        User user=super.getUsername(request);
        if(team==null||team.equals("")){
            team=user.getBlgroup();
        }
        if(launch_time==null||launch_time.equals("")){
            launch_time=chartProductService.selectMaxDate(team);
        }

        //依据当前用户获取所拥有的数据权限
        List<String> grouplist=mycountService.getGroupsByUser(user.getId());

        //依据组名和时间获取最终数据
        List<ChartPstatistics> chartPstatisticsList= groupDataService.getGroupPstatisticsData(launch_time,team);

        model.addAttribute("grouplist",grouplist);
        model.addAttribute("launch_time",launch_time);
        model.addAttribute("team",team);
        model.addAttribute("chartPstatistics",chartPstatisticsList);

        //获取当前的用户对应的角色，依据角色构造菜单
        List<Func> funcs=super.getFucByUserId(request);
        model.addAttribute("funcs",funcs);
        String currenturl="/deliverydata";
        //依据url获取菜单名称
        Func func=funcService.getFuncByUrl(currenturl);
        model.addAttribute("currentname",func.getName());
        model.addAttribute("currenturl",currenturl);
        return "/cod/deliverydata";
    }
}
