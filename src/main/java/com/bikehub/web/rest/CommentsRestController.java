package com.bikehub.web.rest;

import com.bikehub.model.dto.comment.CommentAddDTO;
import com.bikehub.model.dto.comment.CommentMessageDTO;
import com.bikehub.model.view.CommentViewModel;
import com.bikehub.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CommentsRestController {

    private final CommentService commentService;

    public CommentsRestController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping(value = "/{offerId}/comments", consumes = "application/json", produces = "application/json")
    public ResponseEntity<CommentViewModel> createComment(@PathVariable("offerId") Long offerId,
                                                          @AuthenticationPrincipal UserDetails user,
                                                          @RequestBody CommentMessageDTO commentMessageDTO) {

        CommentAddDTO commentAddDTO = new CommentAddDTO(user.getUsername(),
                offerId,
                commentMessageDTO.getMessage());

        CommentViewModel comment = commentService.createComment(commentAddDTO);

        return ResponseEntity
                .created(URI.create(String.format("/api/%d/comments/%d", offerId, comment.getId())))
                .body(comment);
    }

    @GetMapping("/{offerId}/comments")
    public ResponseEntity<List<CommentViewModel>> getAllComments(@PathVariable("offerId") Long offerId) {

        return ResponseEntity.ok(commentService.getAllCommentsForOffer(offerId));
    }
}