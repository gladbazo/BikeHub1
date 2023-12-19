package com.bikehub.repository;

import com.bikehub.model.entity.Offer;
import com.bikehub.model.enums.CategoryNameEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OfferRepository extends JpaRepository<Offer, Long> {

    List<Offer> findAllByName(String name);

    List<Offer> findAllByCategoryCategory(CategoryNameEnum categoryNameEnum);
}
