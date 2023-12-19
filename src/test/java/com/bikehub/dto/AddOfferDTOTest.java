package com.bikehub.dto;

import com.bikehub.model.dto.offer.AddOfferDTO;
import com.bikehub.model.enums.CategoryNameEnum;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AddOfferDTOTest {

    @Test
    public void testSettersAndGetters() {
        AddOfferDTO addOfferDTO = new AddOfferDTO();

        // Set values using setters
        addOfferDTO.setName("Car Model");
        addOfferDTO.setCategory(CategoryNameEnum.MANUAL);
        addOfferDTO.setYear(2020);
        addOfferDTO.setMileage(5000);
        addOfferDTO.setImageUrl("example.com/image");
        addOfferDTO.setPrice(BigDecimal.valueOf(25000.00));

        // Validate values retrieved using getters
        assertEquals("Car Model", addOfferDTO.getName());
        assertEquals(CategoryNameEnum.MANUAL, addOfferDTO.getCategory());
        assertEquals(2020, addOfferDTO.getYear());
        assertEquals(5000, addOfferDTO.getMileage());
        assertEquals("example.com/image", addOfferDTO.getImageUrl());
        assertEquals(BigDecimal.valueOf(25000.00), addOfferDTO.getPrice());
    }
}

