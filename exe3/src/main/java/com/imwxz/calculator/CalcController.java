package com.imwxz.calculator;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class CalcController {
    @RequestMapping(value = "/calc", method = RequestMethod.POST)
    public static JsonResult Calc(@RequestParam(value = "expression", defaultValue = "") String expression,
                                  @RequestParam(value = "ans", defaultValue = "0") String ans) {
        JsonResult ret = new JsonResult();
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
        return ret;
    }
}
