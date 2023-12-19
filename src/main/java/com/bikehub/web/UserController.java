package com.bikehub.web;

import com.bikehub.model.entity.User;
import com.bikehub.repository.UserRepository;
import com.bikehub.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private  UserService userService;

    @PutMapping("/{currentUsername}/change-username")
    public ResponseEntity<String> changeUsername(
            @PathVariable("currentUsername") String currentUsername,
            @RequestParam("newUsername") String newUsername,
            @RequestParam("password") String password) {

        boolean usernameChanged = userService.changeUsername(currentUsername, newUsername, password);

        if (usernameChanged) {
            return ResponseEntity.ok("Username changed successfully");
        } else {
            return ResponseEntity.badRequest().body("Failed to change username");
        }
    }
}




