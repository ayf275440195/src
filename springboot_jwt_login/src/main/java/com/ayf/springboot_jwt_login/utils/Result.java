package com.ayf.springboot_jwt_login.utils;

import java.io.Serializable;


public class Result implements Serializable {
    private static final String OK = "success";
    private static final String ERR = "err";

    private String code = "success";

    private Object data ;

    public static Result OK(Object data){

        return new Result(OK,data);
    }
    public static Result ERR(Object data){
        return new Result(ERR,data);
    }

    public Result(String code, Object data) {
        this.code = code;
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
