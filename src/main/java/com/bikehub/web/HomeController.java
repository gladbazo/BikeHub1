package com.bikehub.web;

import com.bikehub.model.entity.User;
import com.bikehub.model.view.UserProfileView;
import com.bikehub.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class HomeController {

    private final UserService userService;

    public HomeController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String index() {
        return "index";
    }

    @GetMapping("/users/profile")
    public String profile(Principal principal, Model model) {

        String username = principal.getName();
        User user = userService.getUser(username);

        UserProfileView userProfileView = userService.userToProfileViewMap(user);

        model.addAttribute("user", userProfileView);

        return "profile";
    }
}
