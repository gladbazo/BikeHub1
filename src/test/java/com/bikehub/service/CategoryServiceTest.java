package com.bikehub.service;

import com.bikehub.model.dto.offer.OfferDetailsDTO;
import com.bikehub.model.entity.Category;
import com.bikehub.model.entity.Offer;
import com.bikehub.model.enums.CategoryNameEnum;
import com.bikehub.repository.OfferRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private OfferRepository offerRepository;

    @InjectMocks
    private CategoryService categoryService;

    @Test
    public void testGetAllByCategory() {
        // Mock data
        Offer offer1 = new Offer();
        offer1.setCategory(new Category(CategoryNameEnum.MANUAL));
        offer1.setYear(2020);
        offer1.setMileage(1500);
        Offer offer2 = new Offer();
        offer2.setCategory(new Category(CategoryNameEnum.MANUAL));
        offer2.setYear(2018);
        offer2.setMileage(460);
        List<Offer> mockOfferList = Arrays.asList(offer1, offer2);

        // Mock behavior of offerRepository
        when(offerRepository.findAllByCategoryCategory(CategoryNameEnum.MANUAL))
                .thenReturn(mockOfferList);
        OfferDetailsDTO offerDetailsdto = new OfferDetailsDTO();
        offerDetailsdto.setCategory(CategoryNameEnum.MANUAL);
        // Mock mapping behavior
        when(modelMapper.map(any(), eq(OfferDetailsDTO.class)))
                .thenReturn(offerDetailsdto);

        // Call the method being tested
        List<OfferDetailsDTO> result = categoryService.getAllByCategory(CategoryNameEnum.MANUAL);

        // Assertions
        assertEquals(2, result.size()); // Assuming 2 offers returned
        // Add more assertions based on your actual mapping logic
    }

    @Test
    public void testGetAllInCategoryCount() {
        // Mock data
        Offer offer1 = new Offer();
        offer1.setCategory(new Category(CategoryNameEnum.MANUAL));
        offer1.setYear(2020);
        offer1.setMileage(1500);
        Offer offer2 = new Offer();
        offer2.setCategory(new Category(CategoryNameEnum.MANUAL));
        offer2.setYear(2018);
        offer2.setMileage(460);
        List<Offer> mockOfferList = Arrays.asList(offer1, offer2);

        // Mock behavior of offerRepository
        when(offerRepository.findAllByCategoryCategory(CategoryNameEnum.MANUAL))
                .thenReturn(mockOfferList);

        // Call the method being tested
        long result = categoryService.getAllInCategoryCount(CategoryNameEnum.MANUAL);

        // Assertions
        assertEquals(2, result); // Assuming 2 offers returned
    }
}

