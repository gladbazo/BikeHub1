package com.bikehub.web;

import com.bikehub.model.entity.User;
import com.bikehub.model.view.UserProfileView;
import com.bikehub.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

import java.security.Principal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class HomeControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private Model model;

    @InjectMocks
    private HomeController homeController;

    @Test
    public void testProfile() {
        String username = "testUser";
        User mockedUser = new User(); // Create a User instance or mock as needed

        // Mock behavior of Principal and UserService
        Principal mockPrincipal = () -> username;
        when(userService.getUser(username)).thenReturn(mockedUser);

        // Call the method being tested
        String result = homeController.profile(mockPrincipal, model);

        // Verify interactions and assertions
        assertEquals("profile", result); // Check if the correct view name is returned

        verify(userService, times(1)).getUser(username); // Verify userService.getUser() is called once with the correct username
    }


}
