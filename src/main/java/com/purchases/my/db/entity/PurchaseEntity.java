package com.purchases.my.db.entity;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "purchase", schema = "my_costs")
public class PurchaseEntity {
    private int id;
    private Date date;
    private double price;
    private String comment;
    private CategoryEntity categoryByCategoryId;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    @Column(name = "date", nullable = false)
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Basic
    @Column(name = "price", nullable = false, precision = 0)
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Basic
    @Column(name = "comment", length = 45)
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PurchaseEntity that = (PurchaseEntity) o;
        return id == that.id &&
                Double.compare(that.price, price) == 0 &&
                Objects.equals(date, that.date) &&
                Objects.equals(comment, that.comment) &&
                Objects.equals(categoryByCategoryId, that.categoryByCategoryId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, date, price, comment, categoryByCategoryId);
    }

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id", nullable = false)
    public CategoryEntity getCategoryByCategoryId() {
        return categoryByCategoryId;
    }

    public void setCategoryByCategoryId(CategoryEntity categoryByCategoryId) {
        this.categoryByCategoryId = categoryByCategoryId;
    }

    @Override
    public String toString() {
        return "PurchaseEntity{" +
                "id=" + id +
                ", date=" + date +
                ", price=" + price +
                ", comment='" + comment + '\'' +
                ", categoryByCategoryId=" + categoryByCategoryId.getId() + "category name: " + categoryByCategoryId.getName() +
                '}';
    }
}
