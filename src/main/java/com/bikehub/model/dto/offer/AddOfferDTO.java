package com.bikehub.model.dto.offer;

import com.bikehub.model.enums.CategoryNameEnum;
import com.bikehub.model.validator.YearNotInTheFuture;

import javax.validation.constraints.*;
import java.math.BigDecimal;

public class AddOfferDTO {
    @NotEmpty(message = "Name is required")
    private String name;
    @NotNull(message = "Category is required.")
    private CategoryNameEnum category;

    @YearNotInTheFuture(message="Year must not be in Future")
    private int year;
    @Positive
    @NotNull
    private int mileage;


    @NotEmpty(message = "Image Url name is required.")
    private String imageUrl;

    @Positive(message = "Price must be a positive number.")
    @NotNull(message = "Price is required.")
    private BigDecimal price;
    public AddOfferDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CategoryNameEnum getCategory() {
        return category;
    }

    public void setCategory(CategoryNameEnum category) {
        this.category = category;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMileage() {
        return mileage;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
