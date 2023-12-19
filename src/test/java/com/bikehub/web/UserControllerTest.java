package com.bikehub.web;

import com.bikehub.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testChangeUsername_Successful() {
        // Mocking userService.changeUsername() to return true for a successful username change
        when(userService.changeUsername(anyString(), anyString(), anyString())).thenReturn(true);

        ResponseEntity<String> response = userController.changeUsername("currentUsername", "newUsername", "password");

        // Verify if userService.changeUsername() is called with the correct arguments
        verify(userService, times(1)).changeUsername("currentUsername", "newUsername", "password");

        // Check if the response status code is OK (200)
        assertEquals(200, response.getStatusCodeValue());

        // Check the response message
        assertEquals("Username changed successfully", response.getBody());
    }

    @Test
    public void testChangeUsername_Failure() {
        // Mocking userService.changeUsername() to return false for a failed username change
        when(userService.changeUsername(anyString(), anyString(), anyString())).thenReturn(false);

        ResponseEntity<String> response = userController.changeUsername("currentUsername", "newUsername", "password");

        // Verify if userService.changeUsername() is called with the correct arguments
        verify(userService, times(1)).changeUsername("currentUsername", "newUsername", "password");

        // Check if the response status code is Bad Request (400)
        assertEquals(400, response.getStatusCodeValue());

        // Check the response message
        assertEquals("Failed to change username", response.getBody());
    }
}

