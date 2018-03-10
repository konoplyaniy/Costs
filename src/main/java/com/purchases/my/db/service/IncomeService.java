package com.purchases.my.db.service;

import com.purchases.my.db.dao.IncomeDAO;
import com.purchases.my.db.entity.IncomeEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class IncomeService {
    private IncomeDAO incomeDAO;

    public void setIncomeDAO(IncomeDAO incomeDAO) {
        this.incomeDAO = incomeDAO;
    }

    @Transactional
    public void addIncome(IncomeEntity incomeEntity) {
        incomeDAO.addIncomeEntity(incomeEntity);
    }

    @Transactional
    public void updateIncome(IncomeEntity incomeEntity) {
        incomeDAO.updateIncomeEntity(incomeEntity);
    }

    @Transactional
    public void removeIncome(int id) {
        incomeDAO.removeIncomeEntity(id);
    }

    @Transactional
    public IncomeEntity getIncomeEntity(int id) {
        return incomeDAO.getIncomeEntity(id);
    }

    @Transactional
    public List<IncomeEntity> getIncomeEntities(Date start, Date end) {
        return incomeDAO.getIncomeEntities(start, end);
    }

    @Transactional
    public long getFullIncome() {
        return incomeDAO.getFullIncome();
    }

    @Transactional
    public long getIncomeQuantity(Date start, Date end) {
        return incomeDAO.getIncomeQuantity(start, end);
    }
}
