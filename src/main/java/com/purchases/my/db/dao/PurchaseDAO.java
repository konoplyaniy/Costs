package com.purchases.my.db.dao;

import com.purchases.my.db.entity.PurchaseEntity;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Repository
public class PurchaseDAO {
    @Autowired
    private SessionFactory sessionFactory;
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void addPurchaseEntity(PurchaseEntity purchaseEntity) {
        sessionFactory.getCurrentSession().persist(purchaseEntity);
    }

    public PurchaseEntity getPurchase(int id) {
        return (PurchaseEntity) sessionFactory.getCurrentSession().get(PurchaseEntity.class, id);
    }

    public void removePurchase(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        PurchaseEntity purchase = (PurchaseEntity) session.get(PurchaseEntity.class, id);
        if (null != purchase) {
            session.delete(purchase);
        }
    }

    public void updatePurchaseEntity(PurchaseEntity purchaseEntity) {
        sessionFactory.getCurrentSession().update(purchaseEntity);
    }

    public void removePurchaseEntity(PurchaseEntity purchaseEntity) {
        sessionFactory.getCurrentSession().delete(purchaseEntity);
    }

    @SuppressWarnings("unchecked")
    public List<PurchaseEntity> getPurchases(Date start, Date end) {
        Calendar calendar = Calendar.getInstance();
                calendar.setTime(end);
                calendar.add(Calendar.DATE,1);
        end = calendar.getTime();
        Query query = sessionFactory.getCurrentSession().createQuery("from PurchaseEntity where date between :start and :end");
        query.setParameter("start", start);
        query.setParameter("end", end);
        return (List<PurchaseEntity>) query.list();
    }

    @SuppressWarnings("unchecked")
    public List<PurchaseEntity> getPurchases(Date date) {
        Query query = sessionFactory.getCurrentSession().createQuery("from PurchaseEntity " +
                "where DAY(date) = DAY(:date) and MONTH(date) = MONTH(:date) and YEAR(date) = YEAR(:date)");
        query.setParameter("date", date);
        return query.list();
    }

    public double getPurchasePriceByDay(Date date) {
        Query query = sessionFactory.getCurrentSession().createQuery("select SUM(price) from PurchaseEntity " +
                "where DAY(date) = DAY(:date) and MONTH(date) = MONTH(:date) and YEAR(date) = YEAR(:date)");
        query.setParameter("date", date);
        if (null != query.uniqueResult()) {
            return (double) query.uniqueResult();
        } else {
            return 0;
        }
    }

    public double getPurchasePrice(Date start, Date end) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(end);
        calendar.add(Calendar.DATE,1);
        end = calendar.getTime();
        Query query = sessionFactory.getCurrentSession().createQuery("select SUM(price) from PurchaseEntity " +
                "where date between :start and :end");
        query.setParameter("start", start);
        query.setParameter("end", end);
        if (null != query.uniqueResult()) {
            return (double) query.uniqueResult();
        } else {
            return 0;
        }
    }

    public double getFullPurchasePrice() {
        Query query = sessionFactory.getCurrentSession().createQuery("select SUM(price) from PurchaseEntity ");
        if (null != query.uniqueResult()) {
            return (double) query.uniqueResult();
        } else {
            return 0;
        }
    }
}
