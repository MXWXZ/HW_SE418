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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CalculatorApplicationTests {
    @Autowired
    private MockMvc mockMvc;

    private void performPost(String username, String password, int suc_code) throws Exception {
        mockMvc.perform(post("/login")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .param("username", username)
                .param("password", password))
                .andExpect(handler().handlerType(AuthController.class))
                .andExpect(handler().methodName("Login"))
                .andExpect(jsonPath("$.code").value(suc_code));
    }

    // test login
    @Test
    public void login() throws Exception {
        performPost("admin", "123456", 0);
        performPost("admin", "wrong", 1);
    }

    @Test
    public void loginFail() throws Exception {
        mockMvc.perform(post("/login")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .param("username", "admin"))
                //.param("password", password))
                .andExpect(status().isBadRequest());
        mockMvc.perform(post("/login")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                //.param("username", "admin"))
                .param("password", "123456"))
                .andExpect(status().isBadRequest());
        mockMvc.perform(post("/login")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                //.param("username", "admin"))
                //.param("password", "123456"))
                .andExpect(status().isBadRequest());
    }
}
