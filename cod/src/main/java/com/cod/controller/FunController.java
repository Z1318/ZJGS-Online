package com.cod.controller;


import com.cod.controller.common.CommonController;
import com.cod.entity.manage.Func;
import com.cod.service.FuncService;
import com.cod.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Author zyp
 */
@Controller
public class FunController extends CommonController {

    @Autowired
    FuncService funcService;
    @Autowired
    RoleService roleService;
    /**
     * 所有菜单的页面
     * @return
     */
    @RequestMapping(value="/allfunc",method = RequestMethod.GET)
    public String allfunc(HttpServletRequest request,Model model){
        //获取所有的菜单
        List<Func> funcList=funcService.getFuncs();
        model.addAttribute("allfuncs",funcList);
        //获取当前的用户对应的角色，依据角色构造菜单
        List<Func> funcs=super.getFucByUserId(request);
        model.addAttribute("funcs",funcs);

        String currenturl="/allfunc";
        //依据url获取菜单名称
        Func func=funcService.getFuncByUrl(currenturl);
        model.addAttribute("currentname",func.getName());
        model.addAttribute("currenturl",currenturl);
        return "/cod/allfunc";
    }

    /**
     * 添加菜单
     * @return
     */
    @RequestMapping(value="/addfunc",method = RequestMethod.POST)
    public String addFunc(HttpServletRequest request){
        String name=request.getParameter("funcname");
        String url=request.getParameter("url");
        String parent_id=request.getParameter("parent_id");
        String icon=request.getParameter("icon");
        Func func=new Func();
        func.setName(name);
        func.setParent_id(Integer.parseInt(parent_id));
        func.setUrl(url);
        func.setIcon(icon);
        funcService.insertData(func);
        return "redirect:/allfunc";

    }

    /**
     * 依据id修改菜单
     */
    @RequestMapping(value="revisefunc",method = RequestMethod.POST)
    public  String revise(HttpServletRequest request){
        String id=request.getParameter("id");
        String name=request.getParameter("funcname");
        String url=request.getParameter("url");
        String parent_id=request.getParameter("parent_id");
        String icon=request.getParameter("icon");
        Func func=new Func();
        func.setName(name);
        func.setParent_id(Integer.parseInt(parent_id));
        func.setUrl(url);
        func.setIcon(icon);
        func.setId(Long.parseLong(id));
        funcService.updateFunc(func);
        return "redirect:/allfunc";
    }


    /**
     * 删除菜单
     * @return
     */
    @RequestMapping(value = "/deletefunc")
    public String deleteFunc(HttpServletRequest request){
        String id=request.getParameter("id");
        //依据id删除func
        funcService.deleteFuncById(Integer.parseInt(id));
        //依据菜单id删除角色菜单对应关系
        roleService.deleteRoleFuncByFuncid(Integer.parseInt(id));
        return "redirect:/allfunc";
    }

}
