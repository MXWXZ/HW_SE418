package com.imwxz.calculator;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.handler;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CalculatorApplicationTests {
    @Autowired
    private MockMvc mockMvc;

    private void performPost(String expression, String ans, int suc_code, String suc_ans) throws Exception {
        mockMvc.perform(post("/calc")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .param("expression", expression)
                .param("ans", ans))
                .andExpect(handler().handlerType(CalcController.class))
                .andExpect(handler().methodName("Calc"))
                .andExpect(jsonPath("$.code").value(suc_code))
                .andExpect(jsonPath("$.ans").value(suc_ans));
    }

    @Test
    public void contextLoads() throws Exception {
        performPost("ANS", "0", 0, "0");
        performPost("ANS*2", "0", 0, "0");
        performPost("1+(-3)", "0", 0, "-2");
        performPost("-5!", "0", 0, "-120");
        performPost("11*12/(1+(1/2))+5*(6-4)*31", "0", 0, "398");
        performPost("5!", "0", 0, "120");
        performPost("+3", "0", 0, "3");
        performPost("ANS!!", "3", 0, "720");
        performPost("3", "0", 0, "3");
        performPost("-ANS!!-5%2*(2*2-0!)!!", "3", 0, "-1440");
        performPost("-1-1440", "0", 0, "-1441");
        performPost("3*ANS-ANS", "-1441", 0, "-2882");
        performPost("78+3*0!", "0", 0, "81");
        performPost("0.98729/2", "0", 0, "0.49365");
        performPost("128%42", "0", 0, "2");
        performPost("-3", "0", 0, "-3");

        // error
        performPost("1+4+5*-3", "0", 3, "");
        performPost("q3", "0", 3, "");
        performPost("(-5)!", "0", 3, "");
        performPost("(-5)!", "0", 3, "");
        performPost("", "0", 1, "");
        performPost("1", "3s", 2, "");
    }

}
