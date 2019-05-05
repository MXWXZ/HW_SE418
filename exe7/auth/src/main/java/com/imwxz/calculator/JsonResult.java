package com.imwxz.calculator;

public class JsonResult {
    private int code = -1;       // 0 ok, 1 username/password wrong
    private String msg = "";     // error msg

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

}
