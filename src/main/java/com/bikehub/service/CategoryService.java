package com.bikehub.service;

import com.bikehub.model.dto.offer.OfferDetailsDTO;
import com.bikehub.repository.OfferRepository;
import com.bikehub.model.entity.Offer;
import com.bikehub.model.enums.CategoryNameEnum;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    private final ModelMapper modelMapper;
    private final OfferRepository offerRepository;

    public CategoryService(ModelMapper modelMapper, OfferRepository offerRepository) {
        this.modelMapper = modelMapper;
        this.offerRepository = offerRepository;
    }

    public List<OfferDetailsDTO> getAllByCategory(CategoryNameEnum categoryNameEnum) {

        return offerRepository.findAllByCategoryCategory(categoryNameEnum)
                .stream()
                .map(offer -> modelMapper.map(offer, OfferDetailsDTO.class))
                .collect(Collectors.toList());
    }

    public long getAllInCategoryCount(CategoryNameEnum categoryNameEnum) {

        List<Offer> listFiltered = offerRepository.findAllByCategoryCategory(categoryNameEnum);

        return listFiltered.size();
    }
}
