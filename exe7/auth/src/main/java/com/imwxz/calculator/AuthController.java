package com.imwxz.calculator;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public static JsonResult Login(@RequestParam(value = "username") String username,
                                   @RequestParam(value = "password") String password) {
        JsonResult ret = new JsonResult();
        if (username.equals("admin") && password.equals("123456")) {
            ret.setCode(0);
            ret.setMsg("success");
        } else {
            ret.setCode(1);
            ret.setMsg("wrong username or password");
        }
        return ret;
    }
}
