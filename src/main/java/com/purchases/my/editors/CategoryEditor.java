package com.purchases.my.editors;

import com.purchases.my.db.entity.CategoryEntity;
import com.purchases.my.db.service.CategoryService;

import java.beans.PropertyEditorSupport;

public class CategoryEditor extends PropertyEditorSupport {
    private CategoryService categoryService;

    public CategoryEditor(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Override
    public String getAsText() {
        CategoryEntity category = (CategoryEntity) getValue();
        if (category != null) {
//            System.out.println("--->>>" + category.getName());
            return category.getName();
        } else {
//            System.out.println("Return empty string");
            return "";
        }
    }

    // Converts a String to a Category (when submitting form)
    @Override
    public void setAsText(String text) {
        /*System.out.println("search category: [" + text + "]");*/
        if (text.isEmpty()){
            this.setValue(new CategoryEntity());
        }else {
            try {
                int id = Integer.parseInt(text);
                this.setValue(categoryService.getCategory(id));
               /* System.out.println("found category---->>>> " + categoryService.getCategory(Integer.parseInt(text)));*/
            }catch (NumberFormatException e){
                System.out.println("NumberFormatException while parse category id!!CategoryEditor.setAsText");
            }
        }
    }
}
