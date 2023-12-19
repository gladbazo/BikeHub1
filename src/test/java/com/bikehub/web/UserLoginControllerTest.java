package com.bikehub.web;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@SpringBootTest
@AutoConfigureMockMvc
public class UserLoginControllerTest {

@Mock
private UsernamePasswordAuthenticationFilter usernamePasswordAuthenticationFilter;

@InjectMocks
private UserLoginController userLoginController;
@Autowired
private MockMvc mockMvc;



@Test
public void testLogin() throws Exception {
        mockMvc.perform(get("/users/login"))
        .andExpect(status().isOk())
        .andExpect(view().name("login"));
        }

@Test
public void testOnFailedLogin() throws Exception {
        mockMvc.perform(post("/users/login")).
                andExpect(status().is4xxClientError());
        }
}
