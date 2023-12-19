package com.bikehub.util;

import com.bikehub.model.dto.offer.OfferDetailsDTO;
import com.bikehub.model.entity.Category;
import com.bikehub.model.entity.Offer;
import com.bikehub.model.entity.User;
import com.bikehub.model.entity.UserRoleEntity;
import com.bikehub.model.enums.CategoryNameEnum;
import com.bikehub.model.enums.UserRoleEnum;
import com.bikehub.repository.CategoryRepository;
import com.bikehub.repository.OfferRepository;
import com.bikehub.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Component
public class TestDataUtils {

    private final UserRepository userRepository;
    private final OfferRepository offerRepository;
    private final CategoryRepository categoryRepository;

    public TestDataUtils(UserRepository userRepository, OfferRepository offerRepository, CategoryRepository categoryRepository) {
        this.userRepository = userRepository;
        this.offerRepository = offerRepository;
        this.categoryRepository = categoryRepository;
    }

//    private void initRoles() {
//
//        if (userRoleRepository.count() == 0) {
//            UserRoleEntity userRole = new UserRoleEntity();
//            userRole.setUserRole(UserRoleEnum.USER);
//
//            UserRoleEntity adminRole = new UserRoleEntity();
//            adminRole.setUserRole(UserRoleEnum.ADMIN);
//
//            userRoleRepository.save(adminRole);
//            userRoleRepository.save(userRole);
//        }
//    }

    public User createTestAdmin(String username) {

//        initRoles();

        var admin = new User();

        admin.setUsername(username);
        admin.setEmail("pesho@example.com");
        admin.setPassword("topsecret");
        admin.setFirstName("Pesho");
        admin.setLastName("Petrov");
        admin.setAge(18);
        admin.setActive(true);

        return userRepository.save(admin);
    }

    public User createTestUser(String username) {

//        initRoles();

        var admin = new User();

        admin.setUsername(username);
        admin.setEmail("pesho@example.com");
        admin.setPassword("topsecret");
        admin.setFirstName("Pesho");
        admin.setLastName("Petrov");
        admin.setAge(18);
        admin.setActive(true);
        UserRoleEntity adm = new UserRoleEntity();
        adm.setUserRole(UserRoleEnum.ADMIN);
        admin.setUserRoles(List.of(adm));

        return userRepository.save(admin);
    }
    public OfferDetailsDTO createOfferDetailsDTO(){
        OfferDetailsDTO offerDetailsDTO = new OfferDetailsDTO();
        offerDetailsDTO.setId(1L);
        return offerDetailsDTO;
    }

    public Offer createTestOffer(User postedBy) {
        var offer = new Offer();

        offer.setId(1L);
        offer.setImageUrl("test");
        offer.setName("akva");
        offer.setMileage(2000);
        offer.setPrice(BigDecimal.valueOf(5000));
        offer.setPostedBy(postedBy);
        offer.setCategory(createTestCategory());

        return offerRepository.save(offer);
    }

    public Category createTestCategory() {
        var category = new Category(CategoryNameEnum.MANUAL);

        return categoryRepository.save(category);
    }

    public void cleanUpDatabase() {
        offerRepository.deleteAll();
        userRepository.deleteAll();
//        userRoleRepository.deleteAll();
        categoryRepository.deleteAll();
    }
}
