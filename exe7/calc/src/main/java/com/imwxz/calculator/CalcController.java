package com.imwxz.calculator;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLConnection;

@RestController
public class CalcController {
    private static boolean Login(String url, String param) throws Exception {
        PrintWriter out = null;
        BufferedReader in = null;
        StringBuilder result = new StringBuilder();
        try {
            URL realUrl = new URL(url);
            URLConnection conn = realUrl.openConnection();
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(10 * 1000);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            out = new PrintWriter(conn.getOutputStream());
            out.print(param);
            out.flush();
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null)
                result.append(line);
        } finally {
            if (out != null) out.close();
            if (in != null) in.close();
        }
        return result.toString().contains("success");
    }

    @RequestMapping(value = "/calc", method = RequestMethod.POST)
    public static JsonResult Calc(@RequestParam(value = "expression", defaultValue = "") String expression,
                                  @RequestParam(value = "ans", defaultValue = "0") String ans,
                                  @RequestParam("username") String username,
                                  @RequestParam("password") String password) throws Exception {
        String param = "username=" + username + "&password=" + password;
        JsonResult ret = new JsonResult();
        if (Login("http://localhost:1926/login", param)) {
            if (expression.isEmpty()) {
                ret.setCode(1);
                ret.setMsg("Empty expression!");
            } else {
                try {
                    BigDecimal tmp = new BigDecimal(ans);
                    Calculator calc = new Calculator();
                    ret.setAns(calc.expression(expression, tmp));
                    ret.setCode(0);
                    ret.setMsg("OK");
                } catch (NumberFormatException e) {
                    ret.setCode(2);
                    ret.setMsg("Ans format error!");
                } catch (CalcError e) {
                    ret.setCode(3);
                    ret.setMsg("Expression format error!");
                }
            }
        } else {
            ret.setCode(4);
            ret.setMsg("Wrong username or password!");
        }
        return ret;
    }
}
