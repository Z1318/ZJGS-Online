package com.cod.controller;


import com.cod.controller.common.CommonController;
import com.cod.entity.manage.Func;
import com.cod.entity.manage.User;
import com.cod.service.MycountService;
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
import java.util.List;

@Controller
@RequestMapping(value = "/cod")
public class LoginController extends CommonController {

     @Autowired
     MycountService mycountService;

    /**
     * 登陆页面
     * @return
     */
    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public String login(HttpServletRequest request){
        //得到session对象
        HttpSession session = request.getSession(true);
        if(session.getAttribute("loginName")!=null){
            //登录成功,跳转到主页
            return "redirect:/cod/chartproduct";
        }
           //未登录跳转到登陆页
         return "/cod/login";
    }

    /**
     * 验证登陆
     * @return
     */
    @RequestMapping(value = "/login/dologin",method = RequestMethod.POST)
    public String dologin(HttpServletRequest request,@RequestParam(value = "username") String username,
                          @RequestParam(value = "password") String password,Model model){
        //判断登录是否成功
        User mycount=mycountService.getMycount(username,DigestUtils.md5DigestAsHex(password.getBytes()));
        //登陆成功
        if(mycount!=null){
            //创建session对象
            HttpSession session = request.getSession();
            //把用户数据保存在session域对象中
            session.setAttribute("loginName", mycount.getUsername());
            //判断调转到哪个页面
            String page=super.selectPage(request);
            return "redirect:"+page;
        }
        else{
            return "redirect:/cod/login";
        }

    }

   @RequestMapping(value="/main",method=RequestMethod.GET)
    public String main(HttpServletRequest request,Model model){
        //获取当前的用户
        HttpSession session = request.getSession(true);
        String student_id=(String) session.getAttribute("loginName");
        model.addAttribute("student_id",student_id);
        //获取当前的用户对应的角色，依据角色构造菜单
       List<Func> funcs=super.getFucByUserId(request);
       model.addAttribute("funcs",funcs);
       model.addAttribute("currenturl","/main");
       return "/cod/main";
    }

    /**
     * 退出登录
     * @return
     */
    @RequestMapping(value = "/quit",method = RequestMethod.GET)
    public String quit(HttpServletRequest request){
        request.getSession().removeAttribute("loginName");
        return "redirect:/cod/login";
    }

}
