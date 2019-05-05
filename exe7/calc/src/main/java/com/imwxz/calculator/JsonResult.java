package com.imwxz.calculator;

public class JsonResult {
    private int code = -1;       // -1 uninit, 0 normal, 1 empty exp, 2 ans error, 3 exp error
    private String ans = "";     // answer
    private String msg = "";     // error msg

    public String getAns() {
        return ans;
    }

    public void setAns(String ans) {
        this.ans = ans;
    }

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
