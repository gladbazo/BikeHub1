package com.bikehub.dto;

import com.bikehub.model.dto.UserRegisterDTO;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserRegisterDTOTest {

    @Test
    public void testSettersAndGetters() {
        UserRegisterDTO userDTO = new UserRegisterDTO();

        // Set values using setters
        userDTO.setUsername("john_doe");
        userDTO.setFirstName("John");
        userDTO.setLastName("Doe");
        userDTO.setEmail("john@example.com");
        userDTO.setAge(25);
        userDTO.setPassword("password");
        userDTO.setConfirmPassword("password");

        // Validate values retrieved using getters
        assertEquals("john_doe", userDTO.getUsername());
        assertEquals("John", userDTO.getFirstName());
        assertEquals("Doe", userDTO.getLastName());
        assertEquals("john@example.com", userDTO.getEmail());
        assertEquals(25, userDTO.getAge());
        assertEquals("password", userDTO.getPassword());
        assertEquals("password", userDTO.getConfirmPassword());
    }
}
