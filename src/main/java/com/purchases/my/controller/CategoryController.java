package com.purchases.my.controller;

import com.purchases.my.db.entity.CategoryEntity;
import com.purchases.my.db.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class CategoryController {
    private CategoryService categoryService;

    @Autowired
    @Qualifier(value = "categoryService")
    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home() {
        return "home";
    }

    @RequestMapping(value = "/categories", method = RequestMethod.GET)
    public String listCategories(Model model) {
        model.addAttribute("category", new CategoryEntity());
        model.addAttribute("listCategories", this.categoryService.getAllCategories());
        return "categories";
    }

    @RequestMapping(value = "/category/add-category", method = RequestMethod.POST)
    public String addCategory(@ModelAttribute("category") CategoryEntity categoryEntity,
                              RedirectAttributes redirectAttributes) {
        if (null !=this.categoryService.getCategory(categoryEntity.getName())){
            redirectAttributes.addFlashAttribute("errorMSG", "Category \"" + categoryEntity.getName() + "\" already exist");
            return "redirect:/categories";
        }
        if (categoryEntity.getId() == 0) {
            this.categoryService.addCategory(categoryEntity);
            redirectAttributes.addFlashAttribute("successMSG", "Added new category \"" + categoryEntity.getName() + "\"");
        } else {
            this.categoryService.editCategory(categoryEntity);
            redirectAttributes.addFlashAttribute("successMSG", "Category updated");
        }
        return "redirect:/categories";
    }

    @RequestMapping("/remove-category/{id}")
    public String removeCategory(@PathVariable("id") int id,
                                 final RedirectAttributes redirectAttributes) {
        String categoryName = this.categoryService.getCategory(id).getName();
        this.categoryService.removeCategory(id);
        redirectAttributes.addFlashAttribute("successMSG", "Category \"" + categoryName + "\" successfully removed");
        return "redirect:/categories";
    }

    @RequestMapping("/edit-category/{id}")
    public String editCategory(@PathVariable("id") int id, Model model) {
        model.addAttribute("category", this.categoryService.getCategory(id));
        model.addAttribute("listCategories", this.categoryService.getAllCategories());
        return "categories";
    }

}
