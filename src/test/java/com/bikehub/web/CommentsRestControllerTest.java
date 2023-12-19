package com.bikehub.web;

import com.bikehub.model.dto.comment.CommentAddDTO;
import com.bikehub.model.dto.comment.CommentMessageDTO;
import com.bikehub.model.view.CommentViewModel;
import com.bikehub.service.CommentService;
import com.bikehub.web.rest.CommentsRestController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CommentsRestControllerTest {

    @Mock
    private CommentService commentService;

    @InjectMocks
    private CommentsRestController commentsRestController;

    @Test
    public void testCreateComment() {
        // Mocked data
        Long offerId = 1L;
        UserDetails userDetails = new User("testUser", "password", new ArrayList<>());
        CommentMessageDTO commentMessageDTO = new CommentMessageDTO("Test comment message");

        // Mock behavior of commentService
        CommentViewModel mockedComment = new CommentViewModel(); // Create a mocked CommentViewModel
        when(commentService.createComment(any(CommentAddDTO.class))).thenReturn(mockedComment);

        // Call the method being tested
        ResponseEntity<CommentViewModel> responseEntity = commentsRestController.createComment(offerId, userDetails, commentMessageDTO);

        // Assertions
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(mockedComment, responseEntity.getBody());

        verify(commentService, times(1)).createComment(any(CommentAddDTO.class)); // Verify commentService.createComment() is called
    }


    @Test
    public void testGetAllComments() {
        // Mocked data
        Long offerId = 1L;
        List<CommentViewModel> mockedCommentList = new ArrayList<>(); // Create a mocked list of CommentViewModel

        // Mock behavior of commentService
        when(commentService.getAllCommentsForOffer(offerId)).thenReturn(mockedCommentList);

        // Call the method being tested
        ResponseEntity<List<CommentViewModel>> responseEntity = commentsRestController.getAllComments(offerId);

        // Assertions
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(mockedCommentList, responseEntity.getBody());

        verify(commentService, times(1)).getAllCommentsForOffer(offerId); // Verify commentService.getAllCommentsForOffer() is called
    }
}

