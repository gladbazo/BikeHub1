package com.bikehub.service;

import com.bikehub.model.entity.User;
import com.bikehub.model.entity.UserRoleEntity;
import com.bikehub.model.enums.UserRoleEnum;
import com.bikehub.model.user.BikehubUserDetails;
import com.bikehub.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.TestPropertySource;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
public class BikehubUserDetailsServiceTest {

    @Mock
    private UserRepository mockUserRepository;

    private BikehubUserDetailsService toTest;

    @BeforeEach
    public void setUp() {
        toTest = new BikehubUserDetailsService(
                mockUserRepository
        );
    }

    @Test
    void testLoadUserByUsername_UserExists() {

        User testUserEntity =
                new User();

        List<UserRoleEntity> userRoleEntities = new ArrayList<>();
        UserRoleEntity testUserRole = new UserRoleEntity();
        UserRoleEntity testAdminRole = new UserRoleEntity();
        testUserRole.setUserRole(UserRoleEnum.USER);
        testAdminRole.setUserRole(UserRoleEnum.ADMIN);

        userRoleEntities.add(testUserRole);
        userRoleEntities.add(testAdminRole);

        testUserEntity.setUsername("petko");
        testUserEntity.setEmail("pesho@example.com");
        testUserEntity.setPassword("topsecret");
        testUserEntity.setFirstName("Pesho");
        testUserEntity.setLastName("Petrov");
        testUserEntity.setActive(true);
        testUserEntity.setUserRoles(userRoleEntities);

        when(mockUserRepository.findByUsername(testUserEntity.getUsername())).
                thenReturn(Optional.of(testUserEntity));

        BikehubUserDetails userDetails = (BikehubUserDetails)
                toTest.loadUserByUsername(testUserEntity.getUsername());

        Assertions.assertEquals(testUserEntity.getUsername(), userDetails.getUsername());

        Assertions.assertEquals(testUserEntity.getUsername(),
                userDetails.getUsername());

        Assertions.assertEquals(testUserEntity.getFirstName(),
                userDetails.getFirstName());

        Assertions.assertEquals(testUserEntity.getLastName(),
                userDetails.getLastName());

        Assertions.assertEquals(testUserEntity.getPassword(),
                userDetails.getPassword());

        var authorities = userDetails.getAuthorities();

        Assertions.assertEquals(2, authorities.size());

        var authoritiesIter = authorities.iterator();

        Assertions.assertEquals("ROLE_" + UserRoleEnum.USER.name(),
                authoritiesIter.next().getAuthority());

        Assertions.assertEquals("ROLE_" + UserRoleEnum.ADMIN.name(),
                authoritiesIter.next().getAuthority());
    }

    @Test
    public void testLoadUserByUsername_UserNotFound() {
        // Call the method being tested and assert for UsernameNotFoundException
        assertThrows(UsernameNotFoundException.class, () -> toTest.loadUserByUsername("nonExistingUser"));
    }
    @Test
    public void testMap() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        // Create a mock User entity
        User userEntity = new User();
        userEntity.setUsername("testUser");
        userEntity.setPassword("testPassword");
        userEntity.setFirstName("John");
        userEntity.setLastName("Doe");

        // Mock UserRoleEntity
        UserRoleEntity userRoleEntity = new UserRoleEntity();
        userRoleEntity.setUserRole(UserRoleEnum.ADMIN); // Or any other role
        userEntity.setUserRoles(Collections.singletonList(userRoleEntity));
        Method mapMethod = BikehubUserDetailsService.class.getDeclaredMethod("map", User.class);
        mapMethod.setAccessible(true);

        UserDetails userDetails = (UserDetails) mapMethod.invoke(toTest, userEntity);

        // Assertions
        assertEquals("testUser", userDetails.getUsername());
        assertEquals("testPassword", userDetails.getPassword());
        // Add more assertions for first name, last name, and authorities
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
        assertEquals(1, authorities.size());
        assertEquals("ROLE_ADMIN", authorities.iterator().next().getAuthority());
    }
}
