package com.cod.controller.chart;

import com.cod.controller.common.CommonController;
import com.cod.dao.ChartProductDao;
import com.cod.entity.chart.ChartProduct;
import com.cod.entity.manage.Func;
import com.cod.entity.manage.User;
import com.cod.service.ChartProductService;
import com.cod.service.FuncService;
import com.cod.service.MycountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 报表初始数据控制器
 */
@Controller
public class ChartProductController extends CommonController {
     @Autowired
     ChartProductService chartProductService;
     @Autowired
     MycountService mycountService;

     @Autowired
     FuncService funcService;

    /**
     * 展示初始导入数据
     * @param request
     * @param model
     * @return
     */
     @RequestMapping(value = "/chartproduct",method = RequestMethod.GET)
     public String chartProduct(HttpServletRequest request,Model model){
         //依据当前用户查询出所在组投放的产品
         User user=super.getUsername(request);
         String team=user.getBlgroup();
         String newtime=request.getParameter("sTime");
         //获取最近的导入时间
         if(newtime==null||newtime.equals("")){
             newtime=chartProductService.selectMaxDate(team);
         }
         //查询所在组的最新初始数据
         List<ChartProduct> chartProductList=chartProductService.getProductByTeamAndLaunchTime(team,newtime);
         model.addAttribute("chartproducts",chartProductList);
         List<Func> funcs=super.getFucByUserId(request);
         model.addAttribute("funcs",funcs);
         model.addAttribute("newtime",newtime);
         String currenturl="/chartproduct";
         //依据url获取菜单名称
         Func func=funcService.getFuncByUrl(currenturl);
         model.addAttribute("currentname",func.getName());
         model.addAttribute("currenturl",currenturl);
         return "/cod/chartproduct";
     }


    /**
     * 导入excel,csv文件
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/channelchartProduct",method = RequestMethod.POST)
    public String channelExcelProduct(@RequestParam(value="filename") MultipartFile file,
                                      HttpServletRequest request, HttpServletResponse response){
        User user=super.getUsername(request);
        //判断文件是否为空
        if(file==null) return null;
        //获取文件名
        String name=file.getOriginalFilename();
        String fileType = name.substring(name.lastIndexOf(".")+1);
        //进一步判断文件是否为空（即判断其大小是否为0或其名称是否为null）
        long size=file.getSize();
        if(name==null || ("").equals(name) && size==0) return null;
        //批量导入
        String flag = chartProductService.readExcelAndCsv(name,file,user.getBlgroup(),fileType);
        return flag;
    }



    /**
     *
     * by DL
     * 依据id修改产品
     */
    @RequestMapping(value="reviseproduct",method = RequestMethod.POST)
    public  String revise(HttpServletRequest request){
        String id=request.getParameter("id");
        String team=request.getParameter("team");
        String name=request.getParameter("name");
        String sku = request.getParameter("sku");
        String person = request.getParameter("person");
        String fb_cost=request.getParameter("fb_cost");
        String fb_income=request.getParameter("fb_income");
        String order_num=request.getParameter("order_num");
        String cart_num=request.getParameter("cart_num");
        String show_num=request.getParameter("show_num");
        String site_conversion = request.getParameter("site_conversion");
        String singleResult = request.getParameter("singleResult");
        String singleFree = request.getParameter("singleFree");
        String launch_time=request.getParameter("launch_time");
        ChartProduct chartProduct=new ChartProduct();
        chartProduct.setId(Long.parseLong(id));
        chartProduct.setTeam(team);
        chartProduct.setName(name);
        chartProduct.setSku(sku);
        chartProduct.setPerson(person);
        chartProduct.setFb_cost(Float.parseFloat(fb_cost));
        chartProduct.setFb_income(Float.parseFloat(fb_income));
        chartProduct.setOrder_num(Integer.parseInt(order_num));
        chartProduct.setCart_num(Integer.parseInt(cart_num));
        chartProduct.setShow_num(Integer.parseInt(show_num));
        chartProduct.setSite_conversion(Integer.parseInt(site_conversion));
        chartProduct.setSingleResult(Float.parseFloat(singleResult));
        chartProduct.setSingleFree(Float.parseFloat(singleFree));
        chartProduct.setLaunch_time(launch_time);
        chartProductService.updateProduct(chartProduct);
        return "redirect:/chartroduct";
    }
}
