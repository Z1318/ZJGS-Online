package com.cod.controller;




import com.cod.common.page.PagingResult;
import com.cod.controller.common.CommonController;
import com.cod.dto.RoleDto;
import com.cod.dto.UserDto;
import com.cod.entity.manage.*;
import com.cod.service.FuncService;
import com.cod.service.MycountService;
import com.cod.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import sun.security.provider.MD5;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;


@Controller
public class UserController extends CommonController {
   @Autowired
    MycountService mycountService;
   @Autowired
    RoleService roleService;

    @Autowired
    FuncService funcService;

    /**
     * 所有用户的页
     * @return
     */
    @RequestMapping(value="/alluser",method = RequestMethod.GET)
    public String allUser(HttpServletRequest request,Model model,
                          @RequestParam(value = "page", defaultValue = "1") Integer page,
                          @RequestParam(value = "size", defaultValue = "10") Integer size){
        //获取所有的用户
        PagingResult<UserDto> pageusers= mycountService.getAllMycount(page,size,"/alluser");
        //获取所有的角色
        List<RoleDto> roles=roleService.getRoles();
        //获取所有数据组
        List<GroupDto> groupNames = mycountService.getAllGroup();
        model.addAttribute("roles",roles);
        model.addAttribute("users",pageusers);
        model.addAttribute("groupNames",groupNames);//所有组别

        //获取当前的用户对应的角色，依据角色构造菜单
        List<Func> funcs=super.getFucByUserId(request);
        model.addAttribute("funcs",funcs);
        String currenturl="/alluser";
        //依据url获取菜单名称
        Func func=funcService.getFuncByUrl(currenturl);
        model.addAttribute("currentname",func.getName());
        model.addAttribute("currenturl",currenturl);
        return "/cod/alluser";
    }

    /**
     * 添加用户
     * @return
     */
    @RequestMapping(value="/adduser",method = RequestMethod.POST)
    public String addFunc(HttpServletRequest request){
        String username=request.getParameter("username");
        String password=request.getParameter("password");
        String phone=request.getParameter("phone");
        User user=new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setPhone(phone);
        mycountService.insertMycount(user);
        return "redirect:/alluser";

    }



    /**
     *添加用户角色对应关系
     */
    @RequestMapping(value = "/addroleid",method=RequestMethod.POST)
    public String addroleid(HttpServletRequest request){
        String userid=request.getParameter("user_id");//用户id
        String rolesid[]=request.getParameterValues("rolesid");//角色id
        if(rolesid!=null){
            //依据用户id将角色删除
            roleService.deleteUserRole(Integer.parseInt(userid));
            List<User_Role> user_roles=new ArrayList<>();//存放数据的集合
            //将用户角色对应关系插入数据库
            for (String roleid:rolesid){     //for循环是为了将用户已选择的角色一一对应
                User_Role user_role=new User_Role();
                user_role.setRole_id(Integer.parseInt(roleid));
                user_role.setUser_id(Integer.parseInt(userid));
                user_roles.add(user_role);
            }
            roleService.insertUserRole(user_roles);

        }
        return "redirect:/alluser";
    }

    /*
     *添加用户数据对应关系
     */

    @RequestMapping(value = "/addgrouppower",method=RequestMethod.POST)
    public String addgrouppower(HttpServletRequest request){
        String userid=request.getParameter("user_id");//用户id
        String groups[]=request.getParameterValues("groupname");//角色id
        if(groups!=null){
            //依据用户id将数据删除
            mycountService.deleteUserGroup(Integer.parseInt(userid));
            List<User_Group> user_groups=new ArrayList<>();//存放数据的集合
            //将用户数据对应关系插入数据库
            for (String group:groups){     //for循环是为了将用户已选择的数据一一对应
                User_Group user_group=new User_Group();
                user_group.setGroupName(group);
                user_group.setUser_id(Integer.parseInt(userid));
                user_groups.add(user_group);
            }
            mycountService.insertUserGroup(user_groups);
        }


        return "redirect:/alluser";
    }



    /**
     * 修改密码页面
     * @return
     */
    @RequestMapping(value = "/revisepage",method = RequestMethod.GET)
    public String revisepage(HttpServletRequest request,Model model){
        List<Func> funcs=super.getFucByUserId(request);
        model.addAttribute("funcs",funcs);
        String currenturl="/revisepage";
        //依据url获取菜单名称
        Func func=funcService.getFuncByUrl(currenturl);
        model.addAttribute("currentname",func.getName());
        return "/cod/revise";

    }


    @RequestMapping(value = "/revisePassword",method = RequestMethod.POST)
    public String updatePassword(HttpServletRequest request,Model model){
        String password = request.getParameter("oldpassword");
        String newpassword = request.getParameter("password");
        String md5password = null;
        //获取当前登录用户
        User user=super.getUsername(request);
        md5password = DigestUtils.md5DigestAsHex(password.getBytes());
        int i = 0;
        if (md5password.equals(user.getPassword())){
            i=mycountService.updatePassById(user.getId(),DigestUtils.md5DigestAsHex(newpassword.getBytes()));
            if (i != 0){
                return "redirect:/cod/quit";
            }
        }
        return "redirect:/revisepage";
    }


}
