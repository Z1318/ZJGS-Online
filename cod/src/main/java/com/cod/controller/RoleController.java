package com.cod.controller;


import com.cod.controller.common.CommonController;
import com.cod.dto.RoleDto;
import com.cod.entity.manage.Func;
import com.cod.entity.manage.Role;
import com.cod.entity.manage.Role_Func;
import com.cod.service.FuncService;
import com.cod.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author  董磊
 */
@Controller
public class RoleController extends CommonController {
   @Autowired
    RoleService roleService;
   @Autowired
    FuncService funcService;


    /**
     * 所有角色的页面
     * @return
     */
    @RequestMapping(value="/allrole",method = RequestMethod.GET)
    public String allRole(HttpServletRequest request,Model model){
        List<RoleDto> list=roleService.getRoles();
        //获取所有的func
        List<Func> allfuncs=funcService.getFuncs();
        model.addAttribute("allfuncs",allfuncs);
        model.addAttribute("roles",list);
        //获取当前的用户对应的角色，依据角色构造菜单
        List<Func> funcs=super.getFucByUserId(request);
        model.addAttribute("funcs",funcs);
        String currenturl="/allrole";
        //依据url获取菜单名称
        Func func=funcService.getFuncByUrl(currenturl);
        model.addAttribute("currentname",func.getName());
        model.addAttribute("currenturl",currenturl);
        return "/cod/allrole";
    }

    /**
     * 添加角色
     * @return
     */
    @RequestMapping(value="/addrole",method = RequestMethod.POST)
    public String addRole(HttpServletRequest request){
        String name=request.getParameter("rolename");
        Role role=new Role();
        role.setName(name);
        roleService.insertData(role);
        return "redirect:/allrole";
    }

    /**
     * 依据id修改角色
     */
    @RequestMapping(value="/reviserole",method = RequestMethod.POST)
    public  String reviseRole(HttpServletRequest request){
        String id=request.getParameter("id");
        String name=request.getParameter("rolename");
        Role role=new Role();
        role.setName(name);
        role.setId(Long.parseLong(id));
        roleService.updateRole(role);
        return "redirect:/allrole";
    }

    /**
     * 依据角色id 获取
     */
    @RequestMapping(value="/getFuncByRole",method = RequestMethod.GET)
    @ResponseBody
    public List<Integer> getFunc(HttpServletRequest request){
        String roleid=request.getParameter("roleid");
        //依据 roleid获取funcid
        List<Integer> list=roleService.getFuncidByroleid(Integer.parseInt(roleid));
        return list;
    }


    /**
     * 依据role_id 设置func_id
     * @return
     */
    @RequestMapping(value="/refuncrole",method = RequestMethod.POST)
    public String reviseFuncByRoleId(HttpServletRequest request){
        String roleid=request.getParameter("roleid");
        String funcid[]=request.getParameterValues("funcid");
        return "redirect:/allrole";

    }

    /**
     *插入角色菜单对应关系
     */
    @RequestMapping(value = "/addfuncid",method=RequestMethod.POST)
    public String addroleid(HttpServletRequest request){
        String role_id=request.getParameter("roleid");//用户id
        String funcids[]=request.getParameterValues("funcid");//角色id
        if(funcids!=null){
            //依据用户id将角色删除
            roleService.deleteRoleFunc(Integer.parseInt(role_id));
            List<Role_Func> role_funcs=new ArrayList<>();
            //将用户角色对应关系插入数据库
            for (String funcid:funcids){
                Role_Func role_func=new Role_Func();
                role_func.setRole_id(Integer.parseInt(role_id));
                role_func.setFunc_id(Integer.parseInt(funcid));
                role_funcs.add(role_func);
            }
            roleService.insertRoleFunc(role_funcs);

        }
        return "redirect:/allrole";
    }




}
