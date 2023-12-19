package com.bikehub.dto;

import com.bikehub.model.dto.comment.CommentAddDTO;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommentAddDTOTest {

    @Test
    public void testSettersAndGetters() {
        // Create an instance of CommentAddDTO
        CommentAddDTO commentAddDTO = new CommentAddDTO();

        // Set values using setters
        commentAddDTO.setUsername("JohnDoe");
        commentAddDTO.setOfferId(1L);
        commentAddDTO.setMessage("This is a test comment.");

        // Validate values retrieved using getters
        assertEquals("JohnDoe", commentAddDTO.getUsername());
        assertEquals(1L, commentAddDTO.getOfferId());
        assertEquals("This is a test comment.", commentAddDTO.getMessage());
    }
}

