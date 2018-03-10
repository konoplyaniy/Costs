package com.purchases.my.db.dao;

import com.purchases.my.db.entity.CategoryEntity;
import com.purchases.my.db.entity.PurchaseEntity;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CategoryDAO {
    @Autowired
    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void addCategory(CategoryEntity categoryEntity) {
        this.sessionFactory.getCurrentSession().persist(categoryEntity);
    }

    public void editCategory(CategoryEntity categoryEntity) {
        this.sessionFactory.getCurrentSession().update(categoryEntity);
    }

    public void removeCategory(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        CategoryEntity category = (CategoryEntity) session.get(CategoryEntity.class, id);
        if (null != category) {
            session.delete(category);
        }
    }

    public CategoryEntity getCategory(int id) {
        return (CategoryEntity) sessionFactory.getCurrentSession().get(CategoryEntity.class, id);
    }

    public CategoryEntity getCategory(String name) {
        Query query = sessionFactory.getCurrentSession().createQuery("from CategoryEntity where name=:name");
        query.setParameter("name", name);
        return (CategoryEntity) query.uniqueResult();
    }

    public List<PurchaseEntity> getPurchaseEntities(int id) {
        return null;
        /*return (List<PurchaseEntity>) sessionFactory.getCurrentSession().get(CategoryEntity.class, id).getPurchasesById();*/
    }

    public List<PurchaseEntity> getPurchaseEntities(String name) {
        Query query = sessionFactory.getCurrentSession().createQuery("from CategoryEntity where name=:name");
        query.setParameter("name", name);
        return (List<PurchaseEntity>) ((CategoryEntity) query.uniqueResult()).getPurchasesById();
    }

    @SuppressWarnings("unchecked")
    public List<CategoryEntity> getAllCategories() {
        return (List<CategoryEntity>) sessionFactory.getCurrentSession().createQuery("from CategoryEntity ").list();
    }

}
