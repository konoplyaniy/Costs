package com.purchases.my.db.service;

import com.purchases.my.db.dao.CategoryDAO;
import com.purchases.my.db.entity.CategoryEntity;
import com.purchases.my.db.entity.PurchaseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryService {
    private CategoryDAO categoryDAO;

    public void setCategoryDAO(CategoryDAO categoryDAO) {
        this.categoryDAO = categoryDAO;
    }

    @Transactional
    public void addCategory(CategoryEntity category) {
        categoryDAO.addCategory(category);
    }

    @Transactional
    public void editCategory(CategoryEntity categoryEntity){
        categoryDAO.editCategory(categoryEntity);
    }

    @Transactional
    public void removeCategory(int id){
        categoryDAO.removeCategory(id);
    }

    @Transactional
    public CategoryEntity getCategory(int id) {
        return categoryDAO.getCategory(id);
    }

    @Transactional
    public CategoryEntity getCategory(String category) {
        return categoryDAO.getCategory(category);
    }

    @Transactional
    public List<PurchaseEntity> getPurchases(int id) {
        return categoryDAO.getPurchaseEntities(id);
    }

    @Transactional
    public List<PurchaseEntity> getPurchases(String categoryName) {
        return categoryDAO.getPurchaseEntities(categoryName);
    }

    @Transactional
    public List<CategoryEntity> getAllCategories(){
        return categoryDAO.getAllCategories();
    }
}
