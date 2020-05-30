package com.marjakuusi.register.backend.entity;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Entity
public class Product extends AbstractEntity {
    private String name;

    @OneToMany(mappedBy = "product", fetch = FetchType.EAGER)
    private List<Customer> productOwners = new LinkedList<>();

    public Product() {
    }

    public Product(String name) {
        setName(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Customer> getProductOwners() {
        return productOwners;
    }
}