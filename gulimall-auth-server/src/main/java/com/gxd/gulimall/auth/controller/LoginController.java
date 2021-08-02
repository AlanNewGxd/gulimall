package com.gxd.gulimall.auth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author guxiaodong
 * @version 1.0
 * @title 登录控制类
 * @date 2021/8/2 14:56
 */
@Controller
public class LoginController {

    @GetMapping("login.html")
    public String loginPage(){
        return "login";
    }

    @GetMapping("reg.html")
    public String regPage(){
        return "reg";
    }

}
