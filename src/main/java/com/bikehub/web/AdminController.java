package com.bikehub.web;

import com.bikehub.model.enums.UserRoleEnum;
import com.bikehub.model.view.UserProfileView;
import com.bikehub.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class AdminController {

    private final UserService userService;
    private final ModelMapper modelMapper;

    public AdminController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/admin/manage-users")
    public String adminPanel(Model model) {

        List<UserProfileView> usersAllViewModels = userService.findAllUsers()
                .stream()
                .map(usersAllServiceModel -> this.modelMapper.map(usersAllServiceModel, UserProfileView.class))
                .collect(Collectors.toList());

        model.addAttribute("allUsers", usersAllViewModels);
        model.addAttribute("admin", UserRoleEnum.ADMIN);

        return "admin-panel";
    }

    @PatchMapping("/admin/manage-users/add-admin")
    public String makeAdmin(@RequestParam String username) {
        boolean isUserExist = userService.existByUsername(username);

        if (isUserExist) {
            userService.makeUserAdmin(username);
        }

        return "redirect:/admin/manage-users";
    }

    @PatchMapping("/admin/manage-users/remove-admin")
    public String removeAdmin(@RequestParam String username) {

        boolean isUserExist = userService.existByUsername(username);

        if (isUserExist) {
            userService.removeAdminRole(username);
        }

        return "redirect:/admin/manage-users";
    }
}
