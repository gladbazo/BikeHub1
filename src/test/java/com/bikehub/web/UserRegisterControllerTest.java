package com.bikehub.web;

import com.bikehub.model.dto.UserRegisterDTO;
import com.bikehub.service.UserService;
import com.bikehub.web.UserRegisterController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserRegisterControllerTest {

@Mock
private UserService userService;

@InjectMocks
private UserRegisterController userRegisterController;

@Mock
private Model model;

@Mock
private RedirectAttributes redirectAttributes;

@Mock
private BindingResult bindingResult;

@Test
public void testRegisterPage() {
        String viewName = userRegisterController.register();
        assertEquals("register-auth", viewName);
        }

@Test
public void testRegistrationSuccess() {
UserRegisterDTO userRegisterDTO = new UserRegisterDTO();
        when(bindingResult.hasErrors()).thenReturn(false);

        String viewName = userRegisterController.registerConfirm(userRegisterDTO, bindingResult, redirectAttributes);

        verify(userService).register(userRegisterDTO);
        assertEquals("redirect:login", viewName);
        }

@Test
public void testRegistrationFailure() {
UserRegisterDTO userRegisterDTO = new UserRegisterDTO();
        when(bindingResult.hasErrors()).thenReturn(true);

        String viewName = userRegisterController.registerConfirm(userRegisterDTO, bindingResult, redirectAttributes);

        verify(redirectAttributes).addFlashAttribute("userRegisterDTO", userRegisterDTO);
        verify(redirectAttributes).addFlashAttribute("org.springframework.validation.BindingResult.userRegisterDTO", bindingResult);
        assertEquals("redirect:register", viewName);
        }

@Test
public void testUserRegisterDTOModelAttribute() {
UserRegisterDTO userRegisterDTO = userRegisterController.userRegisterDTO();
        assertNotNull(userRegisterDTO);
        }
        }
