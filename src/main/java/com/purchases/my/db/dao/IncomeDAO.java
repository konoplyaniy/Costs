package com.purchases.my.db.dao;

import com.purchases.my.db.entity.IncomeEntity;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public class IncomeDAO {
    @Autowired
    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void addIncomeEntity(IncomeEntity incomeEntity) {
        sessionFactory.getCurrentSession().persist(incomeEntity);
    }

    public void updateIncomeEntity(IncomeEntity incomeEntity) {
        sessionFactory.getCurrentSession().update(incomeEntity);
    }

    public void removeIncomeEntity(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        IncomeEntity incomeEntity = (IncomeEntity) session.get(IncomeEntity.class, id);
        if (null != incomeEntity) {
            session.delete(incomeEntity);
        }
    }

    public IncomeEntity getIncomeEntity(int id) {
        return (IncomeEntity) sessionFactory.getCurrentSession().get(IncomeEntity.class, id);
    }

    @SuppressWarnings("unchecked")
    public List<IncomeEntity> getIncomeEntities(Date start, Date end) {
        Query query = sessionFactory.getCurrentSession().createQuery("from IncomeEntity where date between :start and :end");
        query.setParameter("start", start);
        query.setParameter("end", end);
        return (List<IncomeEntity>) query.list();
    }

    public long getFullIncome() {
        Query query = sessionFactory.getCurrentSession().createQuery("select SUM(quantity) from IncomeEntity");
        if (null != query.uniqueResult()) {
            return (long) query.uniqueResult();
        } else {
            return 0;
        }
    }

    public long getIncomeQuantity(Date start, Date end) {
        Query query = sessionFactory.getCurrentSession()
                .createQuery("select SUM(quantity) from IncomeEntity where date between :start and :end");
        query.setParameter("start", start);
        query.setParameter("end", end);
        if (null != query.uniqueResult()) {
            return (long) query.uniqueResult();
        } else {
            return 0;
        }
    }

    //add remove
}
