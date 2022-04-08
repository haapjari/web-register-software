package com.practice.register.backend.entity;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author haapjari
 * @version 30.5.2020
 * Customer Entity Class. Customer class holds details for customer details.
 */
@Entity
public class Customer extends AbstractEntity implements Cloneable {

    /* ----------------------------------------------------------------------------------- */

    /* attributes */

    // enum is a special class that represents group of unchangeable variables
    public enum Status {
        CEO, POC, Employee
    }

    @NotNull
    @NotEmpty
    private String firstName = "";

    @NotNull
    @NotEmpty
    private String lastName = "";

    /* TODO Issue */

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Type type;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Customer.Status status;

    @Email
    @NotNull
    @NotEmpty
    private String email = "";

    /* ----------------------------------------------------------------------------------- */

    /* logic */

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    /* ----------------------------------------------------------------------------------- */

    /**
     * Overrides Javas toString method to return full name
     */
    @Override
    public String toString() {
        return firstName + " " + lastName;
    }

}