package com.bikehub.service;

import com.bikehub.model.dto.UserRegisterDTO;
import com.bikehub.repository.UserRepository;
import com.bikehub.model.entity.User;
import com.bikehub.model.entity.UserRoleEntity;
import com.bikehub.model.enums.UserRoleEnum;
import com.bikehub.model.mapper.UserMapping;
import com.bikehub.model.view.UserProfileView;
import com.bikehub.repository.UserRoleRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapping userMapping;
    private final UserDetailsService userDetailsService;
    private final UserRoleRepository userRoleRepository;


    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, UserMapping userMapping, UserDetailsService userDetailsService, UserRoleRepository userRoleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapping = userMapping;
        this.userDetailsService = userDetailsService;

        this.userRoleRepository = userRoleRepository;
    }

    public void register(UserRegisterDTO userRegisterDTO) {
        User newUser = userMapping.userDtoToUser(userRegisterDTO);

        newUser.setPassword(passwordEncoder.encode(userRegisterDTO.getPassword()));
        UserRoleEntity userRoleEntity=new UserRoleEntity();
        userRoleEntity.setUserRole(UserRoleEnum.USER);
        newUser.setUserRoles(List.of(userRoleEntity));
        newUser = userRepository.save(newUser);
        userRoleRepository.save(userRoleEntity);
        login(newUser);
    }

    private void login(User user) {

        UserDetails userDetails =
                userDetailsService.loadUserByUsername(user.getEmail());

        Authentication auth =
                new UsernamePasswordAuthenticationToken(
                        userDetails,
                        userDetails.getPassword(),
                        userDetails.getAuthorities()
                );

        SecurityContextHolder.
                getContext().
                setAuthentication(auth);
    }

    public User getUser(String username) {

        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username + " was not found!"));
    }

    public UserProfileView userToProfileViewMap(User user) {

        return userMapping.userToProfileView(user);
    }

    public long getRegisteredUsersCount() {

        return userRepository.count();
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public boolean existByUsername(String username) {
        return userRepository.findByUsername(username).isPresent();
    }

    public void makeUserAdmin(String username) {

        User user = this.userRepository.findByUsername(username).get();

        UserRoleEntity userRoleEntity = new UserRoleEntity();
        userRoleEntity.setUserRole(UserRoleEnum.ADMIN);

        user.getUserRoles().add(userRoleEntity);
        userRepository.save(user);
    }

    public void removeAdminRole(String username) {

        User user = this.userRepository.findByUsername(username).get();

        if (user.getId() != 1) {

            UserRoleEntity userRoleEntity = new UserRoleEntity();
            userRoleEntity.setUserRole(UserRoleEnum.ADMIN);

            user.getUserRoles().remove(userRoleEntity);
            userRepository.save(user);
        }
    }

    @Transactional
    public boolean changeUsername(String currentUsername, String newUsername, String password) {
        Optional<User> optionalUser = userRepository.findByUsername(currentUsername);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            // Authenticate user by verifying the password
            if (!passwordEncoder.matches(password, user.getPassword())) {
                return false; // Incorrect password
            }

            // Check if the new username already exists
            Optional<User> existingUserWithNewUsername = userRepository.findByUsername(newUsername);
            if (existingUserWithNewUsername.isPresent()) {
                return false; // New username already exists
            }

            // Set the new username
            user.setUsername(newUsername);
            userRepository.save(user);
            return true; // Username changed successfully
        }

        return false; // User with currentUsername not found
    }

}
