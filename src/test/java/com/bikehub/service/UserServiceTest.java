package com.bikehub.service;

import com.bikehub.model.dto.UserRegisterDTO;
import com.bikehub.model.entity.User;
import com.bikehub.model.entity.UserRoleEntity;
import com.bikehub.model.enums.UserRoleEnum;
import com.bikehub.model.view.UserProfileView;
import com.bikehub.repository.UserRepository;
import com.bikehub.repository.UserRoleRepository;
import com.bikehub.model.mapper.UserMapping;
import com.bikehub.util.TestDataUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserServiceTest {

    @Autowired
    private UserRepository mockUserRepository;

    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserMapping userMapping;

    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private UserRoleRepository userRoleRepository;


    @Autowired
    private TestDataUtils testDataUtils;

    User user;

    @BeforeEach
    void setUp() {

        user = new User();
        user.setUsername("petko");
        user.setEmail("pesho@example.com");
        user.setPassword("topsecret");
        user.setFirstName("Pesho");
        user.setLastName("Petrov");
        user.setAge(18);

        UserRoleEntity userRoleEntity = new UserRoleEntity();
        userRoleEntity.setUserRole(UserRoleEnum.ADMIN);
        user.setUserRoles(List.of(userRoleEntity));
        mockUserRepository.save(user);


        userService = new UserService(mockUserRepository, passwordEncoder, userMapping, userDetailsService,userRoleRepository);
    }

    @AfterEach
    void tearDown() {
        testDataUtils.cleanUpDatabase();
    }

    @Test
    void getUserTest() {

        User userGet = userService.getUser("petko");

        Assertions.assertEquals("petko", userGet.getUsername());
    }

    @Test
    void findAllUsersTest() {

        List<User> userList = userService.findAllUsers();

        Assertions.assertEquals(1, userList.size());
    }

    @Test
    void getRegisteredUsersCountTest() {

        long count = userService.getRegisteredUsersCount();

        Assertions.assertEquals(1, count);
    }

    @Test
    void existByUsernameTEst() {

        boolean exist = userService.existByUsername("petko");

        Assertions.assertTrue(exist);
    }

    @Test
    void testUserProfileView() {

        UserProfileView userProfileView =
                userService.userToProfileViewMap(user);
        Assertions.assertEquals("petko", userProfileView.getUsername());
    }

    @Test
    void testMakeAdmin() {

        userService.makeUserAdmin("petko");

        Assertions.assertEquals(UserRoleEnum.ADMIN, user.getUserRoles().get(0).getUserRole());
    }

    @Test
    void testRemoveAdmin() {

        userService.removeAdminRole("petko");

        Assertions.assertEquals(UserRoleEnum.ADMIN, user.getUserRoles().get(0).getUserRole());
    }
}



