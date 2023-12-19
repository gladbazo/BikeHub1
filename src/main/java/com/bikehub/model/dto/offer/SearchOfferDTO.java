package com.bikehub.model.dto.offer;

import javax.validation.constraints.NotEmpty;

public class SearchOfferDTO {

    @NotEmpty
    private String name;

    public SearchOfferDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
