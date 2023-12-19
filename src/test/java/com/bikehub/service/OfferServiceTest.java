package com.bikehub.service;

import com.bikehub.model.dto.offer.AddOfferDTO;
import com.bikehub.model.dto.offer.OfferDetailsDTO;
import com.bikehub.model.entity.Category;
import com.bikehub.model.entity.Offer;
import com.bikehub.model.entity.User;
import com.bikehub.model.enums.CategoryNameEnum;
import com.bikehub.model.user.BikehubUserDetails;
import com.bikehub.repository.*;
import com.bikehub.util.TestDataUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class OfferServiceTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private OfferRepository offerRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BikehubUserDetailsService userDetailsService;

    @Autowired
    private CategoryRepository categoryRepository;

    private ModelMapper modelMapper;
    @Autowired
    private UserRoleRepository userRoleRepository;
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private TestDataUtils testDataUtils;

    @InjectMocks
    private OfferService offerService;

    private Offer testOffer;

    private User user;

    @BeforeEach
    void setUp() {

        modelMapper = new ModelMapper();

        offerService = new OfferService(offerRepository, userRepository, categoryRepository, modelMapper,userRoleRepository,commentRepository);

        user = testDataUtils.createTestUser("petko");

        testOffer = testDataUtils.createTestOffer(user);

        offerRepository.save(testOffer);
    }

    @AfterEach
    void tearDown() {
        testDataUtils.cleanUpDatabase();
    }


    @Test
    void findOfferByName() {

        List<OfferDetailsDTO> offerByName = offerService.findOfferByName(testOffer.getName());

        Assertions.assertEquals(1, offerByName.size());
    }

    @Test
    void addOfferTest() {

        BikehubUserDetails user = new BikehubUserDetails("123", "petko", "petar", "petkov", Collections.emptyList());

        Category category = new Category();
        category.setCategory(CategoryNameEnum.MANUAL);
        categoryRepository.save(category);

        AddOfferDTO offer = new AddOfferDTO();
        offer.setImageUrl("test");
        offer.setName("akva");
        offer.setYear(2000);
        offer.setMileage(3000);
        offer.setPrice(BigDecimal.valueOf(50));

        offerService.addOffer(offer, user);
        offerRepository.save(testOffer);

        Assertions.assertFalse(offerRepository.findAll().isEmpty());
        offerRepository.delete(testOffer);
    }


    @Test
    void findOfferByIDTest() {

        Offer offer = new Offer();
        offer.setImageUrl("test");
        offer.setName("akva");
        offer.setYear(2000);
        offer.setMileage(3000);
        offer.setPrice(BigDecimal.valueOf(50));


        offerRepository.save(offer);

        OfferDetailsDTO offerDetailsDTO = offerService.findOfferById(2L);

        Assertions.assertEquals("akva", offerDetailsDTO.getName());
        offerRepository.delete(offer);
    }

    @Test
    void isOwnerTest() {

        boolean isOwnerTest = offerService.isOwner("petko", 1L);

        Assertions.assertTrue(isOwnerTest);
    }

    @Test
    void findCount() {

        long count = offerService.getAllListedOffersCount();

        Assertions.assertEquals(1, count);
    }

    @Test
    void deleteTest() {
        Offer offer = new Offer();
        offer.setImageUrl("test");
        offer.setName("akva");
        offer.setYear(2000);
        offer.setMileage(3000);
        offer.setPrice(BigDecimal.valueOf(50));
        offerRepository.save(offer);



        offerService.deleteOfferById(offer.getId());

        Assertions.assertEquals(1, offerRepository.count());
    }
}
