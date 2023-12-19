package com.bikehub.web;

import com.bikehub.model.enums.UserRoleEnum;
import com.bikehub.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import java.util.Collections;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AdminControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private Model model;

    @InjectMocks
    private AdminController adminController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAdminPanel() {
        // Mocking userService.findAllUsers() to return an empty list for simplicity
        when(userService.findAllUsers()).thenReturn(Collections.emptyList());

        String viewName = adminController.adminPanel(model);

        // Check if the view name returned matches the expected view name
        assertEquals("admin-panel", viewName);

        // Verify if userService.findAllUsers() is called once
        verify(userService, times(1)).findAllUsers();

        // Verify if model.addAttribute() is called with the expected attributes
        verify(model, times(1)).addAttribute(eq("allUsers"), anyList());
        verify(model, times(1)).addAttribute(eq("admin"), eq(UserRoleEnum.ADMIN));
    }
}

