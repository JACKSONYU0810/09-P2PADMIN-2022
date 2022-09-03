package com.jackson.p2padmin2022.user.controller;

import com.jackson.p2padmin2022.user.model.UserInfo;
import com.jackson.p2padmin2022.user.service.UserService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * ClassName: UserController
 * Package: com.jackson.p2padmin2022.user.controller
 * Description:
 *
 * @Date: 8/26/2022 5:55 PM
 * @Author: JacksonYu
 */
@Controller
public class UserController {

    @Resource
    private UserService userService;

    @RequestMapping("/")
    public String toLogin(HttpServletRequest request,Model model,HttpServletResponse response,String isAutoLogin){

        Cookie[] cookies = request.getCookies();
        String admin = null;
        String password = null;
        if (cookies==null || cookies.length<2){
            return "login";
        }

        for (Cookie cookie : cookies) {
            if ("username".equals(cookie.getName())){
                admin = cookie.getValue();
                continue;
            }

            if ("password".equals(cookie.getName())){
                password = cookie.getValue();
                continue;
            }
        }

        if (admin != null && password != null){
            UserInfo userInfo = new UserInfo();
            userInfo.setUsername(admin);
            userInfo.setPassword(password);
            userInfo = userService.login(userInfo);

            if (ObjectUtils.allNull(userInfo)){
                model.addAttribute("message", "账户密码不匹配");

                return "login";
            }

            request.getSession().setAttribute("userInfo", userInfo);

            Cookie usernameCookie = new Cookie("username",userInfo.getUsername());
            Cookie passwordCookie = new Cookie("password",userInfo.getPassword());

            usernameCookie.setPath("/");
            passwordCookie.setPath("/");

            usernameCookie.setMaxAge(60*60*24*30);
            passwordCookie.setMaxAge(60*60*24*30);

            response.addCookie(usernameCookie);
            response.addCookie(passwordCookie);

        }

        return "index";
    }

    @RequestMapping("/index")
    public String index(){

        return "index";
    }

    @RequestMapping("/login")
    public String login(UserInfo userInfo, String isAutoLogin, Model model, HttpServletRequest request, HttpServletResponse response){


        userInfo = userService.login(userInfo);

        if (ObjectUtils.allNull(userInfo)){
            model.addAttribute("message", "账户密码不匹配");

            return "login";
        }

        request.getSession().setAttribute("userInfo", userInfo);

        if ("yes".equals(isAutoLogin)){
            Cookie usernameCookie = new Cookie("username",userInfo.getUsername());
            Cookie passwordCookie = new Cookie("password",userInfo.getPassword());

            usernameCookie.setPath("/");
            passwordCookie.setPath("/");

            usernameCookie.setMaxAge(60*60*24*30);
            passwordCookie.setMaxAge(60*60*24*30);

            response.addCookie(usernameCookie);
            response.addCookie(passwordCookie);

        }

        return "redirect:index";
    }

    @RequestMapping("/logout")
    public String logout(HttpServletRequest request,HttpServletResponse response){
        request.getSession().invalidate();
        Cookie usernameCookie = new Cookie("username",null);
        Cookie passwordCookie = new Cookie("password",null);

        usernameCookie.setPath("/");
        passwordCookie.setPath("/");

        usernameCookie.setMaxAge(60*60*24*30);
        passwordCookie.setMaxAge(60*60*24*30);

        response.addCookie(usernameCookie);
        response.addCookie(passwordCookie);


        return "login";
    }


    @RequestMapping("/noPermission")
    public String noPermission(Model model){
        model.addAttribute("errorMessage", "对不起，您没有权限");
        model.addAttribute("helpMessage", "请联系管理员");
        return "error";
    }

}
