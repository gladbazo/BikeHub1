package com.bikehub.service;


import com.bikehub.model.dto.comment.CommentAddDTO;
import com.bikehub.model.entity.Comment;
import com.bikehub.model.entity.Offer;
import com.bikehub.model.entity.User;
import com.bikehub.model.view.CommentViewModel;
import com.bikehub.repository.CommentRepository;
import com.bikehub.repository.OfferRepository;
import com.bikehub.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.time.LocalDateTime;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class CommentServiceTest {

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private OfferRepository offerRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CommentService commentService;

    @Test
    public void testCreateComment() {
        MockitoAnnotations.initMocks(this);

        // Mocking UserRepository findByUsername to return a user for testing purposes
        User testUser = new User(); // Create a mock user object for testing
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(testUser));

        // Mocking OfferRepository getById to return an offer for testing purposes
        Offer testOffer = new Offer(); // Create a mock offer object for testing
        when(offerRepository.getById(anyLong())).thenReturn(testOffer);

        // Mocking CommentRepository save method
        when(commentRepository.save(any(Comment.class))).thenAnswer(invocation -> {
            Comment comment = invocation.getArgument(0);
            comment.setId(1L); // Simulating saving and setting an ID for the comment
            return comment;
        });

        // Test data for CommentAddDTO
        CommentAddDTO testCommentAddDTO = new CommentAddDTO();
        testCommentAddDTO.setUsername("testUsername");
        testCommentAddDTO.setOfferId(1L);
        testCommentAddDTO.setMessage("Test comment message");

        CommentViewModel commentViewModel = commentService.createComment(testCommentAddDTO);

        // Verify if UserRepository findByUsername is called
        verify(userRepository, times(1)).findByUsername("testUsername");

        // Verify if OfferRepository getById is called
        verify(offerRepository, times(1)).getById(1L);

        // Verify if CommentRepository save is called
        verify(commentRepository, times(1)).save(any(Comment.class));

        // Verify CommentViewModel attributes
        assertEquals(1L, commentViewModel.getId()); // Assuming ID is set to 1 in the save mock
        assertEquals("Test comment message", commentViewModel.getMessage());
    }
}
