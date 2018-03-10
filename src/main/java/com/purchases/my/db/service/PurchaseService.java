package com.purchases.my.db.service;

import com.purchases.my.db.dao.PurchaseDAO;
import com.purchases.my.db.entity.PurchaseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class PurchaseService {
    private PurchaseDAO purchaseDAO;

    public void setPurchaseDAO(PurchaseDAO purchaseDAO) {
        this.purchaseDAO = purchaseDAO;
    }

    @Transactional
    public void addPurchase(PurchaseEntity purchaseEntity) {
        purchaseDAO.addPurchaseEntity(purchaseEntity);
    }

    @Transactional
    public PurchaseEntity getPurchase(int id) {
        return purchaseDAO.getPurchase(id);
    }

    @Transactional
    public void removePurchase(int id) {
        purchaseDAO.removePurchase(id);
    }

    @Transactional
    public void updatePurchase(PurchaseEntity purchaseEntity) {
        purchaseDAO.updatePurchaseEntity(purchaseEntity);
    }

    @Transactional
    public List<PurchaseEntity> getPurchases(Date start, Date end) {
        return purchaseDAO.getPurchases(start, end);
    }

    @Transactional
    public double getPurchasePriceByDay(Date date) {
        return purchaseDAO.getPurchasePriceByDay(date);
    }

    @Transactional
    public List<PurchaseEntity> getPurchases(Date date) {
        return purchaseDAO.getPurchases(date);
    }

    @Transactional
    public double getPurchasePrice(Date start, Date end) {
        return purchaseDAO.getPurchasePrice(start, end);
    }

    @Transactional
    public double getFullPurchasePrice() {
        return purchaseDAO.getFullPurchasePrice();
    }


}
