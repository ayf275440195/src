package com.ayf.springboot_jwt_login.service;

import com.alibaba.fastjson.JSONObject;
import com.ayf.springboot_jwt_login.utils.JwtUtil;
import com.ayf.springboot_jwt_login.utils.Result;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class LoginService {


    public Object loginIn(JSONObject param) {
        Object o = null;
        if (Objects.equals("tom",param.getString("userName"))){
            o = JwtUtil.createToken(param);
            return Result.OK(o);
        }
        return Result.ERR("登录失败");
    }
}
