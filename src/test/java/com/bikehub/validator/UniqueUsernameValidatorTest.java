package com.bikehub.validator;

import com.bikehub.model.entity.User;
import com.bikehub.model.validator.UniqueUsernameValidator;
import com.bikehub.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

public class UniqueUsernameValidatorTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UniqueUsernameValidator uniqueUsernameValidator;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testIsValid_UniqueUsername() {
        // Mocking UserRepository findByUsername to return an empty Optional for a unique username
        when(userRepository.findByUsername("uniqueUsername")).thenReturn(Optional.empty());

        // Check if isValid returns true for a unique username
        boolean result = uniqueUsernameValidator.isValid("uniqueUsername", null);

        assertTrue(result);
    }

    @Test
    public void testIsValid_NonUniqueUsername() {
        // Mocking UserRepository findByUsername to return a non-empty Optional for a non-unique username
        when(userRepository.findByUsername("existingUsername")).thenReturn(Optional.of(new User()));

        // Check if isValid returns false for a non-unique username
        boolean result = uniqueUsernameValidator.isValid("existingUsername", null);

        assertFalse(result);
    }
}

