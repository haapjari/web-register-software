package com.marjakuusi.register.backend.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * @author Jari Haapasaari
 * @version 30.5.2020
 * SuperClass for other Entity Classes
 */
@MappedSuperclass
public abstract class AbstractEntity {

    /* ----------------------------------------------------------------------------------- */

    /* attributes */

    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE)
    private Long id;

    /* ----------------------------------------------------------------------------------- */

    /* logic */

    public Long getId() {
        return id;
    }

    /**
     * Checks if certain id is not null
     * @return boolean value of objects id status
     */
    public boolean isPersisted() {
        return id != null;
    }

    /**
     * Native Java method.
     * @return integer hash code value of the object
     */
    @Override
    public int hashCode() {
        if (getId() != null) {
            return getId().hashCode();
        }
        return super.hashCode();
    }

    /**
     * Overriding string.equals method to compare objects
     * @param obj Object to compare
     * @return boolean value, if object ids match
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        AbstractEntity other = (AbstractEntity) obj;
        if (getId() == null || other.getId() == null) {
            return false;
        }
        return getId().equals(other.getId());
    }
}