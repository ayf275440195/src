package com.ayf.springboot_jwt_login.controller;

import com.alibaba.fastjson.JSONObject;
import com.ayf.springboot_jwt_login.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LoginController {

    @Autowired
    private LoginService loginService ;

    @RequestMapping("/login")
    @ResponseBody
    public Object logigIn(@RequestBody JSONObject param){
        return loginService.loginIn(param);
    }
}
