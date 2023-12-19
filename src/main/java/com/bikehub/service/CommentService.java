package com.bikehub.service;

import com.bikehub.repository.CommentRepository;
import com.bikehub.repository.OfferRepository;
import com.bikehub.repository.UserRepository;
import com.bikehub.model.dto.comment.CommentAddDTO;
import com.bikehub.model.entity.Comment;
import com.bikehub.model.entity.Offer;
import com.bikehub.model.entity.User;
import com.bikehub.model.view.CommentViewModel;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final OfferRepository offerRepository;
    private final UserRepository userRepository;

    public CommentService(CommentRepository commentRepository, OfferRepository offerRepository, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.offerRepository = offerRepository;
        this.userRepository = userRepository;
    }

    public CommentViewModel createComment(CommentAddDTO commentAddDTO) {

        User author = userRepository.findByUsername(commentAddDTO.getUsername()).get();

        Comment comment = new Comment();
        comment.setCreated(LocalDateTime.now());
        comment.setOffer(offerRepository.getById(commentAddDTO.getOfferId()));
        comment.setAuthor(author);
        comment.setApproved(true); //todo admin panel
        comment.setText(commentAddDTO.getMessage());

        commentRepository.save(comment);

        return new CommentViewModel(comment.getId(), author.getUsername(), comment.getText());
    }

    public List<CommentViewModel> getAllCommentsForOffer(Long offerId) {

        Offer offer = offerRepository.findById(offerId).orElseThrow(); //todo maybe check error workshop

        List<Comment> comments = commentRepository.findAllByOffer(offer).get();

        return comments.stream()
                .map(comment -> new CommentViewModel(comment.getId(), comment.getAuthor().getUsername(), comment.getText()))
                .collect(Collectors.toList());
    }
}
