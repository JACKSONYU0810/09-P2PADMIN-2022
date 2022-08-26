package com.jackson.p2padmin2022.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @RequestMapping("/login")
    public String login(){
        return "login";
    }

}
