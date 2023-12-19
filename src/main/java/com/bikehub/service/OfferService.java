package com.bikehub.service;

import com.bikehub.model.dto.offer.AddOfferDTO;
import com.bikehub.model.dto.offer.OfferDetailsDTO;
import com.bikehub.model.entity.Comment;
import com.bikehub.repository.*;
import com.bikehub.model.entity.Category;
import com.bikehub.model.entity.Offer;
import com.bikehub.model.entity.User;
import com.bikehub.model.enums.UserRoleEnum;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OfferService {

    private final OfferRepository offerRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;
    private final UserRoleRepository userRoleRepository;
    private final CommentRepository commentRepository;

    public OfferService(OfferRepository offerRepository, UserRepository userRepository, CategoryRepository categoryRepository, ModelMapper modelMapper, UserRoleRepository userRoleRepository, CommentRepository commentRepository) {
        this.offerRepository = offerRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
        this.userRoleRepository = userRoleRepository;
        this.commentRepository = commentRepository;
    }

    public void addOffer(AddOfferDTO addOfferDTO, UserDetails userDetails) {

        Offer newOffer = modelMapper.map(addOfferDTO, Offer.class);
        Category category = categoryRepository.findByCategory(addOfferDTO.getCategory()).orElse(null);

        User user = userRepository
                .findByUsername(userDetails.getUsername())
                .orElse(null);

        newOffer.setPostedBy(user);
        newOffer.setCategory(category);
        offerRepository.save(newOffer);
    }

    public Page<OfferDetailsDTO> getAllOffers(Pageable pageable) {

        return offerRepository.findAll(pageable)
                .map(offer -> modelMapper.map(offer, OfferDetailsDTO.class));
    }

    public List<OfferDetailsDTO> findOfferByName(String name) {

        List<Offer> filtered = offerRepository.findAll().stream().filter(e -> e.getName().equalsIgnoreCase(name)).toList();

        return filtered.stream().map(offer -> modelMapper.map(offer, OfferDetailsDTO.class)).toList();
    }

    public OfferDetailsDTO findOfferById(Long id) {

        return offerRepository.findById(id)
                .map(offer -> modelMapper.map(offer, OfferDetailsDTO.class))
                .orElse(null);
    }

    public boolean isOwner(String username, Long id) {

        boolean isOwner = offerRepository.
                findById(id).
                filter(o -> o.getPostedBy().getUsername().equals(username)).
                isPresent();

        if (isOwner) {
            return true;
        }

        return userRepository.
                findByUsername(username).
                filter(this::isAdmin).
                isPresent();
    }

    private boolean isAdmin(User user) {

        return user.getUserRoles().
                stream().
                anyMatch(r -> r.getUserRole() == UserRoleEnum.ADMIN);
    }

    public long getAllListedOffersCount() {

        return offerRepository.count();
    }

    public void deleteOfferById(Long offerId) {
        Optional<List<Comment>> comments = commentRepository.findAllByOfferId(offerId);
        comments.ifPresent(commentList -> commentList.forEach((comment) -> {
            commentRepository.deleteById(comment.getId());
        }));
        //userRoleRepository.deleteById(offerId);
        offerRepository.deleteById(offerId);
    }
}
