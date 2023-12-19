package com.bikehub.web;

import com.bikehub.model.enums.CategoryNameEnum;
import com.bikehub.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/manual")
    public String allManual(Model model) {

        model.addAttribute("allManual", categoryService.getAllByCategory(CategoryNameEnum.MANUAL));

        return "category-manual";
    }

    @GetMapping("/electric")
    public String allElectric(Model model) {

        model.addAttribute("allManual", categoryService.getAllByCategory(CategoryNameEnum.ELECTRIC));

        return "category-electric";
    }

    @GetMapping("/motorbike")
    public String allMotorbike(Model model) {

        model.addAttribute("allManual", categoryService.getAllByCategory(CategoryNameEnum.MOTORBIKE));

        return "category-motorbike";
    }
}
