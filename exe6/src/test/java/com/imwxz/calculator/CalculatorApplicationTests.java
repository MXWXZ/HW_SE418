package com.imwxz.calculator;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.logout;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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

    // user login
    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void roleUser() throws Exception {
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

        // mainpage
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello user!"));
    }

    // admin login
    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void roleAdmin() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello admin!"));
    }

    // not login
    @Test
    public void roleNone() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(content().string("Go /login to login!"));
        mockMvc.perform(post("/calc")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .param("expression", "1")
                .param("ans", "0"))
                .andExpect(status().is3xxRedirection());
    }

    // test login
    @Test
    public void login() throws Exception {
        mockMvc.perform(formLogin("/login").user("admin").password("invalid"))
                .andExpect(unauthenticated());
        mockMvc.perform(formLogin("/login").user("user").password("invalid"))
                .andExpect(unauthenticated());


        mockMvc.perform(formLogin("/login").user("admin").password("12345678"))
                .andExpect(authenticated());
        mockMvc.perform(formLogin("/login").user("user").password("123456"))
                .andExpect(authenticated());
    }

    @Test
    public void logoutFail() throws Exception {
        mockMvc.perform(logout("/logout"))
                .andExpect(unauthenticated());
    }
}
