package com.marjakuusi.register.backend.entity;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Jari Haapasaari
 * @version 30.5.2020
 * Entity Class of Customer Type. One "Type" object contains multiple customers.
 * Type can be either "Business", "Enterprise" or "Consumer".
 */
@Entity
public class Type extends AbstractEntity {

    /* ----------------------------------------------------------------------------------- */

    /* attributes */

    private String name;

    /* LinkedList of Customer objects, which are mapped to type. */

    @OneToMany(mappedBy = "type", fetch = FetchType.EAGER)
    private List<Customer> types = new LinkedList<>();

    /* ----------------------------------------------------------------------------------- */

    /* constructors */

    public Type() {
    }

    public Type(String name) {
        setName(name);
    }

    /* ----------------------------------------------------------------------------------- */

    /* logic */

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Customer> getTypes() {
        return types;
    }
}