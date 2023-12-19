package com.bikehub.dto;

import com.bikehub.model.dto.offer.OfferDetailsDTO;
import com.bikehub.model.entity.User;
import com.bikehub.model.enums.CategoryNameEnum;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class OfferDetailsDTOTest {

    @Test
    public void testSettersAndGetters() {
        OfferDetailsDTO offerDTO = new OfferDetailsDTO();

        // Set values using setters
        offerDTO.setId(1L);
        offerDTO.setName("Car Model");
        offerDTO.setYear(2020);
        offerDTO.setMileage(5000);
        offerDTO.setPrice(BigDecimal.valueOf(25000.00));
        offerDTO.setCategory(CategoryNameEnum.MANUAL);
        offerDTO.setImageUrl("example.com/image");
        User user = new User(); // Assuming User class is available
        offerDTO.setPostedBy(user);

        // Validate values retrieved using getters
        assertEquals(1L, offerDTO.getId());
        assertEquals("Car Model", offerDTO.getName());
        assertEquals(2020, offerDTO.getYear());
        assertEquals(5000, offerDTO.getMileage());
        assertEquals(BigDecimal.valueOf(25000.00), offerDTO.getPrice());
        assertEquals(CategoryNameEnum.MANUAL, offerDTO.getCategory());
        assertEquals("example.com/image", offerDTO.getImageUrl());
        assertEquals(user, offerDTO.getPostedBy());
    }
}

