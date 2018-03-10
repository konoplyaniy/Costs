package com.purchases.my.db.entity;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "category", schema = "my_costs")
public class CategoryEntity {
    private int id;
    private String name;
    private Collection<PurchaseEntity> purchasesById;

    public CategoryEntity() {
    }

    public CategoryEntity(String name) {
        this.name = name;
    }

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
    @Column(name = "name", nullable = true, length = 45)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CategoryEntity that = (CategoryEntity) o;

        if (id != that.id) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "categoryByCategoryId")
    public Collection<PurchaseEntity> getPurchasesById() {
        return purchasesById;
    }

    public void setPurchasesById(Collection<PurchaseEntity> purchasesById) {
        this.purchasesById = purchasesById;
    }

    @Override
    public String toString() {
        return name;
    }
}