package com.bikehub.web;

import com.bikehub.exception.InvalidDatesException;
import com.bikehub.model.dto.offer.AddOfferDTO;
import com.bikehub.model.dto.offer.OfferDetailsDTO;
import com.bikehub.model.dto.offer.SearchOfferDTO;
import com.bikehub.exception.CannotDeleteOffer;
import com.bikehub.exception.OfferNotFoundException;
import com.bikehub.service.OfferService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping("/offers")
public class OfferController {

    private final OfferService offerService;


    public OfferController(OfferService offerService) {
        this.offerService = offerService;

    }

    @GetMapping("/all")
    public String allOffers(Model model, @PageableDefault(
            sort = "price",
            direction = Sort.Direction.ASC,
            page = 0,
            size = 3) Pageable pageable) {

        model.addAttribute("offers", offerService.getAllOffers(pageable));

        return "all-offers";
    }

    @GetMapping("/add")
    public String addOffer(Model model) {
        model.addAttribute("addOfferDTO", new AddOfferDTO());

        return "offers-add";
    }
    @PostMapping("/add")
    public String submitAddOffer(@ModelAttribute("addOfferDTO") AddOfferDTO addOfferDTO, Principal principal){
        UserDetails userDetails = (UserDetails) ((Authentication) principal).getPrincipal();
        offerService.addOffer(addOfferDTO, userDetails);
        return "redirect:/offers/all";
    }




    @ExceptionHandler({OfferNotFoundException.class})
    @GetMapping("/{id}/details")
    public String getOfferDetail(@PathVariable("id") Long id, Model model) {

        OfferDetailsDTO offerById = offerService.findOfferById(id);

        if (offerById == null) {
            return "error/offer-not-found";
        }

        model.addAttribute("offer", offerById);

        return "offer-details";
    }



    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}/details")
    @ExceptionHandler({CannotDeleteOffer.class})
    public String deleteOffer(@PathVariable("id") Long id) {

        offerService.deleteOfferById(id);

        return "redirect:/offers/all";
    }

    @GetMapping("/search")
    public String searchOffer() {
        return "offer-search";
    }

    @GetMapping("/search/{name}")
    public String searchResults(@PathVariable String name, Model model) {

        model.addAttribute("offers", offerService.findOfferByName(name));

        return "offer-search";
    }

    @PostMapping("/search")
    public String searchQuery(@Valid SearchOfferDTO searchOfferDTO,
                              BindingResult bindingResult,
                              RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("searchOfferModel", searchOfferDTO);
            redirectAttributes.addFlashAttribute(
                    "org.springframework.validation.BindingResult.searchOfferModel",
                    bindingResult);
            return "redirect:/search";
        }

        return String.format("redirect:/offers/search/%s", searchOfferDTO.getName());
    }

    @ModelAttribute
    public AddOfferDTO addOfferDTO() {
        return new AddOfferDTO();
    }

    @ModelAttribute(name = "searchOfferModel")
    private SearchOfferDTO searchOfferDTO() {
        return new SearchOfferDTO();
    }


}
