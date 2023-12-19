package com.bikehub.repository;

import com.bikehub.model.entity.Comment;
import com.bikehub.model.entity.Offer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    Optional<List<Comment>> findAllByOfferId(Long offerId);
    Optional<List<Comment>> findAllByOffer(Offer offer);
}
