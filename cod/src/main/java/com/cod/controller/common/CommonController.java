package com.cod.controller.common;

import com.cod.entity.manage.Func;
import com.cod.entity.manage.User;
import com.cod.service.FuncService;
import com.cod.service.MycountService;
import com.cod.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

public class CommonController {
    @Autowired
    MycountService mycountService;

    @Autowired
    RoleService roleService;

    @Autowired
    FuncService funcService;



    //获取当前登录的用户
    public User getUsername(HttpServletRequest request){
        //获取当前登录的用户
        HttpSession session = request.getSession(true);
        String username=(String) session.getAttribute("loginName");
        User user=mycountService.getMycountByUsername(username);
        return  user;
    }

    //依据当前登录的用户获取用户所对应的菜单
    public List<Func> getFucByUserId(HttpServletRequest request){
        User user=this.getUsername(request);
        //获取所有的菜单
        List<Func> allfuncs=funcService.getFuncs();
        if(user.getUsername().equals("admin")){
            //获取所有的父节点
            List<Func> funcList=new ArrayList<>(allfuncs);
            List<Func> permissionlist=new ArrayList<>();

            for(Func func:funcList){
                if(func.getParent_id()==-1){
                    permissionlist.add(func);
                }
            }
            Collections.sort(permissionlist);
            //递归遍历父节点
            for (Func func:permissionlist){
                this.buildTree(funcList,func);
            }
            return permissionlist;
        }
        //依据用户获取角色
        List<Integer> roles=roleService.getRoleidByUserid(user.getId());
        //依据角色获取菜单id
        List<Long> funcs=roleService.findByRoleIds(roles);
        Set<Func> permissionfunc=new HashSet<>();//存放权限内的集合
        //唯一的菜单id
        Set<Long> funcids=new HashSet<>();
        for (Long funcid:funcs){
            funcids.add(funcid);
        }
        //获取权限内的菜单
        for(Func func:allfuncs){
            if(funcids.contains(func.getId())){
                permissionfunc.add(func);
            }
        }
        //获取所有的父节点
        List<Func> funcList=new ArrayList<>(permissionfunc);
        List<Func> permissionlist=new ArrayList<>();

        for(Func func:funcList){
            if(func.getParent_id()==-1){
                permissionlist.add(func);
            }
        }
        Collections.sort(permissionlist);
        Collections.sort(funcList);
        //递归遍历父节点
        for (Func func:permissionlist){
                this.buildTree(funcList,func);
        }
        return permissionlist;
    }

    //递归构造菜单树
    private void buildTree(List<Func> treeNodeList, Func parent) {
        for(Func node:treeNodeList){
            if (node.getParent_id() == parent.getId()&&node.getParent_id()!=-1) {
                parent.getChildren().add(node);
                buildTree(treeNodeList, node);
            }
        }
    }



    /**
     * 选择要跳转的页面
     * @return
     */
    public String selectPage(HttpServletRequest request) {
        User user = this.getUsername(request);
        if (user.getUsername().equals("admin")) {
            return "/alluser";
        }
        //获取所有的菜单
        List<Func> allfuncs = funcService.getFuncs();
        //依据用户获取角色
        List<Integer> roles = roleService.getRoleidByUserid(user.getId());
        //依据角色获取菜单id
        List<Long> funcs = roleService.findByRoleIds(roles);
        Set<Func> permissionfunc = new HashSet<>();//存放权限内的集合
        //唯一的菜单id
        Set<Long> funcids = new HashSet<>();
        for (Long funcid : funcs) {
            funcids.add(funcid);
        }
        //获取权限内的菜单
        for (Func func : allfuncs) {
            if (funcids.contains(func.getId())) {
                permissionfunc.add(func);
            }
        }

        List<Func> funcList = new ArrayList<>(permissionfunc);
        List<Func> permissionlist = new ArrayList<>();

        for (Func func : funcList) {
            if (func.getParent_id() == -1) {
                permissionlist.add(func);
            }
        }
        Collections.sort(permissionlist);
        Collections.sort(funcList);
        String page = "";
        for (Func func : funcList) {
            if (!func.getUrl().equals("#")) {
                page = func.getUrl();
                break;
            }
        }
        return page;


    }
}
